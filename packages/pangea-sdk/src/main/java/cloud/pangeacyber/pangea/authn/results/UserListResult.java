package cloud.pangeacyber.pangea.authn.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.authn.models.User;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserListResult {
    @JsonProperty("users")
    User[] users;

    public User[] getUsers() {
        return users;
    }
}
