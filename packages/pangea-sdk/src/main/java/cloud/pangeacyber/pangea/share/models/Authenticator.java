package cloud.pangeacyber.pangea.share.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Authenticator {

	@JsonProperty("auth_type")
	private AuthenticatorType authType;

	@JsonProperty("auth_context")
	private String authContext;

	public Authenticator() {}

	public Authenticator(AuthenticatorType authType, String authContext) {
		this.authType = authType;
		this.authContext = authContext;
	}

	public AuthenticatorType getAuthType() {
		return authType;
	}

	public String getAuthContext() {
		return authContext;
	}
}
