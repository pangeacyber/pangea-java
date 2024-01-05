package cloud.pangeacyber.pangea.store.results;

import cloud.pangeacyber.pangea.store.models.ShareLinkItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShareLinkDeleteResult {

	@JsonProperty("share_link_objects")
	List<ShareLinkItem> shareLinkObjects;

	public ShareLinkDeleteResult() {}

	public List<ShareLinkItem> getShareLinkObjects() {
		return shareLinkObjects;
	}
}
