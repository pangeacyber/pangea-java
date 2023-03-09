package cloud.pangeacyber.pangea.vault.results;

import cloud.pangeacyber.pangea.vault.models.JWKSet;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JWKGetResult {

	@JsonProperty("jwk")
	JWKSet jwk;

	public JWKGetResult() {}

	public JWKSet getJWK() {
		return jwk;
	}
}
