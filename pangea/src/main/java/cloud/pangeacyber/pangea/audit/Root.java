package cloud.pangeacyber.pangea.audit;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Root {
    @JsonProperty("tree_name")
    String treeName;

    @JsonProperty("size")
    int size;

    @JsonProperty("root_hash")
    String rootHash;

    @JsonProperty("url")
    String url;

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
