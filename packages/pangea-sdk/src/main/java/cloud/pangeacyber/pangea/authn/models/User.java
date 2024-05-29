package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	/** The identity of a user or a service. */
	@JsonProperty("id")
	private String id;

	/** An email address. */
	@JsonProperty("email")
	private String email;

	/** A username. */
	@JsonProperty("username")
	private String username;

	/** A user profile as a collection of string properties. */
	@JsonProperty("profile")
	private Profile profile;

	/** True if the user's email has been verified. */
	@JsonProperty("verified")
	private boolean verified;

	/** True if the service administrator has disabled user account. */
	@JsonProperty("disabled")
	private boolean disabled;

	/** An ID for an agreement. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("accepted_eula_id")
	private String acceptedEulaId;

	/** An ID for an agreement. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("accepted_privacy_policy_id")
	private String acceptedPrivacyPolicyId;

	/** A time in ISO-8601 format. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("last_login_at")
	private String lastLoginAt;

	/** A time in ISO-8601 format. */
	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("login_count")
	private int loginCount;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("last_login_ip")
	private String lastLoginIp;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("last_login_city")
	private String lastLoginCity;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("last_login_country")
	private String lastLoginCountry;

	/** A list of authenticators. */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("authenticators")
	Authenticator[] authenticators;

	public String getID() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public Profile getProfile() {
		return profile;
	}

	public boolean isVerified() {
		return verified;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public String getAcceptedEulaId() {
		return acceptedEulaId;
	}

	public String getAcceptedPrivacyPolicyId() {
		return acceptedPrivacyPolicyId;
	}

	public String getLastLoginAt() {
		return lastLoginAt;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public String getLastLoginCity() {
		return lastLoginCity;
	}

	public String getLastLoginCountry() {
		return lastLoginCountry;
	}

	public Authenticator[] getAuthenticators() {
		return authenticators;
	}
}
