package cloud.pangeacyber.pangea.share.results;

import cloud.pangeacyber.pangea.share.models.ItemData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListResult {

	@JsonProperty("count")
	int count;

	@JsonProperty("last")
	String last;

	@JsonProperty("objects")
	List<ItemData> objects;

	public ListResult() {}

	public int getCount() {
		return count;
	}

	public String getLast() {
		return last;
	}

	public List<ItemData> getObjects() {
		return objects;
	}
}
