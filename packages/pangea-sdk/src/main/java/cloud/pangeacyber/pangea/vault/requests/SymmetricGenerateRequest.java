package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.vault.models.ItemType;
import cloud.pangeacyber.pangea.vault.models.KeyPurpose;
import cloud.pangeacyber.pangea.vault.models.SymmetricAlgorithm;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SymmetricGenerateRequest extends CommonGenerateRequest {

	@JsonProperty("type")
	ItemType type;

	@JsonProperty("algorithm")
	SymmetricAlgorithm algorithm = null;

	@JsonProperty("purpose")
	KeyPurpose purpose = null;

	public SymmetricGenerateRequest(SymmetricGenerateRequestBuilder builder) {
		super(builder);
		this.type = builder.type;
		this.algorithm = builder.algorithm;
		this.purpose = builder.purpose;
	}

	public SymmetricAlgorithm getAlgorithm() {
		return algorithm;
	}

	public KeyPurpose getPurpose() {
		return purpose;
	}

	public static class SymmetricGenerateRequestBuilder
		extends CommonGenerateRequestBuilder<SymmetricGenerateRequestBuilder> {

		ItemType type;
		SymmetricAlgorithm algorithm = null;
		KeyPurpose purpose = null;

		public SymmetricGenerateRequestBuilder(SymmetricAlgorithm algorithm, KeyPurpose purpose) {
			super();
			this.type = ItemType.SYMMETRIC_KEY;
			this.algorithm = algorithm;
			this.purpose = purpose;
		}

		public SymmetricGenerateRequest build() {
			return new SymmetricGenerateRequest(this);
		}

		public SymmetricGenerateRequestBuilder setAlgorithm(SymmetricAlgorithm algorithm) {
			this.algorithm = algorithm;
			return this;
		}

		public SymmetricGenerateRequestBuilder setPurpose(KeyPurpose purpose) {
			this.purpose = purpose;
			return this;
		}
	}
}
