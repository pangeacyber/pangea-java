package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SecretStoreResult extends CommonStoreResult{
    @JsonProperty("secret")
    String secret = null;

    public SecretStoreResult() {
    }

    public SecretStoreResult(String type, String id, Integer version, String secret) {
        super(type, id, version);
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

}
