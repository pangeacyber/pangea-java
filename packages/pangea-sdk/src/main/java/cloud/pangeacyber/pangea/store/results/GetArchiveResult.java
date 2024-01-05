package cloud.pangeacyber.pangea.store.results;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetArchiveResult {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("dest_url")
	private String destUrl;

	@JsonProperty("count")
	private int count;

	public GetArchiveResult() {}

	public String getDestUrl() {
		return destUrl;
	}

	public int getCount() {
		return count;
	}
}
