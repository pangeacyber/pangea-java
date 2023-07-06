package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EncryptRequest extends BaseRequest {

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

	protected EncryptRequest(Builder builder) {
		this.id = builder.id;
		this.plainText = builder.plainText;
		this.version = builder.version;
		this.additionalData = builder.additionalData;
	}

	public static class Builder {

		String id;
		String plainText;
		Integer version;
		String additionalData;

		public Builder(String id, String plainText) {
			this.id = id;
			this.plainText = plainText;
		}

		public EncryptRequest build() {
			return new EncryptRequest(this);
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
