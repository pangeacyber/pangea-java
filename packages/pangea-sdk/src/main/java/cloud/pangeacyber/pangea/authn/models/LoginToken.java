package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginToken extends SessionToken {

	@JsonProperty("token")
	String token;

	public String getToken() {
		return token;
	}
}
