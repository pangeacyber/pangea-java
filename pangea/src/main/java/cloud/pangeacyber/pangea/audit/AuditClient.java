package cloud.pangeacyber.pangea.audit;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bouncycastle.crypto.CryptoException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.audit.arweave.Arweave;
import cloud.pangeacyber.pangea.audit.arweave.PublishedRoot;
import cloud.pangeacyber.pangea.audit.utils.Hash;
import cloud.pangeacyber.pangea.exceptions.AuditException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;


final class SearchResultRequest{
    @JsonProperty("id")
    String id;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("include_membership_proof")
    Boolean IncludeMembershipProof;

    @JsonProperty("include_hash")   // Will be removed soon
    Boolean includeHash = true;

    @JsonProperty("include_root")
    Boolean includeRoot = true;     // Will be removed soon, and return by default

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("limit")
    Integer limit = 20;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("offset")
    Integer offset = 0;

    public SearchResultRequest(String id, Boolean includeMembershipProof, Integer limit, Integer offset) {
        this.id = id;
        IncludeMembershipProof = includeMembershipProof;
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
final class ResultResponse extends Response<SearchResultOutput> {}
final class RootResponse extends Response<RootOutput> {}

public class AuditClient extends Client {
    public static String serviceName = "audit";
    LogSigner signer;
    Map<Integer, PublishedRoot> publishedRoots;
    // TODO: declare signer here, and initialize it in null

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

    private LogResponse logPost(Event event, Boolean verbose, String signature, String publicKey)  throws IOException, InterruptedException, PangeaAPIException{
        LogRequest request = new LogRequest(event, verbose, signature, publicKey);
        return doPost("/v1/log", request, LogResponse.class);
    }

    private LogResponse doLog(Event event, boolean sign, Boolean verbose) throws IOException, InterruptedException, PangeaAPIException, CryptoException, JsonProcessingException, Exception{
        String signature = null;
        String publicKey = null;

        if(sign && this.signer == null){
            // trows exception
        }
        else if(sign && this.signer != null){
            ObjectMapper mapper = new ObjectMapper();
            String canEvent = mapper.writeValueAsString(event);
            signature = this.signer.sign(canEvent);
            publicKey = this.signer.getPublicKey();
        }
        return logPost(event, verbose, signature, publicKey);
    }

    public LogResponse log(Event event) throws IOException, InterruptedException, PangeaAPIException, CryptoException, JsonProcessingException, Exception{
        return doLog(event, false, null);
    }

    public LogResponse log(Event event, boolean sign, boolean verbose) throws IOException, InterruptedException, PangeaAPIException, CryptoException, JsonProcessingException, Exception {
        return doLog(event, sign, verbose);
    }

    private RootResponse rootPost(Integer treeSize) throws IOException, InterruptedException, PangeaAPIException {
        RootRequest request = new RootRequest(treeSize);
        return doPost("/v1/root", request, RootResponse.class);        
    }

    public RootResponse root() throws IOException, InterruptedException, PangeaAPIException {
        return rootPost(null);
    }

    public RootResponse root(int treeSize) throws IOException, InterruptedException, PangeaAPIException {
        return rootPost(treeSize);
    }

    private SearchResponse handleSearchResponse(SearchResponse response, boolean verifyConsistency, boolean verifyEvents) throws IOException, InterruptedException, PangeaAPIException, AuditException{

        if(verifyEvents){
            for(SearchEvent searchEvent : response.getResult().getEvents()){
                // verify event hash
                searchEvent.verifyHash();

                // TODO: Not working yet
                // searchEvent.verifySignature();
            }
        }

        Root root = response.getResult().getRoot();

        if(verifyConsistency && root != null){
            // if there is no root, we don't have any record migrated to cold. We cannot verify any proof

            updatePublishedRoots(response.getResult());
            for(SearchEvent searchEvent: response.getResult().getEvents()){
                searchEvent.verifyMembershipProof(Hash.decode(root.getRootHash()));
                searchEvent.verifyConsistency(publishedRoots);    
            }
        }
        return response;
    }

    private void updatePublishedRoots(SearchOutput result){
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
            arweaveRoots = arweave.getPublishedRoots(treeSizes.toArray(sizes));
        } else {
            // TODO: Check this when accept local roots
            return;
        }

        for(Integer treeSize: sizes){
            if(arweaveRoots.containsKey(treeSize)){
                PublishedRoot pubRoot = arweaveRoots.get(treeSize);
                pubRoot.setSource("arweave");
                this.publishedRoots.put(treeSize, pubRoot);
            }
            // TODO: else check pangea roots
        }

    }

    private SearchResponse searchPost(SearchInput request, boolean verifyConsistency, boolean verifyEvents) throws IOException, InterruptedException, PangeaAPIException, AuditException{
        SearchResponse response = doPost("/v1/search", request, SearchResponse.class);
        return handleSearchResponse(response, verifyConsistency, verifyEvents);
    }

    public SearchResponse search(SearchInput input) throws IOException, InterruptedException, PangeaAPIException, AuditException{
        input.setIncludeMembershipProof(true);
        return searchPost(input, true, true);
    }

    public SearchResponse search(SearchInput input, boolean verifyConsistency, boolean verifyEvents) throws IOException, InterruptedException, PangeaAPIException, AuditException {
        if(verifyConsistency){
            input.setIncludeMembershipProof(true);
        }
        return searchPost(input, verifyConsistency, verifyEvents);
    }

}
