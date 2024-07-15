package cloud.pangeacyber.pangea.authn.results;

import cloud.pangeacyber.pangea.authn.models.Authenticator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAuthenticatorsListResult {

	/** A list of authenticators. */
	@JsonProperty("authenticators")
	Authenticator[] authenticators;

	/** A list of authenticators. */
	public Authenticator[] getAuthenticators() {
		return authenticators;
	}
}
