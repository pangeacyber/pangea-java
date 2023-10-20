package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.models.IDProvider;
import cloud.pangeacyber.pangea.authn.models.Profile;
import cloud.pangeacyber.pangea.authn.models.Scopes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCreateRequest extends BaseRequest {

	@JsonProperty("email")
	String email;

	@JsonProperty("profile")
	Profile profile;

	private UserCreateRequest(Builder builder) {
		this.email = builder.email;
		this.profile = builder.profile;
	}

	public static class Builder {

		String email;
		Profile profile;

		public Builder(String email, Profile profile) {
			this.email = email;
			this.profile = profile;
		}

		public UserCreateRequest build() {
			return new UserCreateRequest(this);
		}

	}
}
