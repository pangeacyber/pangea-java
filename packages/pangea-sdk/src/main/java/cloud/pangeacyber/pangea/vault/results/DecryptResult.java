package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DecryptResult {

	@JsonProperty("id")
	String id;

	@JsonProperty("plain_text")
	String plainText;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("version")
	Integer version = null;

	@JsonProperty("algorithm")
	String algorithm;

	public DecryptResult() {}

	public DecryptResult(String id, String plainText, Integer version, String algorithm) {
		this.id = id;
		this.plainText = plainText;
		this.version = version;
		this.algorithm = algorithm;
	}

	public String getId() {
		return id;
	}

	public String getPlainText() {
		return plainText;
	}

	public Integer getVersion() {
		return version;
	}

	public String getAlgorithm() {
		return algorithm;
	}
}
