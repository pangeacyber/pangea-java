package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * Result of an encrypt/decrypt structured request.
 *
 * @param <T> Structured data type.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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

	public EncryptStructuredResult() {}

	public String getAlgorithm() {
		return this.algorithm;
	}

	public String getId() {
		return this.id;
	}

	public int getVersion() {
		return this.version;
	}

	public T getStructuredData() {
		return this.structuredData;
	}
}
