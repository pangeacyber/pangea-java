package cloud.pangeacyber.pangea.share.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteResult {

	/** Number of objects deleted. */
	@JsonProperty("count")
	int count;

	public DeleteResult() {}

	/** Number of objects deleted. */
	public int getCount() {
		return count;
	}
}
