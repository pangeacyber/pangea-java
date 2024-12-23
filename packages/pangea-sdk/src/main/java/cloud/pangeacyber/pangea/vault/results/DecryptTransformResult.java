package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Result of a decrypt transform request. */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class DecryptTransformResult {

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

	/**
	 * User provided tweak string. If not provided, a random string will be
	 * generated and returned. The user must securely store the tweak source
	 * which will be needed to decrypt the data.
	 */
	@JsonProperty("tweak")
	private String tweak;
}
