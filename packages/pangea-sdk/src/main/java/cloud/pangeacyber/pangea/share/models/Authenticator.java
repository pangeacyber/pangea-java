package cloud.pangeacyber.pangea.share.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Authenticator {

	/** An authentication mechanism */
	@JsonProperty("auth_type")
	private AuthenticatorType authType;

	/** An email address, a phone number or a password to access the link */
	@JsonProperty("auth_context")
	private String authContext;

	public Authenticator() {}

	public Authenticator(AuthenticatorType authType, String authContext) {
		this.authType = authType;
		this.authContext = authContext;
	}

	/** An authentication mechanism */
	public AuthenticatorType getAuthType() {
		return authType;
	}

	/** An email address, a phone number or a password to access the link */
	public String getAuthContext() {
		return authContext;
	}
}
