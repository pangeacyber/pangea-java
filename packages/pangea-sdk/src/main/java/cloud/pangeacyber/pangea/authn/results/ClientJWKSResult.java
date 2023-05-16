package cloud.pangeacyber.pangea.authn.results;

import cloud.pangeacyber.pangea.vault.models.JWK;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientJWKSResult {

	@JsonProperty("keys")
	JWK[] keys;

	public ClientJWKSResult() {}

	public JWK[] getKeys() {
		return keys;
	}
}
