package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JWTSignResult {

	@JsonProperty("jws")
	String jws;

	public JWTSignResult() {}

	public String getJws() {
		return jws;
	}
}
