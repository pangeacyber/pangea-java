package cloud.pangeacyber.pangea.share.results;

import cloud.pangeacyber.pangea.share.models.ShareLinkItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShareLinkListResult {

	/** The total number of share links matched by the list request. */
	@JsonProperty("count")
	int count;

	/** Used to fetch the next page of the current listing when provided in a repeated request's last parameter. */
	@JsonProperty("last")
	String last;

	@JsonProperty("share_link_objects")
	List<ShareLinkItem> shareLinkObjects;

	public ShareLinkListResult() {}

	/** The total number of share links matched by the list request. */
	public int getCount() {
		return count;
	}

	/** Used to fetch the next page of the current listing when provided in a repeated request's last parameter. */
	public String getLast() {
		return last;
	}

	public List<ShareLinkItem> getShareLinkObjects() {
		return shareLinkObjects;
	}
}
