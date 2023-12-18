package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.IPProxyData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IPProxyResult extends IntelCommonResult {

	@JsonProperty("data")
	IPProxyData Data;

	public IPProxyData getData() {
		return Data;
	}
}
