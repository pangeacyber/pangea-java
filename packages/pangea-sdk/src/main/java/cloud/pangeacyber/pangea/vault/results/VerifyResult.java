package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyResult {

	@JsonProperty("id")
	String id;

	@JsonProperty("version")
	Integer version;

	@JsonProperty("algorithm")
	String algorithm;

	@JsonProperty("valid_signature")
	boolean validSignature;

	public VerifyResult() {}

	public String getAlgorithm() {
		return algorithm;
	}

	public String getId() {
		return id;
	}

	public Integer getVersion() {
		return version;
	}

	public boolean isValidSignature() {
		return validSignature;
	}
}
