package cloud.pangeacyber.pangea.authn.results;

import cloud.pangeacyber.pangea.authn.models.IDProviders;
import cloud.pangeacyber.pangea.authn.models.MFAProviders;
import cloud.pangeacyber.pangea.authn.models.Profile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCreateResult {

	@JsonProperty("id")
	String id;

	@JsonProperty("email")
	String email;

	@JsonProperty("profile")
	Profile profile;

	@JsonProperty("id_providers")
	IDProviders idProviders;

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

	public String getEmail() {
		return email;
	}

	public Profile getProfile() {
		return profile;
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

	public String getID() {
		return id;
	}

	public IDProviders getIdProviders() {
		return idProviders;
	}
}
