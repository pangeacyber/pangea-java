package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SymmetricGenerateResult extends CommonGenerateResult {

	@JsonProperty("algorithm")
	String algorithm;

	@JsonProperty("purpose")
	String purpose;

	public SymmetricGenerateResult() {
		super();
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public String getPurpose() {
		return purpose;
	}
}
