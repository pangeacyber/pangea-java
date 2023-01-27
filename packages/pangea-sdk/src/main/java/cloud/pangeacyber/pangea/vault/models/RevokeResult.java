package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RevokeResult {
    @JsonProperty("id")
    String id;

    public RevokeResult() {
    }

    public RevokeResult(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
