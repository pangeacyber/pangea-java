package cloud.pangeacyber.pangea.vault.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class PangeaTokenStoreRequest extends CommonStoreRequest{
    @JsonProperty("token")
    String token = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("retain_previous_version")
    Boolean retainPreviousVersion = null;

    protected PangeaTokenStoreRequest(PangeaTokenStoreRequestBuilder builder){
        super(builder);
        this.token = builder.token;
        this.retainPreviousVersion = builder.retainPreviousVersion;
    }

    public String getToken() {
        return token;
    }

    public Boolean getRetainPreviousVersion() {
        return retainPreviousVersion;
    }

    public static class PangeaTokenStoreRequestBuilder extends CommonStoreRequestBuilder<PangeaTokenStoreRequestBuilder> {
        String token = null;
        Boolean retainPreviousVersion = null;

        public PangeaTokenStoreRequestBuilder(String token) {
            this.token = token;
        }

        public PangeaTokenStoreRequest build(){
            return new PangeaTokenStoreRequest(this);
        }

        public PangeaTokenStoreRequestBuilder setRetainPreviousVersion(Boolean retainPreviousVersion) {
            this.retainPreviousVersion = retainPreviousVersion;
            return this;
        }
    }
}
