package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	@JsonProperty("id")
	String id;

	@JsonProperty("email")
	String email;

	@JsonProperty("profile")
	Profile profile;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("scopes")
	Scopes scopes;

	@JsonProperty("id_providers")
	IDProviders idProviders;

	@JsonProperty("mfa_providers")
	MFAProviders mfaProviders;

	@JsonProperty("require_mfa")
	Boolean requireMFA;

	@JsonProperty("verified")
	Boolean verified;

	@JsonProperty("disabled")
	Boolean disabled;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("last_login_at")
	String lastLoginAt;

	@JsonProperty("created_at")
	String createdAt;

	@JsonProperty("identity")
	String identity;

	public Profile getProfile() {
		return profile;
	}

	public String getID() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public Scopes getScopes() {
		return scopes;
	}

	public ArrayList<String> getIdProviders() {
		return idProviders;
	}

	public ArrayList<String> getMfaProviders() {
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

	public String getCreatedAt() {
		return createdAt;
	}

	public String getIdentity() {
		return identity;
	}
}
