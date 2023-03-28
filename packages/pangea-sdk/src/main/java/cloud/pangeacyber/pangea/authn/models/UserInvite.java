package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInvite {

	@JsonProperty("id")
	String id;

	@JsonProperty("inviter")
	String inviter;

	@JsonProperty("invite_org")
	String inviteOrg;

	@JsonProperty("email")
	String email;

	@JsonProperty("callback")
	String callback;

	@JsonProperty("state")
	String state;

	@JsonProperty("require_mfa")
	Boolean require_mfa;

	@JsonProperty("created_at")
	String createdAt;

	@JsonProperty("expire")
	String expire;

	public String getId() {
		return id;
	}

	public String getInviter() {
		return inviter;
	}

	public String getInviteOrg() {
		return inviteOrg;
	}

	public String getEmail() {
		return email;
	}

	public String getCallback() {
		return callback;
	}

	public String getState() {
		return state;
	}

	public Boolean getRequire_mfa() {
		return require_mfa;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getExpire() {
		return expire;
	}
}
