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
	@JsonProperty("id")
	String id;

	private UserProfileUpdateRequest(Builder builder) {
		this.profile = builder.profile;
		this.email = builder.email;
		this.id = builder.id;
	}

	public static class Builder {

		Profile profile;
		String email;
		String id;

		public Builder(Profile profile) {
			this.profile = profile;
		}

		public UserProfileUpdateRequest build() {
			return new UserProfileUpdateRequest(this);
		}

		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}

		public Builder setId(String id) {
			this.id = id;
			return this;
		}
	}
}
