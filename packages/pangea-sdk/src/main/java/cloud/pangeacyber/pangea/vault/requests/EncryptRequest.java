package cloud.pangeacyber.pangea.vault.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EncryptRequest {

	@JsonProperty("id")
	String id;

	@JsonProperty("plain_text")
	String plainText;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("version")
	Integer version;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("additional_data")
	String additionalData;

	protected EncryptRequest(EncryptRequestBuilder builder) {
		this.id = builder.id;
		this.plainText = builder.plainText;
		this.version = builder.version;
		this.additionalData = builder.additionalData;
	}

	public static class EncryptRequestBuilder {

		String id;
		String plainText;
		Integer version;
		String additionalData;

		public EncryptRequestBuilder(String id, String plainText) {
			this.id = id;
			this.plainText = plainText;
		}

		public EncryptRequest build() {
			return new EncryptRequest(this);
		}

		public EncryptRequestBuilder setVersion(Integer version) {
			this.version = version;
			return this;
		}

		public EncryptRequestBuilder setAdditionalData(String additionalData) {
			this.additionalData = additionalData;
			return this;
		}
	}
}