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
import cloud.pangeacyber.pangea.audit.arweave.Arweave;
import cloud.pangeacyber.pangea.audit.arweave.PublishedRoot;
import cloud.pangeacyber.pangea.audit.utils.ConsistencyProof;
import cloud.pangeacyber.pangea.audit.utils.Verification;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.SignerException;
import cloud.pangeacyber.pangea.exceptions.VerificationFailed;


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
    @JsonProperty("verbose")
    Boolean verbose;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("signature")
    String signature;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("public_key")
    String publicKey;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("prev_root")
    String prevRoot;

    public LogRequest(Event event, Boolean verbose, String signature, String publicKey, String prevRoot) {
        this.event = event;
        this.verbose = verbose;
        this.signature = signature;
        this.publicKey = publicKey;
        this.prevRoot = prevRoot;
    }
}

public class AuditClient extends Client {
    public static String serviceName = "audit";
    LogSigner signer;
    Map<Integer, PublishedRoot> publishedRoots;
    boolean allowServerRoots = true;    // In case of Arweave failure, ask the server for the roots
    String prevUnpublishedRoot = null;

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

    private LogResponse logPost(Event event, Boolean verbose, String signature, String publicKey, boolean verify)  throws PangeaException, PangeaAPIException{
        String prevRoot = null;
        if(verify){
            verbose = true;
            prevRoot = this.prevUnpublishedRoot;
        }
        LogRequest request = new LogRequest(event, verbose, signature, publicKey, prevRoot);
        return doPost("/v1/log", request, LogResponse.class);
    }

    private LogResponse doLog(Event event, SignMode signMode, Boolean verbose, boolean verify) throws PangeaException, PangeaAPIException{
        String signature = null;
        String publicKey = null;

        if(signMode == SignMode.LOCAL && this.signer == null){
            throw new SignerException("Signer not initialized", null);
        } else if(signMode == SignMode.LOCAL && this.signer != null){
            String canEvent;
            try{
                canEvent = Event.canonicalize(event);
            } catch(Exception e){
                throw new SignerException("Failed to convert event to string", e);
            }

            signature = this.signer.sign(canEvent);
            publicKey = this.signer.getPublicKey();
        }

        LogResponse response = logPost(event, verbose, signature, publicKey, verify);
        processLogResponse(response.getResult(), verify);
        return response;
    }

    private void processLogResponse(LogResult result, boolean verify) throws VerificationFailed, PangeaException{
        String newUnpublishedRoot = result.getUnpublishedRoot();

        result.setEventEnvelope(EventEnvelope.fromRaw(result.getRawEnvelope()));
        if(verify){
            EventEnvelope.verifyHash(result.getRawEnvelope(), result.getHash());
            result.verifySignature();
            if(newUnpublishedRoot != null){
                result.membershipVerification = Verification.verifyMembershipProof(newUnpublishedRoot, result.hash, result.membershipProof);
                if(result.consistencyProof != null && this.prevUnpublishedRoot != null){
                    ConsistencyProof conProof = Verification.decodeConsistencyProof(result.consistencyProof);
                    result.consistencyVerification = Verification.verifyConsistencyProof(newUnpublishedRoot, this.prevUnpublishedRoot, conProof);
                }
            }
        }

        if( newUnpublishedRoot != null){
            this.prevUnpublishedRoot = newUnpublishedRoot;
        }
    }


    /**
     * Log an entry
     * @pangea.description Log an event to Audit Secure Log. By default does not sign event and verbose is left as server default
     * @param event event to log
     * @return LogResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * {@code
     * String msg = "Event's message";
     * 
     * Event event = new Event(msg);
     * 
     * LogResponse response = client.log(event);
     * }
     */
    public LogResponse log(Event event) throws PangeaException, PangeaAPIException{
        return doLog(event, SignMode.UNSIGNED, null, false);
    }

    /**
     * Log an entry - event, sign, verbose
     * @pangea.description Log an event to Audit Secure Log. Can select sign event or not and verbosity of the response.
     * @param event event to log
     * @param signMode "Unsigned" or "Local"
     * @param verbose true to more verbose response
     * @return LogResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * {@code
     * String msg = "Event's message";
     * 
     * Event event = new Event(msg);
     * 
     * LogResponse response = client.log(event, "Local", true);
     * }
     */
    public LogResponse log(Event event, SignMode signMode, boolean verbose, boolean verify) throws PangeaException, PangeaAPIException {
        return doLog(event, signMode, verbose, verify);
    }

    private RootResponse rootPost(Integer treeSize) throws PangeaException, PangeaAPIException {
        RootRequest request = new RootRequest(treeSize);
        return doPost("/v1/root", request, RootResponse.class);
    }

    /**
     * Get last root
     * @pangea.description Get last root from Pangea Server
     * @return RootResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * {@code
     * RootResponse response = client.getRoot();
     * }
     */
    public RootResponse getRoot() throws PangeaException, PangeaAPIException {
        return rootPost(null);
    }

