package cloud.pangeacyber.pangea.authn.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cloud.pangeacyber.pangea.authn.models.MFAProvider;
import cloud.pangeacyber.pangea.authn.models.Profile;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileUpdateRequest {
    @JsonProperty("profile")
    Profile profile;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("email")
    String email;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("identity")
    String identity;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("require_mfa")
    Boolean requireMFA;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("mfa_value")
    String mfaValue;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("mfa_provider")
    MFAProvider mfaProvider;

    private UserProfileUpdateRequest(UserProfileUpdateRequestBuilder builder) {
        this.profile = builder.profile;
        this.email = builder.email;
        this.identity = builder.identity;
        this.requireMFA = builder.requireMFA;
        this.mfaProvider = builder.mfaProvider;
        this.mfaValue = builder.mfaValue;
	}

	public static class UserProfileUpdateRequestBuilder{
        Profile profile;
        String email;
        String identity;
        Boolean requireMFA;
        String mfaValue;
        MFAProvider mfaProvider;

		public UserProfileUpdateRequestBuilder(Profile profile) {
            this.profile = profile;
        }

		public UserProfileUpdateRequest build(){
			return new UserProfileUpdateRequest(this);
		}

        public void setEmail(String email) {
            this.email = email;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public void setRequireMFA(Boolean requireMFA) {
            this.requireMFA = requireMFA;
        }

        public void setMfaValue(String mfaValue) {
            this.mfaValue = mfaValue;
        }

        public void setMfaProvider(MFAProvider mfaProvider) {
            this.mfaProvider = mfaProvider;
        }
	}
}
