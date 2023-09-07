package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.URLReputationData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class URLReputationResult extends IntelCommonResult {

	@JsonProperty("data")
	URLReputationData data;

	public URLReputationData getData() {
		return data;
	}
}
