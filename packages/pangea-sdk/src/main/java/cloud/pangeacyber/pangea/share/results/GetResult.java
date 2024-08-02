package cloud.pangeacyber.pangea.share.results;

import cloud.pangeacyber.pangea.share.models.ItemData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetResult {

	@JsonProperty("object")
	ItemData object;

	/** A URL where the file can be downloaded from. (transfer_method: dest-url) */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("dest_url")
	String destUrl;

	public GetResult() {}

	public ItemData getObject() {
		return object;
	}

	/** A URL where the file can be downloaded from. (transfer_method: dest-url) */
	public String getDestUrl() {
		return destUrl;
	}
}
