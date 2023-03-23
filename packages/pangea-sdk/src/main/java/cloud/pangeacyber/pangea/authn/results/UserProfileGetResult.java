package cloud.pangeacyber.pangea.authn.results;

import cloud.pangeacyber.pangea.authn.models.MFAProviders;
import cloud.pangeacyber.pangea.authn.models.Profile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileGetResult {

	@JsonProperty("identity")
	String identity;

	@JsonProperty("email")
	String email;

	@JsonProperty("profile")
	Profile profile;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id_provider")
	String idProvider;

	@JsonProperty("mfa_providers")
	MFAProviders mfaProviders;

	@JsonProperty("require_mfa")
	boolean requireMFA;

	@JsonProperty("verified")
	boolean verified;

	@JsonProperty("last_login_at")
	String lastLoginAt;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("disabled")
	Boolean disabled;

	public String getIdentity() {
		return identity;
	}

	public String getEmail() {
		return email;
	}

	public Profile getProfile() {
		return profile;
	}

	public String getIdProvider() {
		return idProvider;
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
