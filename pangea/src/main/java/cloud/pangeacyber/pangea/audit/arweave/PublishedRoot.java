package cloud.pangeacyber.pangea.audit.arweave;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;


public class PublishedRoot {
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("size")
    int size;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("root_hash")
    String rootHash;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("published_at")
    String publishedAt;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("consistency_proof")
    String[] consistencyProof;

    @JsonIgnore
    String source = "unknown";

    public void setSource(String source) {
        this.source = source;
    }

    public int getSize() {
        return size;
    }

    public String getRootHash() {
        return rootHash;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String[] getConsistencyProof() {
        return consistencyProof;
    }

    public String getSource() {
        return source;
    }
}
