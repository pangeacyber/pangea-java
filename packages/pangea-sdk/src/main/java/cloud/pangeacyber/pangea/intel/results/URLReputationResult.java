package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.URLReputationData;
import cloud.pangeacyber.pangea.intel.models.URLReputationDataItem;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class URLReputationResult extends IntelCommonResult {

	@JsonProperty("data")
	URLReputationData data;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("data_details")
	HashMap<String,URLReputationDataItem> dataDetails = null;

	public URLReputationData getData() {
		return data;
	}

	public Map<String,URLReputationDataItem> getDataDetails() {
		return dataDetails;
	}

}
