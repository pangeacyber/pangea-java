package cloud.pangeacyber.pangea.share.results;

import cloud.pangeacyber.pangea.share.models.ShareLinkItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShareLinkGetResult {

	@JsonProperty("share_link_object")
	ShareLinkItem shareLinkObject;

	public ShareLinkGetResult() {}

	public ShareLinkItem getShareLinkObject() {
		return shareLinkObject;
	}
}
