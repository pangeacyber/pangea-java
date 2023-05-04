package cloud.pangeacyber.pangea.authn.results;

import cloud.pangeacyber.pangea.authn.models.IDProviders;
import cloud.pangeacyber.pangea.authn.models.Profile;
import cloud.pangeacyber.pangea.authn.models.Scopes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVerifyResult {

	@JsonProperty("id")
	String id;

	@JsonProperty("email")
	String email;

	@JsonProperty("profile")
	Profile profile;

	@JsonProperty("scopes")
	Scopes scopes;

	@JsonProperty("id_providers")
	IDProviders idProviders;

	@JsonProperty("mfa_providers")
	String[] mfaProviders;

	@JsonProperty("require_mfa")
	boolean requireMFA;

	@JsonProperty("verified")
	boolean verified;

	@JsonProperty("diabled")
	boolean disabled;

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

	public Scopes getScopes() {
		return scopes;
	}

	public IDProviders getIdProviders() {
		return idProviders;
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

	public String[] getMfaProviders() {
		return mfaProviders;
	}
}
