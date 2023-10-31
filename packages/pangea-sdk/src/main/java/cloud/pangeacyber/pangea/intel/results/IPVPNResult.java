package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.IPVPNData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IPVPNResult extends IntelCommonResult {

	@JsonProperty("data")
	IPVPNData Data;

	public IPVPNData getData() {
		return Data;
	}
}
