package cloud.pangeacyber.pangea.vault.results;

import cloud.pangeacyber.pangea.vault.models.ListItemData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListResult {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("items")
	List<ListItemData> items = null;

	@JsonProperty("count")
	int count;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("last")
	String last = null;

	public ListResult() {}

	public List<ListItemData> getItems() {
		return items;
	}

	public int getCount() {
		return count;
	}

	public String getLast() {
		return last;
	}
}
