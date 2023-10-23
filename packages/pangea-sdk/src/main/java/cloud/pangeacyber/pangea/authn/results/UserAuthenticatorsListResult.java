package cloud.pangeacyber.pangea.authn.results;

import cloud.pangeacyber.pangea.authn.models.Authenticator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAuthenticatorsListResult {

	@JsonProperty("authenticators")
	Authenticator[] authenticators;

	public Authenticator[] getAuthenticators() {
		return authenticators;
	}
}
