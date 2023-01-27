package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class DecryptRequest {
    @JsonProperty("id")
    String id;

    @JsonProperty("cipher_text")
    String cipherText;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("version")
    Integer version = null;

    public DecryptRequest(String id, String cipherText, Integer version) {
        this.id = id;
        this.cipherText = cipherText;
        this.version = version;
    }

    public DecryptRequest(String id, String cipherText) {
        this.id = id;
        this.cipherText = cipherText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCipherText() {
        return cipherText;
    }

    public void setCipherText(String cipherText) {
        this.cipherText = cipherText;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }


}
