package cloud.pangeacyber.pangea.authn.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.authn.models.IDProvider;
import cloud.pangeacyber.pangea.authn.models.Profile;
import cloud.pangeacyber.pangea.authn.models.Scopes;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVerifyResult {
    @JsonProperty("identity")
    String identity;

    @JsonProperty("email")
    String email;

    @JsonProperty("profile")
    Profile profile;

    @JsonProperty("scopes")
    Scopes scopes;

    @JsonProperty("id_provider")
    IDProvider idProvider;

    @JsonProperty("require_mfa")
    boolean requireMFA;

    @JsonProperty("verified")
    boolean verified;

    @JsonProperty("diabled")
    boolean disabled;

    @JsonProperty("last_login_at")
    String lastLoginAt;

    public String getIdentity() {
        return identity;
    }

    public String getEmail() {
        return email;
    }

    public Profile getProfile() {
        return profile;
    }

    public Scopes getScopes() {
        return scopes;
    }

    public IDProvider getIdProvider() {
        return idProvider;
    }

    public boolean isRequireMFA() {
        return requireMFA;
    }

    public boolean isVerified() {
        return verified;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public String getLastLoginAt() {
        return lastLoginAt;
    }

}
