package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteResult {
    @JsonProperty("id")
    String id;

    public DeleteResult() {
    }

    public DeleteResult(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
