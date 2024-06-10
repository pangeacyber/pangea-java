package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class ExportResult {

	/** The ID of the item. */
	@JsonProperty("id")
	private String id;

	/** The item version. */
	@JsonProperty("version")
	private String version;

	/** The type of the key. */
	@JsonProperty("type")
	private String type;

	/** The state of the item. */
	@JsonProperty("item_state")
	private String itemState;

	/** The algorithm of the key. */
	@JsonProperty("algorithm")
	private String algorithm;

	/** The public key (in PEM format). */
	@JsonProperty("public_key")
	private String publicKey;

	/** The private key (in PEM format). */
	@JsonProperty("private_key")
	private String privateKey;

	/** The key material. */
	@JsonProperty("key")
	private String key;

	/**
	 * Whether exported key(s) are encrypted with encryption_key sent on the
	 * request or not. If encrypted, the result is sent in base64, any other
	 * case they are in PEM format plain text.
	 */
	@JsonProperty("encrypted")
	private Boolean encrypted;

	public String getId() {
		return id;
	}

	public String getVersion() {
		return version;
	}

	public String getType() {
		return type;
	}

	public String getItemState() {
		return itemState;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public String getKey() {
		return key;
	}

	public Boolean isEncrypted() {
		return encrypted;
	}
}
