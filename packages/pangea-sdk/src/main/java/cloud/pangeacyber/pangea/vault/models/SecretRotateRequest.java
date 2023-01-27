package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SecretRotateRequest extends CommonRotateRequest{
    @JsonProperty("secret")
    String secret = null;

    public SecretRotateRequest(String id, String secret) {
        super(id);
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

}
