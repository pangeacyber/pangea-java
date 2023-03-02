package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.vault.models.ItemType;
import cloud.pangeacyber.pangea.vault.models.KeyPurpose;
import cloud.pangeacyber.pangea.vault.models.SymmetricAlgorithm;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SymmetricGenerateRequest extends CommonGenerateRequest {

	@JsonProperty("type")
	ItemType type;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("algorithm")
	SymmetricAlgorithm algorithm = null;

	@JsonInclude(Include.NON_NULL)
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

		public SymmetricGenerateRequestBuilder() {
			super();
			this.type = ItemType.SYMMETRIC_KEY;
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
