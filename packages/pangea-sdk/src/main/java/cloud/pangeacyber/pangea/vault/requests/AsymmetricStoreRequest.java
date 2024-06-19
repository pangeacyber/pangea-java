package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.vault.models.AsymmetricAlgorithm;
import cloud.pangeacyber.pangea.vault.models.ItemType;
import cloud.pangeacyber.pangea.vault.models.KeyPurpose;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AsymmetricStoreRequest extends CommonStoreRequest {

	@JsonProperty("type")
	ItemType type;

	@JsonProperty("algorithm")
	AsymmetricAlgorithm algorithm = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("purpose")
	KeyPurpose purpose = null;

	@JsonProperty("public_key")
	String encodedPublicKey;

	@JsonProperty("private_key")
	String encodedPrivateKey = null;

	/** Whether the key is exportable or not. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("exportable")
	Boolean exportable = null;

	protected AsymmetricStoreRequest(Builder builder) {
		super(builder);
		this.type = builder.type;
		this.algorithm = builder.algorithm;
		this.purpose = builder.purpose;
		this.encodedPrivateKey = builder.encodedPrivateKey;
		this.encodedPublicKey = builder.encodedPublicKey;
		this.exportable = builder.exportable;
	}

	public AsymmetricAlgorithm getAlgorithm() {
		return algorithm;
	}

	public KeyPurpose getPurpose() {
		return purpose;
	}

	public String getEncodedPublicKey() {
		return encodedPublicKey;
	}

	public String getEncodedPrivateKey() {
		return encodedPrivateKey;
	}

	public static class Builder extends CommonBuilder<Builder> {

		ItemType type;
		AsymmetricAlgorithm algorithm = null;
		KeyPurpose purpose = null;
		String encodedPublicKey;
		String encodedPrivateKey = null;
		Boolean exportable = null;

		public Builder(
			String encodedPrivateKey,
			String encodedPublicKey,
			AsymmetricAlgorithm algorithm,
			KeyPurpose purpose,
			String name
		) {
			super(name);
			this.type = ItemType.ASYMMETRIC_KEY;
			this.algorithm = algorithm;
			this.encodedPublicKey = encodedPublicKey;
			this.encodedPrivateKey = encodedPrivateKey;
			this.purpose = purpose;
		}

		public AsymmetricStoreRequest build() {
			return new AsymmetricStoreRequest(this);
		}

		public Builder purpose(KeyPurpose purpose) {
			this.purpose = purpose;
			return this;
		}

		public Builder exportable(boolean exportable) {
			this.exportable = exportable;
			return this;
		}
	}
}
