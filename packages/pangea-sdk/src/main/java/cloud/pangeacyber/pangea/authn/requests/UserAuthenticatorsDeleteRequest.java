package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAuthenticatorsDeleteRequest extends BaseRequest {

	@JsonProperty("authenticator_id")
	private String authenticatorID;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id")
	private String id;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("email")
	private String email;

	private UserAuthenticatorsDeleteRequest(Builder builder) {
		this.authenticatorID = builder.authenticatorID;
		this.id = builder.id;
		this.email = builder.email;
	}

	public static class Builder {

		private String authenticatorID;
		private String id = null;
		private String email = null;

		public Builder(String authenticatorID) {
			this.authenticatorID = authenticatorID;
		}

		public Builder setID(String id) {
			this.id = id;
			return this;
		}

		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}

		public UserAuthenticatorsDeleteRequest build() {
			return new UserAuthenticatorsDeleteRequest(this);
		}
	}

	public String getAuthenticatorID() {
		return authenticatorID;
	}

	public String getID() {
		return id;
	}

	public String getEmail() {
		return email;
	}
}