    /**
     * Get root from Pangea Server
     * @pangea.description Get root from three of treeSize from Pangea Server
     * @param treeSize tree size to get root
     * @return RootResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * {@code
     * RootResponse response = client.getRoot(treeSize);
     * }
     */
    public RootResponse getRoot(int treeSize) throws PangeaException, PangeaAPIException {
        return rootPost(treeSize);
    }

    private void processSearchResult(ResultsOutput result, boolean verifyConsistency, boolean verifyEvents) throws PangeaException, PangeaAPIException{
        for(SearchEvent searchEvent : result.getEvents()){
            searchEvent.setEventEnvelope(EventEnvelope.fromRaw(searchEvent.getRawEnvelope()));
        }

        if(verifyEvents){
            for(SearchEvent searchEvent : result.getEvents()){
                EventEnvelope.verifyHash(searchEvent.getRawEnvelope(), searchEvent.getHash());
                searchEvent.verifySignature();
            }
        }

        Root root = result.getRoot();
        Root unpublishedRoot = result.getUnpublishedRoot();

        if(verifyConsistency){
            if(root != null){
                updatePublishedRoots(result);
            }

            for(SearchEvent searchEvent: result.getEvents()){
                searchEvent.verifyMembershipProof(searchEvent.isPublished()? root.getRootHash() : unpublishedRoot.getRootHash());
                searchEvent.verifyConsistency(publishedRoots);
            }
        }
    }

    private void updatePublishedRoots(ResultsOutput result){
        Root root = result.getRoot();
        if(root == null){
            return;
        }

        Set<Integer> treeSizes = new HashSet<Integer>();
        for(SearchEvent searchEvent: result.getEvents()){
            if(searchEvent.published){
                int leafIndex = searchEvent.getLeafIndex();
                treeSizes.add(leafIndex + 1);
                if(leafIndex > 0){
                    treeSizes.add(leafIndex);
                }
            }
        }

        treeSizes.add(root.getSize());
        treeSizes.removeAll(publishedRoots.keySet());
        Integer[] sizes = new Integer[treeSizes.size()];

        Map<Integer, PublishedRoot> arweaveRoots;
        if( !treeSizes.isEmpty()){
            Arweave arweave = new Arweave(root.getTreeName());
            arweaveRoots = arweave.getPublishedRoots(treeSizes.toArray(sizes));
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
     * Search
     * @pangea.description Perform a search of logs according to input param. By default verify logs consistency and events hash and signature.
     * @param input query filters to perform search
     * @return SearchResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * {@code
     * SearchInput input = new SearchInput("message:Integration test msg");
     * 
     * input.setMaxResults(10);
     * 
     * SearchResponse response = client.search(input);
     * }
     */
    public SearchResponse search(SearchInput input) throws PangeaException, PangeaAPIException{
        return searchPost(input, true, true);
    }

    /**
     * Search - input, verifyConsistency, verifyEvents
     * @pangea.description Perform a search of logs according to input param. Allow to select to verify or nor consistency proof and events.
     * @param input query filters to perform search
     * @param verifyConsistency true to verify logs consistency proofs
     * @param verifyEvents true to verify logs hash and signature
     * @return SearchResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * {@code
     * SearchInput input = new SearchInput("message:Integration test msg");
     * 
     * input.setMaxResults(10);
     * 
     * SearchResponse response = client.search(input);
     * }
     */
    public SearchResponse search(SearchInput input, boolean verifyConsistency, boolean verifyEvents) throws PangeaException, PangeaAPIException {
        if(verifyConsistency){
            input.setVerbose(true);
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
     * Results
     * @pangea.description Return result's page from search id.
     * @param id A search results identifier returned by the search call. By default verify events and do not verify consistency.
     * @param limit Number of audit records to include in a single set of results.
     * @param offset Offset from the start of the result set to start returning results from.
     * @return ResultsResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     */
    public ResultsResponse results(String id, Integer limit, Integer offset) throws PangeaException, PangeaAPIException{
        return resultPost(id, limit, offset, false, true);
    }

    /**
     * Results - id, limit, offset, verifyConsistency, verifyEvents
     * @pangea.description Return result's page from search id. Allow to select to verify or nor consistency proof and events.
     * @param id A search results identifier returned by the search call.
     * @param limit Number of audit records to include in a single set of results.
     * @param offset Offset from the start of the result set to start returning results from.
     * @param verifyConsistency true to verify logs consistency proofs
     * @param verifyEvents true to verify logs hash and signature
     * @return ResultsResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     */
    public ResultsResponse results(String id, Integer limit, Integer offset, boolean verifyConsistency, boolean verifyEvents) throws PangeaException, PangeaAPIException{
        return resultPost(id, limit, offset, verifyConsistency, verifyEvents);
    }

}
