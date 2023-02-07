package cloud.pangeacyber.pangea.authn.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cloud.pangeacyber.pangea.authn.models.Profile;
import cloud.pangeacyber.pangea.authn.models.Scopes;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginResult {
    @JsonProperty("token")
    String token;

    @JsonProperty("id")
    String id;

    @JsonProperty("type")
    String type;

    @JsonProperty("life")
    int life;

    @JsonProperty("expire")
    String expire;

    @JsonProperty("identity")
    String identity;

    @JsonProperty("email")
    String email;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("scopes")
    Scopes scopes;

    @JsonProperty("profile")
    Profile profile;

    @JsonProperty("created_at")
    String createdAt;

    public String getToken() {
        return token;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getLife() {
        return life;
    }

    public String getExpire() {
        return expire;
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

    public Profile getProfile() {
        return profile;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
