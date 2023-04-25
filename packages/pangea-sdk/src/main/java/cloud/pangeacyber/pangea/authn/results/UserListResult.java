package cloud.pangeacyber.pangea.authn.results;

import cloud.pangeacyber.pangea.authn.models.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserListResult {

	@JsonProperty("users")
	User[] users;

	@JsonProperty("last")
	String last;

	@JsonProperty("count")
	Integer count;

	public User[] getUsers() {
		return users;
	}

	public String getLast() {
		return last;
	}

	public Integer getCount() {
		return count;
	}
}
