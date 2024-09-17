package cloud.pangeacyber.pangea.vault.results;

import cloud.pangeacyber.pangea.vault.models.ExportEncryptionType;
import cloud.pangeacyber.pangea.vault.models.ItemType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public final class ExportResult {

	/** The ID of the item. */
	@JsonProperty("id")
	private String id;

	/** The type of the key. */
	@JsonProperty("type")
	private ItemType type;

	/** The item version. */
	@JsonProperty("version")
	private String version;

	/** The state of the item. */
	@JsonProperty("enabled")
	private boolean enabled;

	/** The algorithm of the key. */
	@JsonProperty("algorithm")
	private String algorithm;

	/**
	 * Encryption format of the exported key(s). It could be none if returned in
	 * plain text, asymmetric if it is encrypted just with the public key sent
	 * in encryption_public_key, or kem if it was encrypted using KEM protocol.
	 */
	@JsonProperty("encryption_type")
	private ExportEncryptionType encryptionType;

	/** The public key (in PEM format). */
	@JsonProperty("public_key")
	private String publicKey;

	/** The private key (in PEM format). */
	@JsonProperty("private_key")
	private String privateKey;
}
