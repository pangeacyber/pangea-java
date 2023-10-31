package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.IPProxyBulkData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IPProxyBulkResult extends IntelCommonResult {

	@JsonProperty("data")
	IPProxyBulkData Data;

	public IPProxyBulkData getData() {
		return Data;
	}
}
