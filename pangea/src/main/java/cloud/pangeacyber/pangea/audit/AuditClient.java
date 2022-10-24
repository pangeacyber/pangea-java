package cloud.pangeacyber.pangea.audit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.audit.arweave.Arweave;
import cloud.pangeacyber.pangea.audit.arweave.PublishedRoot;
import cloud.pangeacyber.pangea.audit.utils.Hash;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.SignerException;


final class ResultsRequest{
    @JsonProperty("id")
    String id;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("limit")
    Integer limit = 20;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("offset")
    Integer offset = 0;

    public ResultsRequest(String id, Integer limit, Integer offset) {
        this.id = id;
        this.limit = limit;
        this.offset = offset;
    }

}

final class RootRequest{
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("tree_size")
    Integer treeSize;

    public RootRequest(Integer treeSize) {
        this.treeSize = treeSize;
    }
}

final class LogRequest{
    @JsonProperty("event")
    Event event;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("include_hash")
    Boolean includeHash = true;     // Will be removed soon

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("verbose")
    Boolean verbose;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("signature")
    String signature;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("public_key")
    String publicKey;

    public LogRequest(Event event, Boolean verbose, String signature, String publicKey) {
        this.event = event;
        this.verbose = verbose;
        this.signature = signature;
        this.publicKey = publicKey;
    }
}

final class LogResponse extends Response<LogOutput> {}
final class SearchResponse extends Response<SearchOutput> {}
final class ResultsResponse extends Response<ResultsOutput> {}
final class RootResponse extends Response<RootOutput> {}

public class AuditClient extends Client {
    public static String serviceName = "audit";
    LogSigner signer;
    Map<Integer, PublishedRoot> publishedRoots;
    boolean allowServerRoots = true;    // In case of Arweave failure, ask the server for the roots

    public AuditClient(Config config) {
        super(config, serviceName);
        this.signer = null;
        publishedRoots = new HashMap<Integer, PublishedRoot>();
    }

    public AuditClient(Config config, String privateKeyFilename) {
        super(config, serviceName);
        this.signer = new LogSigner(privateKeyFilename);
        publishedRoots = new HashMap<Integer, PublishedRoot>();
    }

    private LogResponse logPost(Event event, Boolean verbose, String signature, String publicKey)  throws PangeaException, PangeaAPIException{
        LogRequest request = new LogRequest(event, verbose, signature, publicKey);
        return doPost("/v1/log", request, LogResponse.class);
    }

    private LogResponse doLog(Event event, boolean sign, Boolean verbose) throws PangeaException, PangeaAPIException{
        String signature = null;
        String publicKey = null;
    
        if(sign && this.signer == null){
            throw new SignerException("Signer not initialized", null);
        }
        else if(sign && this.signer != null){
            String canEvent;
            try{
                canEvent = Event.canonicalize(event);
            } catch(Exception e){
                throw new SignerException("Failed to convert event to string", e);
            }

            signature = this.signer.sign(canEvent);
            publicKey = this.signer.getPublicKey();
        }

        return logPost(event, verbose, signature, publicKey);
    }

    /**
     * @summary Log an event to Audit Secure Log
     * @description Log an event to Audit Secure Log. By default does not sign event and verbose is left as server default
     * @param event - event to log
     * @return LogResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @example
     * ```java
     *  String msg = "Event's message";
     *  Event event = new Event(msg);
     *  LogResponse response = client.log(event);
     * ```
     */

    public LogResponse log(Event event) throws PangeaException, PangeaAPIException{
        return doLog(event, false, null);
    }

    /**
     * @summary Log an event to Audit Secure Log
     * @description Log an event to Audit Secure Log. Can select sign event or not and verbosity of the response.
     * @param event - event to log
     * @param sign - true to sign event
     * @param verbose - true to more verbose response
     * @return LogResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @example
     * ```java
     *  String msg = "Event's message";
     *  Event event = new Event(msg);
     *  LogResponse response = client.log(event, true, true);
     * ```
     */
    public LogResponse log(Event event, boolean sign, boolean verbose) throws PangeaException, PangeaAPIException {
        return doLog(event, sign, verbose);
    }

    private RootResponse rootPost(Integer treeSize) throws PangeaException, PangeaAPIException {
        RootRequest request = new RootRequest(treeSize);
        return doPost("/v1/root", request, RootResponse.class);        
    }

    /**
     * @summary Get root from Pangea Server
     * @description Get last root from Pangea Server
     * @return
     * @throws PangeaException
     * @throws PangeaAPIException
     * @example 
     * ```java
     * RootResponse response = client.getRoot();
     * ```
     */
    public RootResponse getRoot() throws PangeaException, PangeaAPIException {
        return rootPost(null);
    }

    /**
     * @summary Get root from Pangea Server
     * @description Get root from three of treeSize from Pangea Server
     * @param treeSize - tree size to get root
     * @return RootResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @example
     * ```java
     * RootResponse response = client.getRoot(treeSize);
     * ```
     */
    public RootResponse getRoot(int treeSize) throws PangeaException, PangeaAPIException {
        return rootPost(treeSize);
    }

