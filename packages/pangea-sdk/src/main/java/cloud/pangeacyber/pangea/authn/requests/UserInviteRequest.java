package cloud.pangeacyber.pangea.authn.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cloud.pangeacyber.pangea.authn.models.IDProvider;
import cloud.pangeacyber.pangea.authn.models.Profile;
import cloud.pangeacyber.pangea.authn.models.Scopes;

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
    @JsonProperty("invite_org")
    String inviteOrg;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("require_mfa")
    Boolean requireMFA;

    private UserInviteRequest(UserInviteRequestBuilder builder) {
        this.email = builder.email;
        this.inviter = builder.inviter;
        this.callback = builder.callback;
        this.state = builder.state;
        this.inviteOrg = builder.inviteOrg;
        this.requireMFA = builder.requireMFA;
	}

	public static class UserInviteRequestBuilder{
        String inviter;
        String email;
        String callback;
        String state;
        String inviteOrg;
        Boolean requireMFA;

		public UserInviteRequestBuilder(String inviter, String email, String callback, String state) {
            this.inviter = inviter;
            this.email = email;
            this.callback = callback;
            this.state = state;
        }

        public UserInviteRequest build(){
			return new UserInviteRequest(this);
		}

        public void setInviteOrg(String inviteOrg) {
            this.inviteOrg = inviteOrg;
        }

        public void setRequireMFA(Boolean requireMFA) {
            this.requireMFA = requireMFA;
        }


	}

}
