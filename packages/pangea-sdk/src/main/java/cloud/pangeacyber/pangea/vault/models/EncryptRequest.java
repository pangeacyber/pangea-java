package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EncryptRequest {
    @JsonProperty("id")
    String id;

    @JsonProperty("plain_text")
    String plainText;

    public EncryptRequest(String id, String plainText) {
        this.id = id;
        this.plainText = plainText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

}
