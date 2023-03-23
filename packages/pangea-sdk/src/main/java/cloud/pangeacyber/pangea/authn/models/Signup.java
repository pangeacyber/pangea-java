package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Signup {

	@JsonProperty("social_signup")
	SocialSignup socialSignup;

	@JsonProperty("password_signup")
	PasswordSignup passwordSignup;

	public SocialSignup getSocialSignup() {
		return socialSignup;
	}

	public PasswordSignup getPasswordSignup() {
		return passwordSignup;
	}
}
