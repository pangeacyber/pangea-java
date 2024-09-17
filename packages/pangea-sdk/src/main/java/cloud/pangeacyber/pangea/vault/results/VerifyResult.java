package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class VerifyResult {

	/** The ID of the item. */
	@JsonProperty("id")
	String id;

	/** The item version. */
	@JsonProperty("version")
	int version;

	/** The algorithm of the key. */
	@JsonProperty("algorithm")
	String algorithm;

	/** Indicates if messages have been verified. */
	@JsonProperty("valid_signature")
	boolean validSignature;
}
