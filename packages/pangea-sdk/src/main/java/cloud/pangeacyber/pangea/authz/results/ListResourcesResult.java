package cloud.pangeacyber.pangea.authz.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListResourcesResult {

	@JsonProperty("ids")
	private String[] ids;

	public ListResourcesResult() {}

	public String[] getIds() {
		return ids;
	}
}
