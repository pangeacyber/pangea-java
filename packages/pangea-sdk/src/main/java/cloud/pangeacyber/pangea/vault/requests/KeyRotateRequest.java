package cloud.pangeacyber.pangea.vault.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class KeyRotateRequest extends CommonRotateRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("public_key")
	String encodedPublicKey = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("private_key")
	String encodedPrivateKey = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("key")
	String encodedSymmetricKey = null;

	protected KeyRotateRequest(KeyRotateRequestBuilder builder) {
		super(builder);
		this.encodedPrivateKey = builder.encodedPrivateKey;
		this.encodedPublicKey = builder.encodedPublicKey;
		this.encodedSymmetricKey = builder.encodedSymmetricKey;
	}

	public String getEncodedPublicKey() {
		return encodedPublicKey;
	}

	public String getEncodedPrivateKey() {
		return encodedPrivateKey;
	}

	public String getEncodedSymmetricKey() {
		return encodedSymmetricKey;
	}

	public static class KeyRotateRequestBuilder extends CommonRotateRequestBuilder<KeyRotateRequestBuilder> {

		String encodedPublicKey = null;
		String encodedPrivateKey = null;
		String encodedSymmetricKey = null;

		public KeyRotateRequestBuilder(String id) {
			super(id);
		}

		public KeyRotateRequest build() {
			return new KeyRotateRequest(this);
		}

		public KeyRotateRequestBuilder setEncodedPublicKey(String encodedPublicKey) {
			this.encodedPublicKey = encodedPublicKey;
			return this;
		}

		public KeyRotateRequestBuilder setEncodedPrivateKey(String encodedPrivateKey) {
			this.encodedPrivateKey = encodedPrivateKey;
			return this;
		}

		public KeyRotateRequestBuilder setEncodedSymmetricKey(String encodedSymmetricKey) {
			this.encodedSymmetricKey = encodedSymmetricKey;
			return this;
		}
	}
}
