package cloud.pangeacyber.pangea.share.results;

import cloud.pangeacyber.pangea.share.models.ItemData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GetArchiveResult {

	/** A location where the archive can be downloaded from. (transfer_method: dest-url) */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("dest_url")
	private String destUrl;

	/** Number of objects included in the archive. */
	@JsonProperty("count")
	private int count;

	/** A list of all objects included in the archive. */
	@JsonProperty("objects")
	List<ItemData> objects;

	public GetArchiveResult() {}

	/** A location where the archive can be downloaded from. (transfer_method: dest-url) */
	public String getDestUrl() {
		return destUrl;
	}

	/** Number of objects included in the archive. */
	public int getCount() {
		return count;
	}

	/** A list of all objects included in the archive. */
	public List<ItemData> getObjects() {
		return objects;
	}
}
