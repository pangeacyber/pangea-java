package cloud.pangeacyber.pangea.vault.results;

import cloud.pangeacyber.pangea.vault.models.ExportEncryptionAlgorithm;
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

	/** The algorithm of the public key used to encrypt exported material. */
	@JsonProperty("asymmetric_algorithm")
	private ExportEncryptionAlgorithm asymmetricAlgorithm;

	@JsonProperty("symmetric_algorithm")
	private String symmetricAlgorithm;

	/**
	 * Encryption format of the exported key(s). It could be none if returned in
	 * plain text, asymmetric if it is encrypted just with the public key sent
	 * in encryption_public_key, or kem if it was encrypted using KEM protocol.
	 */
	@JsonProperty("encryption_type")
	private ExportEncryptionType encryptionType;

	/**
	 * Key derivation function used to derivate the symmetric key when
	 * `encryption_type` is `kem`.
	 */
	@JsonProperty("kdf")
	private String kdf;

	/**
	 * Hash algorithm used to derivate the symmetric key when `encryption_type`
	 * is `kem`.
	 */
	@JsonProperty("hash_algorithm")
	private String hashAlgorithm;

	/**
	 * Iteration count used to derivate the symmetric key when `encryption_type`
	 * is `kem`.
	 */
	@JsonProperty("iteration_count")
	private int iterationCount;

	/**
	 * Salt used to derivate the symmetric key when `encryption_type` is `kem`,
	 * encrypted with the public key provided in `asymmetric_key`.
	 */
	@JsonProperty("encrypted_salt")
	private String encryptedSalt;

	/** The public key (in PEM format). */
	@JsonProperty("public_key")
	private String publicKey;

	/** The private key (in PEM format). */
	@JsonProperty("private_key")
	private String privateKey;

	/** The key material. */
	@JsonProperty("key")
	private String key;
}
