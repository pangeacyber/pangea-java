package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class EncryptResult {

	/** ID of the key. */
	@JsonProperty("id")
	String id;

	/** Version of the key used for encryption. */
	@JsonProperty("version")
	Integer version;

	/** Encrypted message (Base64-encoded). */
	@JsonProperty("cipher_text")
	String cipherText;

	/** Algorithm of the key used for encryption. */
	@JsonProperty("algorithm")
	String algorithm;
}
