package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SymmetricStoreResult extends CommonStoreResult {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("algorithm")
	String algorithm;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("key")
	String encodedSymmetricKey = null;

	public SymmetricStoreResult() {}

	public SymmetricStoreResult(String algorithm, String encodedSymmetricKey) {
		this.algorithm = algorithm;
		this.encodedSymmetricKey = encodedSymmetricKey;
	}

	public SymmetricStoreResult(String type, String id, Integer version, String algorithm, String encodedSymmetricKey) {
		super(type, id, version);
		this.algorithm = algorithm;
		this.encodedSymmetricKey = encodedSymmetricKey;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public String getEncodedSymmetricKey() {
		return encodedSymmetricKey;
	}
}
