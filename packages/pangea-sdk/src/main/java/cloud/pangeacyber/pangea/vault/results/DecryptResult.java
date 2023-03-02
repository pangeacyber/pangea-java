package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DecryptResult {

	@JsonProperty("id")
	String id;

	@JsonProperty("plain_text")
	String plainText;

	@JsonProperty("version")
	Integer version;

	@JsonProperty("algorithm")
	String algorithm;

	public DecryptResult() {}

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
