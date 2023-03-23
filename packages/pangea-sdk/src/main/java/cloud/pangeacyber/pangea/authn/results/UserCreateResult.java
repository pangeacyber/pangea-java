package cloud.pangeacyber.pangea.authn.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cloud.pangeacyber.pangea.authn.models.MFAProviders;
import cloud.pangeacyber.pangea.authn.models.Profile;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCreateResult {
    @JsonProperty("identity")
    String identity;

    @JsonProperty("email")
    String email;

    @JsonProperty("profile")
    Profile profile;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("id_provider")
    String id_provider;

    @JsonProperty("require_mfa")
    Boolean require_mfa;

    @JsonProperty("verified")
    Boolean verified;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("last_login_at")
    String lastLoginAt;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("disabled")
    Boolean disabled;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("mfa_providers")
    MFAProviders mfaProviders;

    public String getIdentity() {
        return identity;
    }

    public String getEmail() {
        return email;
    }

    public Profile getProfile() {
        return profile;
    }

    public String getId_provider() {
        return id_provider;
    }

    public Boolean getRequire_mfa() {
        return require_mfa;
    }

    public Boolean getVerified() {
        return verified;
    }

    public String getLastLoginAt() {
        return lastLoginAt;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public MFAProviders getMfaProviders() {
        return mfaProviders;
    }
}
