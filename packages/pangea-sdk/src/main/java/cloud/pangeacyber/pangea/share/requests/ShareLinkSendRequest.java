package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.share.models.ShareLinkSendItem;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ShareLinkSendRequest extends BaseRequest {

	@JsonProperty("links")
	private List<ShareLinkSendItem> links;

	/** An email address */
	@JsonProperty("sender_email")
	private String senderEmail;

	/** The sender name information. Can be sender's full name for example. */
	@JsonProperty("sender_name")
	private String senderName;

	/** The bucket to use, if not the default. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("bucket_id")
	String bucketId;

	private ShareLinkSendRequest(Builder builder) {
		this.links = builder.links;
		this.senderEmail = builder.senderEmail;
		this.senderName = builder.senderName;
		this.bucketId = builder.bucketId;
	}

	public List<ShareLinkSendItem> getLinks() {
		return links;
	}

	/** An email address */
	public String getSenderEmail() {
		return senderEmail;
	}

	/** The sender name information. Can be sender's full name for example. */
	public String getSenderName() {
		return senderName;
	}

	/** The bucket to use, if not the default. */
	public String getBucketId() {
		return bucketId;
	}

	public static class Builder {

		private List<ShareLinkSendItem> links;
		private String senderEmail;
		private String senderName;
		private String bucketId;

		public Builder(List<ShareLinkSendItem> links, String senderEmail) {
			this.links = links;
			this.senderEmail = senderEmail;
		}

		/** The sender name information. Can be sender's full name for example. */
		public Builder senderName(String senderName) {
			this.senderName = senderName;
			return this;
		}

		/** The bucket to use, if not the default. */
		public Builder bucketId(String bucketId) {
			this.bucketId = bucketId;
			return this;
		}

		public ShareLinkSendRequest build() {
			return new ShareLinkSendRequest(this);
		}
	}
}
