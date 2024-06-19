package cloud.pangeacyber.pangea.vault.results;

import cloud.pangeacyber.pangea.vault.models.TransformAlphabet;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Result of an encrypt transform request. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EncryptTransformResult {

	/** The ID of the item.*/
	@JsonProperty("id")
	private String id;

	/** The item version.*/
	@JsonProperty("version")
	private int version;

	/** The algorithm of the key.*/
	@JsonProperty("algorithm")
	private String algorithm;

	/** The encrypted message.*/
	@JsonProperty("cipher_text")
	private String cipherText;

	/**
	 * User provided tweak string. If not provided, a random string will be
	 * generated and returned. The user must securely store the tweak source
	 * which will be needed to decrypt the data.
	 */
	@JsonProperty("tweak")
	private String tweak;

	/** Set of characters to use for format-preserving encryption (FPE).*/
	@JsonProperty("alphabet")
	private TransformAlphabet alphabet;

	public EncryptTransformResult() {}

	public EncryptTransformResult(
		String id,
		int version,
		String algorithm,
		String cipherText,
		String tweak,
		TransformAlphabet alphabet
	) {
		this.id = id;
		this.version = version;
		this.algorithm = algorithm;
		this.cipherText = cipherText;
		this.tweak = tweak;
		this.alphabet = alphabet;
	}

	public String getId() {
		return id;
	}

	public int getVersion() {
		return version;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public String getCipherText() {
		return cipherText;
	}

	public String getTweak() {
		return tweak;
	}

	public TransformAlphabet getAlphabet() {
		return alphabet;
	}
}
