package cloud.pangeacyber.pangea.vault.results;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Result of a decrypt transform request.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DecryptTransformResult extends BaseRequest {

	/** The ID of the item. */
	@JsonProperty("id")
	private String id;

	/** The item version. */
	@JsonProperty("version")
	private int version;

	/** The algorithm of the key. */
	@JsonProperty("algorithm")
	private String algorithm;

	/** Decrypted message. */
	@JsonProperty("plain_text")
	private String plainText;

	/** User-provided tweak, which must be a base64-encoded 7-digit string.*/
	@JsonProperty("tweak")
	private String tweak;

	public DecryptTransformResult() {}

	public String getId() {
		return id;
	}

	public int getVersion() {
		return version;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public String getPlainText() {
		return plainText;
	}

	public String getTweak() {
		return tweak;
	}
}
