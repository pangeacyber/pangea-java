package cloud.pangeacyber.pangea.authn.results;

import cloud.pangeacyber.pangea.authn.models.IDProviders;
import cloud.pangeacyber.pangea.authn.models.MFAProviders;
import cloud.pangeacyber.pangea.authn.models.Profile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileGetResult {

	@JsonProperty("id")
	String id;

	@JsonProperty("email")
	String email;

	@JsonProperty("profile")
	Profile profile;

	@JsonProperty("id_providers")
	IDProviders idProviders;

	@JsonProperty("mfa_providers")
	MFAProviders mfaProviders;

	@JsonProperty("require_mfa")
	boolean requireMFA;

	@JsonProperty("verified")
	boolean verified;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("disabled")
	Boolean disabled;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("last_login_at")
	String lastLoginAt;

	@JsonProperty("created_at")
	String createdAt;

	public String getCreatedAt() {
		return createdAt;
	}

	public String getID() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public Profile getProfile() {
		return profile;
	}

	public IDProviders getIdProviders() {
		return idProviders;
	}

	public MFAProviders getMfaProviders() {
		return mfaProviders;
	}

	public boolean isRequireMFA() {
		return requireMFA;
	}

	public boolean isVerified() {
		return verified;
	}

	public String getLastLoginAt() {
		return lastLoginAt;
	}

	public Boolean getDisabled() {
		return disabled;
	}
}
