package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SignResult {

	@JsonProperty("id")
	String id;

	@JsonProperty("version")
	Integer version;

	@JsonProperty("signature")
	String signature;

	@JsonProperty("algorithm")
	String algorithm;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("public_key")
	String encodedPublicKey = null;

	public SignResult() {}

	public String getEncodedPublicKey() {
		return encodedPublicKey;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public String getId() {
		return id;
	}

	public Integer getVersion() {
		return version;
	}

	public String getSignature() {
		return signature;
	}
}
