package cloud.pangeacyber.pangea.vault.results;

import cloud.pangeacyber.pangea.vault.models.TransformAlphabet;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Result of an encrypt transform request. */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
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
}
