package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.URLReputationBulkData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class URLReputationBulkResult extends IntelCommonResult {

	@JsonProperty("data")
	URLReputationBulkData data;

	public URLReputationBulkData getData() {
		return data;
	}
}
