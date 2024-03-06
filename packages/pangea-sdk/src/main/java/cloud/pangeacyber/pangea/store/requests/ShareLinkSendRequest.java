package cloud.pangeacyber.pangea.store.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.store.models.ShareLinkSendItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ShareLinkSendRequest extends BaseRequest {

	@JsonProperty("links")
	private List<ShareLinkSendItem> links;

	@JsonProperty("sender_email")
	private String senderEmail;

	@JsonProperty("sender_name")
	private String senderName;

	private ShareLinkSendRequest(Builder builder) {
		this.links = builder.links;
		this.senderEmail = builder.senderEmail;
		this.senderName = builder.senderName;
	}

	public List<ShareLinkSendItem> getLinks() {
		return links;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public String getSenderName() {
		return senderName;
	}

	public static class Builder {

		private List<ShareLinkSendItem> links;
		private String senderEmail;
		private String senderName;

		public Builder(List<ShareLinkSendItem> links, String senderEmail) {
			this.links = links;
			this.senderEmail = senderEmail;
		}

		public Builder senderName(String senderName) {
			this.senderName = senderName;
			return this;
		}

		public ShareLinkSendRequest build() {
			return new ShareLinkSendRequest(this);
		}
	}
}
