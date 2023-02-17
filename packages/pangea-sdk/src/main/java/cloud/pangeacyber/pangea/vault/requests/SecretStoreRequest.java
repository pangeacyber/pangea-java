package cloud.pangeacyber.pangea.vault.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class SecretStoreRequest extends CommonStoreRequest{
    @JsonProperty("secret")
    String secret = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("retain_previous_version")
    Boolean retainPreviousVersion = null;

    protected SecretStoreRequest(SecretStoreRequestBuilder builder){
        super(builder);
        this.secret = builder.secret;
        this.retainPreviousVersion = builder.retainPreviousVersion;
    }

    public String getSecret() {
        return secret;
    }

    public Boolean getRetainPreviousVersion() {
        return retainPreviousVersion;
    }

    public static class SecretStoreRequestBuilder extends CommonStoreRequestBuilder<SecretStoreRequestBuilder> {
        String secret = null;
        Boolean retainPreviousVersion = null;

        public SecretStoreRequestBuilder(String secret) {
            this.secret = secret;
        }

        public SecretStoreRequest build(){
            return new SecretStoreRequest(this);
        }

        public SecretStoreRequestBuilder setRetainPreviousVersion(Boolean retainPreviousVersion) {
            this.retainPreviousVersion = retainPreviousVersion;
            return this;
        }
    }
}
