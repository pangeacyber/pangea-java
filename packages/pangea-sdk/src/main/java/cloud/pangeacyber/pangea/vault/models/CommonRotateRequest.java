package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommonRotateRequest {
    @JsonProperty("id")
    String id;

    public CommonRotateRequest() {
    }

    public CommonRotateRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
