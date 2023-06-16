package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.models.IDProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUpdateRequest extends BaseRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("email")
	String email;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("authenticator")
	String authenticator;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id")
	IDProvider id;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("disabled")
	Boolean disabled;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("require_mfa")
	Boolean requireMFA;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("verified")
	Boolean verified;

	private UserUpdateRequest(Builder builder) {
		this.id = builder.id;
		this.email = builder.email;
		this.authenticator = builder.authenticator;
		this.disabled = builder.disabled;
		this.requireMFA = builder.requireMFA;
		this.verified = builder.verified;
	}

	public static class Builder {

		IDProvider id;
		String email;
		String authenticator;
		Boolean disabled;
		Boolean requireMFA;
		Boolean verified;

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

		public Builder setAuthenticator(String authenticator) {
			this.authenticator = authenticator;
			return this;
		}

		public Builder setDisabled(Boolean disabled) {
			this.disabled = disabled;
			return this;
		}

		public Builder setRequireMFA(Boolean requireMFA) {
			this.requireMFA = requireMFA;
			return this;
		}

		public Builder setVerified(Boolean verified) {
			this.verified = verified;
			return this;
		}
	}
}
