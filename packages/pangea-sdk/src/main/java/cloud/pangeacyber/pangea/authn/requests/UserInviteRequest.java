package cloud.pangeacyber.pangea.authn.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInviteRequest {

	@JsonProperty("inviter")
	String inviter;

	@JsonProperty("email")
	String email;

	@JsonProperty("callback")
	String callback;

	@JsonProperty("state")
	String state;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("require_mfa")
	Boolean requireMFA;

	private UserInviteRequest(Builder builder) {
		this.email = builder.email;
		this.inviter = builder.inviter;
		this.callback = builder.callback;
		this.state = builder.state;
		this.requireMFA = builder.requireMFA;
	}

	public static class Builder {

		String inviter;
		String email;
		String callback;
		String state;
		Boolean requireMFA;

		public Builder(String inviter, String email, String callback, String state) {
			this.inviter = inviter;
			this.email = email;
			this.callback = callback;
			this.state = state;
		}

		public UserInviteRequest build() {
			return new UserInviteRequest(this);
		}

		public void setRequireMFA(Boolean requireMFA) {
			this.requireMFA = requireMFA;
		}
	}
}