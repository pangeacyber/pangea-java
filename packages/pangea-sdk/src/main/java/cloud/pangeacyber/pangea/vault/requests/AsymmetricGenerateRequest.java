package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.vault.models.AsymmetricAlgorithm;
import cloud.pangeacyber.pangea.vault.models.ItemType;
import cloud.pangeacyber.pangea.vault.models.KeyPurpose;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AsymmetricGenerateRequest extends CommonGenerateRequest {

	@JsonProperty("type")
	ItemType type;

	@JsonProperty("algorithm")
	AsymmetricAlgorithm algorithm = null;

	@JsonProperty("purpose")
	KeyPurpose purpose = null;

	protected AsymmetricGenerateRequest(Builder builder) {
		super(builder);
		this.type = builder.type;
		this.algorithm = builder.algorithm;
		this.purpose = builder.purpose;
	}

	public AsymmetricAlgorithm getAlgorithm() {
		return algorithm;
	}

	public KeyPurpose getPurpose() {
		return purpose;
	}

	public static class Builder extends CommonBuilder<Builder> {

		ItemType type;
		AsymmetricAlgorithm algorithm = null;
		KeyPurpose purpose = null;

		public Builder(AsymmetricAlgorithm algorithm, KeyPurpose purpose, String name) {
			super(name);
			this.type = ItemType.ASYMMETRIC_KEY;
			this.algorithm = algorithm;
			this.purpose = purpose;
		}

		public AsymmetricGenerateRequest build() {
			return new AsymmetricGenerateRequest(this);
		}

		public Builder algorithm(AsymmetricAlgorithm algorithm) {
			this.algorithm = algorithm;
			return this;
		}

		public Builder purpose(KeyPurpose purpose) {
			this.purpose = purpose;
			return this;
		}
	}
}
