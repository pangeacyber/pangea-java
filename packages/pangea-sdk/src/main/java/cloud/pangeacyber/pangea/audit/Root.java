package cloud.pangeacyber.pangea.audit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Root {
    @JsonProperty("tree_name")
    String treeName;

    @JsonProperty("size")
    int size;

    @JsonProperty("root_hash")
    String rootHash;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("url")
    String url;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("published_at")
    String publishedAt;

    @JsonProperty("consistency_proof")
    String[] consistencyProof;

    public String getTreeName() {
        return treeName;
    }

    public int getSize() {
        return size;
    }

    public String getRootHash() {
        return rootHash;
    }

    public String getUrl() {
        return url;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String[] getConsistencyProof() {
        return consistencyProof;
    }
}
