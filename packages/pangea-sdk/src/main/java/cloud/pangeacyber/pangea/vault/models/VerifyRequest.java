package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class VerifyRequest {
    @JsonProperty("id")
    String id;

    @JsonProperty("message")
    String message;

    @JsonProperty("signature")
    String signature;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("version")
    Integer version = null;

    public VerifyRequest(String id, String message, String signature) {
        this.id = id;
        this.message = message;
        this.signature = signature;
    }

    public VerifyRequest(String id, String message, String signature, Integer version) {
        this.id = id;
        this.message = message;
        this.signature = signature;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
