package cloud.pangeacyber.pangea.store.results;

import cloud.pangeacyber.pangea.store.models.ItemData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetResult {

	@JsonProperty("object")
	ItemData object;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("dest_url")
	String destUrl;

	public GetResult() {}

	public ItemData getObject() {
		return object;
	}

	public String getDestUrl() {
		return destUrl;
	}
}
