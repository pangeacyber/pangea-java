package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInviteRequest extends BaseRequest {

	@JsonProperty("inviter")
	String inviter;

	@JsonProperty("email")
	String email;

	@JsonProperty("callback")
	String callback;

	@JsonProperty("state")
	String state;

	private UserInviteRequest(Builder builder) {
		this.email = builder.email;
		this.inviter = builder.inviter;
		this.callback = builder.callback;
		this.state = builder.state;
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
	}
}
