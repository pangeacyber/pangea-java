package cloud.pangeacyber.pangea.vault.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SecretRotateRequest extends CommonRotateRequest {

	@JsonProperty("secret")
	String secret = null;

	protected SecretRotateRequest(Builder builder) {
		super(builder);
		this.secret = builder.secret;
	}

	public String getSecret() {
		return secret;
	}

	public static class Builder extends CommonBuilder<Builder> {

		String secret = null;

		public SecretRotateRequest build() {
			return new SecretRotateRequest(this);
		}

		public Builder(String id, String secret) {
			super(id);
			this.secret = secret;
		}

		public Builder secret(String secret) {
			this.secret = secret;
			return this;
		}
	}
}
