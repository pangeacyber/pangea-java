package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ShareLinkDeleteRequest extends BaseRequest {

	@JsonProperty("ids")
	List<String> ids;

	public List<String> getIds() {
		return ids;
	}

	protected ShareLinkDeleteRequest(Builder builder) {
		this.ids = builder.ids;
	}

	public static class Builder {

		List<String> ids;

		public Builder(List<String> ids) {
			this.ids = ids;
		}

		public ShareLinkDeleteRequest build() {
			return new ShareLinkDeleteRequest(this);
		}
	}
}
