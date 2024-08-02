package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ShareLinkGetRequest extends BaseRequest {

	/** The ID of a share link. */
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
