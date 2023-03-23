package cloud.pangeacyber.pangea.authn.results;

import cloud.pangeacyber.pangea.authn.models.MFAProviders;
import cloud.pangeacyber.pangea.authn.models.Profile;
import cloud.pangeacyber.pangea.authn.models.Scopes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUpdateResult {

	@JsonProperty("identity")
	String identity;

	@JsonProperty("email")
	String email;

	@JsonProperty("profile")
	Profile profile;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("scopes")
	Scopes scopes;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id_provider")
	String idProvider;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("mfa_providers")
	MFAProviders mfaProviders;

	@JsonProperty("require_mfa")
	Boolean requireMFA;

	@JsonProperty("verified")
	Boolean verified;

	@JsonProperty("disabled")
	Boolean disabled;

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

	public String getIdProvider() {
		return idProvider;
	}

	public MFAProviders getMfaProviders() {
		return mfaProviders;
	}

	public Boolean getRequireMFA() {
		return requireMFA;
	}

	public Boolean getVerified() {
		return verified;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public String getLastLoginAt() {
		return lastLoginAt;
	}
}
