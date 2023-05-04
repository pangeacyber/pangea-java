package cloud.pangeacyber.pangea.authn.results;

import cloud.pangeacyber.pangea.authn.models.UserInvite;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInviteListResult {

	@JsonProperty("invites")
	UserInvite[] invites;

	@JsonProperty("last")
	String last;

	@JsonProperty("count")
	Integer count;

	public UserInvite[] getInvites() {
		return invites;
	}

	public String getLast() {
		return last;
	}

	public Integer getCount() {
		return count;
	}
}
