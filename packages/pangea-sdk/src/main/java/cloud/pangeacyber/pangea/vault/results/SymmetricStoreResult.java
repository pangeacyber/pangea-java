package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SymmetricStoreResult extends CommonStoreResult {

	@JsonProperty("algorithm")
	String algorithm;

	@JsonProperty("purpose")
	String purpose;

	public SymmetricStoreResult() {
		super();
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public String getPurpose() {
		return purpose;
	}
}
