package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class SignResult {

	/** The ID of the item. */
	@JsonProperty("id")
	String id;

	/** The item version. */
	@JsonProperty("version")
	Integer version;

	/** The signature of the message. */
	@JsonProperty("signature")
	String signature;

	/** The algorithm of the key. */
	@JsonProperty("algorithm")
	String algorithm;

	/** The public key (in PEM format). */
	@JsonProperty("public_key")
	String publicKey;
}
