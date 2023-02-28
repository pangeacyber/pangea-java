package cloud.pangeacyber.pangea.vault.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.vault.models.ItemVersionState;

public class SecretRotateRequest extends CommonRotateRequest{
    @JsonProperty("secret")
    String secret = null;

    protected SecretRotateRequest(SecretRotateRequestBuilder builder) {
        super(builder);
        this.secret = builder.secret;
    }

    public String getSecret() {
        return secret;
    }


    public static class SecretRotateRequestBuilder extends CommonRotateRequestBuilder<SecretRotateRequestBuilder> {
        String secret = null;

        public SecretRotateRequest build(){
            return new SecretRotateRequest(this);
        }

        public SecretRotateRequestBuilder(String id, String secret, ItemVersionState state) {
            super(id, state);
            this.secret = secret;
        }

        public SecretRotateRequestBuilder setSecret(String secret) {
            this.secret = secret;
            return this;
        }
    }
}
