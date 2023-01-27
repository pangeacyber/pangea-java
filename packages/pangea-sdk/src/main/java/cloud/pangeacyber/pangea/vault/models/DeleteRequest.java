package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteRequest {
    @JsonProperty("id")
    String id;

    public DeleteRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
