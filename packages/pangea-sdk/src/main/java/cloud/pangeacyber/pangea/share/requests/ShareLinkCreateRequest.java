package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.share.models.ShareLinkCreateItem;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ShareLinkCreateRequest extends BaseRequest {

	/** The bucket to use, if not the default. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("bucket_id")
	private String bucketId;

	@JsonProperty("links")
	private List<ShareLinkCreateItem> links;

	private ShareLinkCreateRequest(Builder builder) {
		this.links = builder.links;
		this.bucketId = builder.bucketId;
	}

	public List<ShareLinkCreateItem> getLinks() {
		return links;
	}

	public static class Builder {
		private List<ShareLinkCreateItem> links;
		private String bucketId;

		public Builder links(List<ShareLinkCreateItem> links) {
			this.links = links;
			return this;
		}

		/** The bucket to use, if not the default. */
		public Builder bucketId(String bucketId) {
			this.bucketId = bucketId;
			return this;
		}

		public ShareLinkCreateRequest build() {
			return new ShareLinkCreateRequest(this);
		}
	}
}
