package cloud.pangeacyber.pangea.vault.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DecryptRequest {

	@JsonProperty("id")
	String id;

	@JsonProperty("cipher_text")
	String cipherText;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("version")
	Integer version = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("additional_data")
	String additionalData;

	protected DecryptRequest(DecryptRequestBuilder builder) {
		this.id = builder.id;
		this.cipherText = builder.cipherText;
		this.version = builder.version;
		this.additionalData = builder.additionalData;
	}

	public static class DecryptRequestBuilder {

		String id;
		String cipherText;
		Integer version;
		String additionalData;

		public DecryptRequestBuilder(String id, String cipherText) {
			this.id = id;
			this.cipherText = cipherText;
		}

		public DecryptRequest build() {
			return new DecryptRequest(this);
		}

		public DecryptRequestBuilder setVersion(Integer version) {
			this.version = version;
			return this;
		}

		public DecryptRequestBuilder setAdditionalData(String additionalData) {
			this.additionalData = additionalData;
			return this;
		}
	}
}
