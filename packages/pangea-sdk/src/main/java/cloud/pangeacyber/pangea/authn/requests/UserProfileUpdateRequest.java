package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.authn.models.MFAProvider;
import cloud.pangeacyber.pangea.authn.models.Profile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

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

	private UserProfileUpdateRequest(UserProfileUpdateRequestBuilder builder) {
		this.profile = builder.profile;
		this.email = builder.email;
		this.identity = builder.identity;
	}

	public static class UserProfileUpdateRequestBuilder {

		Profile profile;
		String email;
		String identity;

		public UserProfileUpdateRequestBuilder(Profile profile) {
			this.profile = profile;
		}

		public UserProfileUpdateRequest build() {
			return new UserProfileUpdateRequest(this);
		}

		public UserProfileUpdateRequestBuilder setEmail(String email) {
			this.email = email;
			return this;
		}

		public UserProfileUpdateRequestBuilder setIdentity(String identity) {
			this.identity = identity;
			return this;
		}
	}
}
