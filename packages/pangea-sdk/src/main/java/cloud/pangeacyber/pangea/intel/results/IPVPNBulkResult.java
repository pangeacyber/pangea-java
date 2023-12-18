package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.IPVPNBulkData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IPVPNBulkResult extends IntelCommonResult {

	@JsonProperty("data")
	IPVPNBulkData Data;

	public IPVPNBulkData getData() {
		return Data;
	}
}
