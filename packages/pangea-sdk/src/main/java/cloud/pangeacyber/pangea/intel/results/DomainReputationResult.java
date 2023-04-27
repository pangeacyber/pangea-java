package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.DomainReputationData;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class DomainReputationResult extends IntelCommonResult {

	@JsonProperty("data")
	DomainReputationData data;

	public DomainReputationData getData() {
		return data;
	}
}
