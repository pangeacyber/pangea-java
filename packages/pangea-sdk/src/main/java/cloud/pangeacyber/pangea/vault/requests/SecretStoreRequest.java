package cloud.pangeacyber.pangea.vault.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SecretStoreRequest extends CommonStoreRequest {

	@JsonProperty("secret")
	String secret = null;

	protected SecretStoreRequest(SecretStoreRequestBuilder builder) {
		super(builder);
		this.secret = builder.secret;
	}

	public String getSecret() {
		return secret;
	}

	public static class SecretStoreRequestBuilder extends CommonStoreRequestBuilder<SecretStoreRequestBuilder> {

		String secret = null;

		public SecretStoreRequestBuilder(String secret, String name) {
			super(name);
			this.secret = secret;
		}

		public SecretStoreRequest build() {
			return new SecretStoreRequest(this);
		}
	}
}
