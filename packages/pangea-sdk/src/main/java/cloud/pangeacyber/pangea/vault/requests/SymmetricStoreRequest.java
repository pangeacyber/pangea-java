package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.vault.models.ItemType;
import cloud.pangeacyber.pangea.vault.models.KeyPurpose;
import cloud.pangeacyber.pangea.vault.models.SymmetricAlgorithm;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SymmetricStoreRequest extends CommonStoreRequest {

	@JsonProperty("type")
	ItemType type;

	@JsonProperty("algorithm")
	SymmetricAlgorithm algorithm = null;

	@JsonProperty("key")
	String encodedSymmetricKey;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("purpose")
	KeyPurpose purpose = null;

	public SymmetricStoreRequest(SymmetricStoreRequestBuilder builder) {
		super(builder);
		this.type = builder.type;
		this.algorithm = builder.algorithm;
		this.encodedSymmetricKey = builder.encodedSymmetricKey;
		this.purpose = builder.purpose;
	}

	public static class SymmetricStoreRequestBuilder extends CommonStoreRequestBuilder<SymmetricStoreRequestBuilder> {

		ItemType type;
		SymmetricAlgorithm algorithm = null;
		String encodedSymmetricKey;
		KeyPurpose purpose = null;

		public SymmetricStoreRequestBuilder(
			String encodedSymmetricKey,
			SymmetricAlgorithm algorithm,
			KeyPurpose purpose
		) {
			this.type = ItemType.SYMMETRIC_KEY;
			this.algorithm = algorithm;
			this.encodedSymmetricKey = encodedSymmetricKey;
			this.purpose = purpose;
		}

		public SymmetricStoreRequest build() {
			return new SymmetricStoreRequest(this);
		}

		public SymmetricStoreRequestBuilder setPurpose(KeyPurpose purpose) {
			this.purpose = purpose;
			return this;
		}
	}
}
