package cloud.pangeacyber.pangea.vault.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PangeaTokenStoreRequest extends CommonStoreRequest {

	@JsonProperty("secret")
	String token = null;

	@JsonProperty("type")
	String type;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_grace_period")
	String rotationGracePeriod = null;

	protected PangeaTokenStoreRequest(Builder builder) {
		super(builder);
		this.token = builder.token;
		this.rotationGracePeriod = builder.rotationGracePeriod;
		this.type = "pangea_token";
	}

	public String getToken() {
		return token;
	}

	public static class Builder extends CommonBuilder<Builder> {

		String token = null;
		String rotationGracePeriod = null;

		public Builder(String token, String name) {
			super(name);
			this.token = token;
		}

		public PangeaTokenStoreRequest build() {
			return new PangeaTokenStoreRequest(this);
		}

		public Builder rotationGracePeriod(String rotationGracePeriod) {
			this.rotationGracePeriod = rotationGracePeriod;
			return this;
		}
	}
}
