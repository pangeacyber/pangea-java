package cloud.pangeacyber.pangea.vault.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommonRotateRequest {

	@JsonProperty("id")
	String id;

	protected CommonRotateRequest(CommonRotateRequestBuilder<?> builder) {
		this.id = builder.id;
	}

	public String getId() {
		return id;
	}

	public static class CommonRotateRequestBuilder<B extends CommonRotateRequestBuilder<B>> {

		String id;

		public CommonRotateRequestBuilder(String id) {
			this.id = id;
		}

		public CommonRotateRequest build() {
			return new CommonRotateRequest(this);
		}
	}
}
