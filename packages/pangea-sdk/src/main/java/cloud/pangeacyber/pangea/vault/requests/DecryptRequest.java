package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DecryptRequest extends BaseRequest {

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

	protected DecryptRequest(Builder builder) {
		this.id = builder.id;
		this.cipherText = builder.cipherText;
		this.version = builder.version;
		this.additionalData = builder.additionalData;
	}

	public static class Builder {

		String id;
		String cipherText;
		Integer version;
		String additionalData;

		public Builder(String id, String cipherText) {
			this.id = id;
			this.cipherText = cipherText;
		}

		public DecryptRequest build() {
			return new DecryptRequest(this);
		}

		public Builder version(Integer version) {
			this.version = version;
			return this;
		}

		public Builder additionalData(String additionalData) {
			this.additionalData = additionalData;
			return this;
		}
	}
}
