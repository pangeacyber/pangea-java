package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.authn.models.IDProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUpdateRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("email")
	String email;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("authenticator")
	String authenticator;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("identity")
	IDProvider identity;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("disabled")
	Boolean disabled;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("require_mfa")
	Boolean requireMFA;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("verified")
	Boolean verified;

	private UserUpdateRequest(UserUpdateRequestBuilder builder) {
		this.identity = builder.identity;
		this.email = builder.email;
		this.authenticator = builder.authenticator;
		this.disabled = builder.disabled;
		this.requireMFA = builder.requireMFA;
		this.verified = builder.verified;
	}

	public static class UserUpdateRequestBuilder {

		IDProvider identity;
		String email;
		String authenticator;
		Boolean disabled;
		Boolean requireMFA;
		Boolean verified;

		public UserUpdateRequestBuilder() {}

		public UserUpdateRequest build() {
			return new UserUpdateRequest(this);
		}

		public UserUpdateRequestBuilder setIdentity(IDProvider identity) {
			this.identity = identity;
			return this;
		}

		public UserUpdateRequestBuilder setEmail(String email) {
			this.email = email;
			return this;
		}

		public UserUpdateRequestBuilder setAuthenticator(String authenticator) {
			this.authenticator = authenticator;
			return this;
		}

		public UserUpdateRequestBuilder setDisabled(Boolean disabled) {
			this.disabled = disabled;
			return this;
		}

		public UserUpdateRequestBuilder setRequireMFA(Boolean requireMFA) {
			this.requireMFA = requireMFA;
			return this;
		}

		public UserUpdateRequestBuilder setVerified(Boolean verified) {
			this.verified = verified;
			return this;
		}
	}
}