    private void processSearchResult(ResultsOutput result, boolean verifyConsistency, boolean verifyEvents) throws PangeaException, PangeaAPIException{

        if(verifyEvents){
            for(SearchEvent searchEvent : result.getEvents()){
                searchEvent.verifyHash();
                searchEvent.verifySignature();
            }
        }

        Root root = result.getRoot();

        if(verifyConsistency && root != null){  // if there is no root, we don't have any record migrated to cold. We cannot verify any proof
            updatePublishedRoots(result);
            for(SearchEvent searchEvent: result.getEvents()){
                searchEvent.verifyMembershipProof(Hash.decode(root.getRootHash()));
                searchEvent.verifyConsistency(publishedRoots);    
            }
        }
    }

    private void updatePublishedRoots(ResultsOutput result){
        Set<Integer> treeSizes = new HashSet<Integer>();
        for(SearchEvent searchEvent: result.getEvents()){
            int leafIndex = searchEvent.getLeafIndex();
            treeSizes.add(leafIndex + 1);
            if(leafIndex > 0){
                treeSizes.add(leafIndex);
            }
        }

        Root root = result.getRoot();
        if(root != null){
            treeSizes.add(root.getSize());
        }

        treeSizes.removeAll(publishedRoots.keySet());
        Integer[] sizes = new Integer[treeSizes.size()];
        treeSizes.toArray(sizes);
        Map<Integer, PublishedRoot> arweaveRoots;
        if( !treeSizes.isEmpty()){
            Arweave arweave = new Arweave(root.getTreeName());
            arweaveRoots = arweave.getPublishedRoots(treeSizes.toArray(sizes)); //FIXME: This should be just 'sizes'?
        } else {
            return;
        }

        for(Integer treeSize: sizes){
            if(arweaveRoots.containsKey(treeSize)){
                PublishedRoot pubRoot = arweaveRoots.get(treeSize);
                pubRoot.setSource("arweave");
                this.publishedRoots.put(treeSize, pubRoot);
            } else if(this.allowServerRoots){
                RootResponse response;
                try{
                    response = this.getRoot(treeSize);
                    root = response.getResult().getRoot();
                    PublishedRoot pubRoot = new PublishedRoot(root.getSize(), root.getRootHash(), root.getPublishedAt(), root.getConsistencyProof(), "pangea");
                    this.publishedRoots.put(treeSize, pubRoot);
                } catch(Exception e){
                    break;
                }
            }
        }

    }

    private SearchResponse searchPost(SearchInput request, boolean verifyConsistency, boolean verifyEvents) throws PangeaException, PangeaAPIException{
        SearchResponse response = doPost("/v1/search", request, SearchResponse.class);
        processSearchResult(response.getResult(), verifyConsistency, verifyEvents);
        return response;
    }

    /**
     * @summary Search
     * @description Perform a search of logs according to input param. By default verify logs consistency and events hash and signature.
     * @param input - query filters to perform search
     * @return SearchResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @example
     * ```java
     *  SearchInput input = new SearchInput("message:Integration test msg");
     *  input.setMaxResults(10);
     *  SearchResponse response = client.search(input);
     * ```
     */
    public SearchResponse search(SearchInput input) throws PangeaException, PangeaAPIException{
        input.setIncludeMembershipProof(true);
        return searchPost(input, true, true);
    }

    /**
     * @summary Search
     * @description Perform a search of logs according to input param. Allow to select to verify or nor consistency proof and events.
     * @param input - query filters to perfom search
     * @param verifyConsistency - true to verify logs consistency proofs
     * @param verifyEvents - true to verify logs hash and signature
     * @return SearchResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @example
     * ```java
     *  SearchInput input = new SearchInput("message:Integration test msg");
     *  input.setMaxResults(10);
     *  SearchResponse response = client.search(input);
     * ```
     */
    public SearchResponse search(SearchInput input, boolean verifyConsistency, boolean verifyEvents) throws PangeaException, PangeaAPIException {
        if(verifyConsistency){
            input.setIncludeMembershipProof(true);
        }
        return searchPost(input, verifyConsistency, verifyEvents);
    }

    private ResultsResponse resultPost(String id, Integer limit, Integer offset, boolean verifyConsistency, boolean verifyEvents)  throws PangeaException, PangeaAPIException{
        ResultsRequest request = new ResultsRequest(id, limit, offset);
        ResultsResponse response = doPost("/v1/results", request, ResultsResponse.class);
        processSearchResult(response.getResult(), verifyConsistency, verifyEvents);
        return response;
    }

    /**
     * @summary Results
     * @description Return result's page from search id.
     * @param id - A search results identifier returned by the search call. By default verify events and do not verify consistency.
     * @param limit - Number of audit records to include in a single set of results.
     * @param offset - Offset from the start of the result set to start returning results from.
     * @return ResultsResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     */
    public ResultsResponse results(String id, Integer limit, Integer offset) throws PangeaException, PangeaAPIException{
        return resultPost(id, limit, offset, false, true);
    }

    /**
     * @summary Results
     * @description Return result's page from search id. Allow to select to verify or nor consistency proof and events.
     * @param id - A search results identifier returned by the search call.
     * @param limit - Number of audit records to include in a single set of results.
     * @param offset - Offset from the start of the result set to start returning results from.
     * @param verifyConsistency - true to verify logs consistency proofs
     * @param verifyEvents - true to verify logs hash and signature
     * @return ResultsResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     */
    public ResultsResponse results(String id, Integer limit, Integer offset, boolean verifyConsistency, boolean verifyEvents) throws PangeaException, PangeaAPIException{
        return resultPost(id, limit, offset, verifyConsistency, verifyEvents);
    }

}
