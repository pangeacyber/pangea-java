package cloud.pangeacyber.pangea.authn.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cloud.pangeacyber.pangea.authn.models.IDProvider;
import cloud.pangeacyber.pangea.authn.models.Profile;
import cloud.pangeacyber.pangea.authn.models.Scopes;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCreateRequest {
    @JsonProperty("email")
    String email;

    @JsonProperty("authenticator")
    String authenticator;

    @JsonProperty("id_provider")
    IDProvider idProvider;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("verified")
    Boolean verified;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("require_mfa")
    Boolean requireMFA;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("scopes")
    Scopes scopes;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("profile")
    Profile profile;

    private UserCreateRequest(UserCreateRequestBuilder builder) {
        this.email = builder.email;
        this.authenticator = builder.authenticator;
        this.idProvider = builder.idProvider;
        this.verified = builder.verified;
        this.requireMFA = builder.requireMFA;
        this.scopes = builder.scopes;
        this.profile = builder.profile;
	}

	public static class UserCreateRequestBuilder{
        String email;
        String authenticator;
        IDProvider idProvider;
        Boolean verified;
        Boolean requireMFA;
        Scopes scopes;
        Profile profile;

		public UserCreateRequestBuilder(String email, String authenticator, IDProvider idProvider) {
            this.email = email;
            this.authenticator = authenticator;
            this.idProvider = idProvider;
        }

		public UserCreateRequest build(){
			return new UserCreateRequest(this);
		}

        public UserCreateRequestBuilder setVerified(Boolean verified) {
            this.verified = verified;
            return this;
        }

        public UserCreateRequestBuilder setRequireMFA(Boolean requireMFA) {
            this.requireMFA = requireMFA;
            return this;
        }

        public UserCreateRequestBuilder setScopes(Scopes scopes) {
            this.scopes = scopes;
            return this;
        }

        public UserCreateRequestBuilder setProfile(Profile profile) {
            this.profile = profile;
            return this;
        }

	}

}
