package cloud.pangeacyber.pangea.vault.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PangeaTokenStoreRequest extends CommonStoreRequest {

	@JsonProperty("secret")
	String token = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_grace_period")
	String rotationGracePeriod = null;

	protected PangeaTokenStoreRequest(PangeaTokenStoreRequestBuilder builder) {
		super(builder);
		this.token = builder.token;
		this.rotationGracePeriod = builder.rotationGracePeriod;
	}

	public String getToken() {
		return token;
	}

	public static class PangeaTokenStoreRequestBuilder
		extends CommonStoreRequestBuilder<PangeaTokenStoreRequestBuilder> {

		String token = null;
		String rotationGracePeriod = null;

		public PangeaTokenStoreRequestBuilder(String token) {
			this.token = token;
		}

		public PangeaTokenStoreRequest build() {
			return new PangeaTokenStoreRequest(this);
		}

		public PangeaTokenStoreRequestBuilder setRotationGracePeriod(String rotationGracePeriod) {
			this.rotationGracePeriod = rotationGracePeriod;
			return this;
		}
	}
}
