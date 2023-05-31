package cloud.pangeacyber.pangea.vault.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SecretStoreRequest extends CommonStoreRequest {

	@JsonProperty("secret")
	String secret = null;

	@JsonProperty("type")
	String type;

	protected SecretStoreRequest(Builder builder) {
		super(builder);
		this.secret = builder.secret;
		this.type = "secret";
	}

	public String getSecret() {
		return secret;
	}

	public static class Builder extends CommonBuilder<Builder> {

		String secret = null;

		public Builder(String secret, String name) {
			super(name);
			this.secret = secret;
		}

		public SecretStoreRequest build() {
			return new SecretStoreRequest(this);
		}
	}
}
