package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Result of an encrypt/decrypt structured request.
 *
 * @param <T> Structured data type.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class EncryptStructuredResult<K, V, T extends Map<K, V>> {

	/** The ID of the item. */
	@JsonProperty("id")
	String id;

	/** The item version. */
	@JsonProperty("version")
	int version;

	/** The algorithm of the key. */
	@JsonProperty("algorithm")
	String algorithm;

	/** Structured data with filtered fields encrypted/decrypted. */
	@JsonProperty("structured_data")
	T structuredData;
}
