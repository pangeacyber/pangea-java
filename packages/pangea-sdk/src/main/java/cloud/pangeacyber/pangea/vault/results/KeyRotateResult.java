package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KeyRotateResult extends CommonRotateResult {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("public_key")
	String encodedPublicKey = null;

	@JsonProperty("algorithm")
	String algorithm = null;

	@JsonProperty("purpose")
	String purpose = null;

	public KeyRotateResult() {
		super();
	}

	public String getEncodedPublicKey() {
		return encodedPublicKey;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public String getPurpose() {
		return purpose;
	}
}
