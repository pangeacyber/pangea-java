package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAuthenticatorsListRequest extends BaseRequest {

	/** The identity of a user or a service. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id")
	private String id;

	/** An email address. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("email")
	private String email;

	/** A username. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("username")
	private String username;

	private UserAuthenticatorsListRequest(Builder builder) {
		this.id = builder.id;
		this.email = builder.email;
		this.username = builder.username;
	}

	public static class Builder {

		private String id = null;
		private String email = null;
		private String username = null;

		public Builder() {}

		public Builder setID(String id) {
			this.id = id;
			return this;
		}

		public Builder setEmail(String email) {
			this.email = email;
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

		public UserAuthenticatorsListRequest build() {
			return new UserAuthenticatorsListRequest(this);
		}
	}

	public String getID() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}
}
