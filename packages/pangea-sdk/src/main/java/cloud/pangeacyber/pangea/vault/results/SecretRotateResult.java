package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SecretRotateResult extends CommonRotateResult{
    @JsonProperty("secret")
    String secret = null;

    public SecretRotateResult() {
    }

    public SecretRotateResult(String type, String id, Integer version, String secret) {
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
