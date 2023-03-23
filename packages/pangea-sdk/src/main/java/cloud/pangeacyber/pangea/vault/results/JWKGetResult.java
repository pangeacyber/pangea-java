package cloud.pangeacyber.pangea.vault.results;

import cloud.pangeacyber.pangea.vault.models.JWK;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JWKGetResult {

	@JsonProperty("keys")
	JWK[] keys;

	public JWKGetResult() {}

	public JWK[] getKeys() {
		return keys;
	}
}
