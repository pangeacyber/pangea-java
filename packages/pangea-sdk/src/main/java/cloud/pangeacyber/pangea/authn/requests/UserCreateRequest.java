package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.models.Profile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCreateRequest extends BaseRequest {

	/** An email address. */
	@JsonProperty("email")
	String email;

	/** A user profile as a collection of string properties. */
	@JsonProperty("profile")
	Profile profile;

	/** A username. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("username")
	String username;

	private UserCreateRequest(Builder builder) {
		this.email = builder.email;
		this.profile = builder.profile;
		this.username = builder.username;
	}

	public static class Builder {

		String email;
		Profile profile;
		String username;

		public Builder(String email, Profile profile) {
			this.email = email;
			this.profile = profile;
		}

		/**
		 * Add a username to the request parameters.
		 * @param username A username.
		 * @return Builder.
		 */
		public Builder setUsername(String username) {
			this.username = username;
			return this;
		}

		public UserCreateRequest build() {
			return new UserCreateRequest(this);
		}
	}
}
