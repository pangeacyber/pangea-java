package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class DecryptResult {

	/** ID of the key. */
	@JsonProperty("id")
	String id;

	/** Decrypted message (Base64-encoded). */
	@JsonProperty("plain_text")
	String plainText;

	/** Version of the key used for decryption. */
	@JsonProperty("version")
	Integer version;

	/** Algorithm of the key used for decryption. */
	@JsonProperty("algorithm")
	String algorithm;
}
