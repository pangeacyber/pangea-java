package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.DomainWhoIsData;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class DomainWhoIsResult extends IntelCommonResult {

	@JsonProperty("data")
	DomainWhoIsData data;

	public DomainWhoIsData getData() {
		return data;
	}
}
