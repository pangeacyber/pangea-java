package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JWTVerifyResult {

	@JsonProperty("valid_signature")
	boolean validSignature;

	public JWTVerifyResult() {}

	public boolean isValidSignature() {
		return validSignature;
	}
}
