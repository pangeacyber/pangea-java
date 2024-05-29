package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.models.Profile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileUpdateRequest extends BaseRequest {

	/** Updates to a user profile. */
	@JsonProperty("profile")
	Profile profile;

	/** An email address. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("email")
	String email;

	/** The identity of a user or a service. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id")
	String id;

	/** A username. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("username")
	String username;

	private UserProfileUpdateRequest(Builder builder) {
		this.profile = builder.profile;
		this.email = builder.email;
		this.id = builder.id;
		this.username = builder.username;
	}

	public static class Builder {

		Profile profile;
		String email;
		String id;
		String username;

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

		/**
		 * Add a username to the request parameters.
		 * @param username A username.
		 * @return Builder.
		 */
		public Builder setUsername(String username) {
			this.username = username;
			return this;
		}
	}
}
