package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.IPReputationData;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class IPReputationResult extends IntelCommonResult {

	@JsonProperty("data")
	IPReputationData data;

	public IPReputationData getData() {
		return data;
	}
}
