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

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("managed")
	Boolean managed = null;

	@JsonProperty("algorithm")
	AsymmetricAlgorithm algorithm = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("purpose")
	KeyPurpose purpose = null;

	@JsonProperty("public_key")
	String encodedPublicKey;

	@JsonProperty("private_key")
	String encodedPrivateKey = null;

	protected AsymmetricStoreRequest(AsymmetricStoreRequestBuilder builder) {
		super(builder);
		this.type = builder.type;
		this.managed = builder.managed;
		this.algorithm = builder.algorithm;
		this.purpose = builder.purpose;
		this.encodedPrivateKey = builder.encodedPrivateKey;
		this.encodedPublicKey = builder.encodedPublicKey;
	}

	public Boolean getManaged() {
		return managed;
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

	public static class AsymmetricStoreRequestBuilder extends CommonStoreRequestBuilder<AsymmetricStoreRequestBuilder> {

		ItemType type;
		Boolean managed = null;
		AsymmetricAlgorithm algorithm = null;
		KeyPurpose purpose = null;
		String encodedPublicKey;
		String encodedPrivateKey = null;

		public AsymmetricStoreRequestBuilder(
			AsymmetricAlgorithm algorithm,
			String encodedPublicKey,
			String encodedPrivateKey
		) {
			this.type = ItemType.ASYMMETRIC_KEY;
			this.algorithm = algorithm;
			this.encodedPublicKey = encodedPublicKey;
			this.encodedPrivateKey = encodedPrivateKey;
		}

		public AsymmetricStoreRequest build() {
			return new AsymmetricStoreRequest(this);
		}

		public AsymmetricStoreRequestBuilder setManaged(Boolean managed) {
			this.managed = managed;
			return this;
		}

		public AsymmetricStoreRequestBuilder setPurpose(KeyPurpose purpose) {
			this.purpose = purpose;
			return this;
		}
	}
}
