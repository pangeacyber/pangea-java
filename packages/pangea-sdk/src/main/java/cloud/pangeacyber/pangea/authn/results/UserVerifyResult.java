package cloud.pangeacyber.pangea.authn.results;

import cloud.pangeacyber.pangea.authn.models.IDProvider;
import cloud.pangeacyber.pangea.authn.models.Profile;
import cloud.pangeacyber.pangea.authn.models.Scopes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

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

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id_provider")
	IDProvider idProvider;

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

	public String[] getMfaProviders() {
		return mfaProviders;
	}
}
