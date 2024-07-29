package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.models.IDProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUpdateRequest extends BaseRequest {

	/** An email address. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("email")
	String email;

	/** The identity of a user or a service. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id")
	IDProvider id;

	/** A username. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("username")
	String username;

	/**
	 * New disabled value. Disabling a user account will prevent them from
	 * logging in.
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("disabled")
	Boolean disabled;

	/**
	 * Unlock a user account if it has been locked out due to failed
	 * authentication attempts.
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("unlock")
	Boolean unlock;

	private UserUpdateRequest(Builder builder) {
		this.id = builder.id;
		this.email = builder.email;
		this.username = builder.username;
		this.disabled = builder.disabled;
		this.unlock = builder.unlock;
	}

	public static class Builder {

		IDProvider id;
		String email;
		String username;
		Boolean disabled;
		Boolean unlock;

		public Builder() {}

		public UserUpdateRequest build() {
			return new UserUpdateRequest(this);
		}

		public Builder setId(IDProvider id) {
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

		public Builder setDisabled(Boolean disabled) {
			this.disabled = disabled;
			return this;
		}

		public Builder setUnlock(Boolean unlock) {
			this.unlock = unlock;
			return this;
		}
	}
}
