package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.DomainReputationBulkData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class DomainReputationBulkResult extends IntelCommonResult {

	@JsonProperty("data")
	DomainReputationBulkData data;

	public DomainReputationBulkData getData() {
		return data;
	}
}
