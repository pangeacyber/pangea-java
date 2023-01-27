package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateResult {
    @JsonProperty("id")
    String id;

    public UpdateResult() {
    }

    public UpdateResult(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
