package cloud.pangeacyber.pangea.share.results;

import cloud.pangeacyber.pangea.share.models.ItemData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PutResult {

	@JsonProperty("object")
	ItemData object;

	public PutResult() {}

	public ItemData getObject() {
		return object;
	}
}
