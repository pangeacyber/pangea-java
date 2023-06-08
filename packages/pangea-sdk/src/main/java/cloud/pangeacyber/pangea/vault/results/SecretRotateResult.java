package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SecretRotateResult extends CommonRotateResult {

	@JsonProperty("secret")
	String secret = null;

	public SecretRotateResult() {
		super();
	}

	public String getSecret() {
		return secret;
	}
}
