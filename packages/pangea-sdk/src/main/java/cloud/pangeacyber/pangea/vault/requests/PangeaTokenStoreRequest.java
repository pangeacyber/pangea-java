package cloud.pangeacyber.pangea.vault.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PangeaTokenStoreRequest extends CommonStoreRequest {

	@JsonProperty("secret")
	String token = null;

	protected PangeaTokenStoreRequest(PangeaTokenStoreRequestBuilder builder) {
		super(builder);
		this.token = builder.token;
	}

	public String getToken() {
		return token;
	}

	public static class PangeaTokenStoreRequestBuilder
		extends CommonStoreRequestBuilder<PangeaTokenStoreRequestBuilder> {

		String token = null;

		public PangeaTokenStoreRequestBuilder(String token) {
			this.token = token;
		}

		public PangeaTokenStoreRequest build() {
			return new PangeaTokenStoreRequest(this);
		}
	}
}
