package cloud.pangeacyber.pangea.share.results;

import cloud.pangeacyber.pangea.share.models.ItemData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateResult {

	@JsonProperty("object")
	ItemData object;

	public UpdateResult() {}

	public ItemData getObject() {
		return object;
	}
}
