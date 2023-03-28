package cloud.pangeacyber.pangea.authn.results;

import cloud.pangeacyber.pangea.authn.models.TOTPsecret;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMFAStartResult extends CommonFlowResult {

	@JsonProperty("totp_secret")
	TOTPsecret totpSecret;

	public TOTPsecret getTotpSecret() {
		return totpSecret;
	}
}
