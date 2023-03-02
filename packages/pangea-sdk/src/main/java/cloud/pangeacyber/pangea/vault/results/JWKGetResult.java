package cloud.pangeacyber.pangea.vault.results;

import cloud.pangeacyber.pangea.vault.models.JWKSet;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JWKGetResult {

	@JsonProperty("jwk")
	JWKSet jwk;

	public JWKGetResult() {}

	public JWKSet getJWK() {
		return jwk;
	}
}
