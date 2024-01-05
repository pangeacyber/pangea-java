package cloud.pangeacyber.pangea.store.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteResult {

	@JsonProperty("count")
	int count;

	public DeleteResult() {}

	public int getCount() {
		return count;
	}
}
