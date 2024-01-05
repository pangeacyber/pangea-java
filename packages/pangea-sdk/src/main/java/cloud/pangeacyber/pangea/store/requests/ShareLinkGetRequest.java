package cloud.pangeacyber.pangea.store.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ShareLinkGetRequest extends BaseRequest {

	@JsonProperty("id")
	String id;

	protected ShareLinkGetRequest(Builder builder) {
		this.id = builder.id;
	}

	public static class Builder {

		String id;

		public Builder(String id) {
			this.id = id;
		}

		public ShareLinkGetRequest build() {
			return new ShareLinkGetRequest(this);
		}
	}
}
