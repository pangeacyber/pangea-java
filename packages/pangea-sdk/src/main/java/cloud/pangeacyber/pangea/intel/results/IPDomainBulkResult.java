package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.IPDomainBulkData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IPDomainBulkResult extends IntelCommonResult {

	@JsonProperty("data")
	IPDomainBulkData Data;

	public IPDomainBulkData getData() {
		return Data;
	}
}
