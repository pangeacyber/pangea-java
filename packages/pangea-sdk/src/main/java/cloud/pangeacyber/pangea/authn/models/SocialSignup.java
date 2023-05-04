package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialSignup {

	@JsonProperty("redirect_uri")
	HashMap<String, String> redirectURI;

	public HashMap<String, String> getRedirectURI() {
		return redirectURI;
	}
}
