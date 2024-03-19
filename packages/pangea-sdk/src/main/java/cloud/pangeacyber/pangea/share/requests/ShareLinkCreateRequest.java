package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.share.models.ShareLinkCreateItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ShareLinkCreateRequest extends BaseRequest {

	@JsonProperty("links")
	private List<ShareLinkCreateItem> links;

	private ShareLinkCreateRequest(Builder builder) {
		this.links = builder.links;
	}

	public List<ShareLinkCreateItem> getLinks() {
		return links;
	}

	public static class Builder {

		private List<ShareLinkCreateItem> links;

		public Builder links(List<ShareLinkCreateItem> links) {
			this.links = links;
			return this;
		}

		public ShareLinkCreateRequest build() {
			return new ShareLinkCreateRequest(this);
		}
	}
}
