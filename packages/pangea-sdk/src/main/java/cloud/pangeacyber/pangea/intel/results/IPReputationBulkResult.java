package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.IPReputationBulkData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class IPReputationBulkResult extends IntelCommonResult {

	@JsonProperty("data")
	IPReputationBulkData data;

	public IPReputationBulkData getData() {
		return data;
	}
}
