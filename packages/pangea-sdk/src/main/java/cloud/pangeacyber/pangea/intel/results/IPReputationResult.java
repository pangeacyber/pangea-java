package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.IPReputationData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class IPReputationResult extends IntelCommonResult {

	@JsonProperty("data")
	IPReputationData data;

	public IPReputationData getData() {
		return data;
	}
}
