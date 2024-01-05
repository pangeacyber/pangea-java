package cloud.pangeacyber.pangea.store.results;

import cloud.pangeacyber.pangea.store.models.ShareLinkItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShareLinkListResult {

	@JsonProperty("count")
	int count;

	@JsonProperty("last")
	String last;

	@JsonProperty("share_link_objects")
	List<ShareLinkItem> shareLinkObjects;

	public ShareLinkListResult() {}

	public int getCount() {
		return count;
	}

	public List<ShareLinkItem> getShareLinkObjects() {
		return shareLinkObjects;
	}

	public String getLast() {
		return last;
	}
}
