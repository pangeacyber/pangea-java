package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @JsonProperty("profile")
    Profile profile;

    @JsonProperty("identity")
    String identity;

    @JsonProperty("email")
    String email;

    @JsonProperty("scopes")
    Scopes scopes;

    public Profile getProfile() {
        return profile;
    }

    public String getIdentity() {
        return identity;
    }

    public String getEmail() {
        return email;
    }

    public Scopes getScopes() {
        return scopes;
    }
}
