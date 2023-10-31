package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.IPDomainData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IPDomainResult extends IntelCommonResult {

	@JsonProperty("data")
	IPDomainData Data;

	public IPDomainData getData() {
		return Data;
	}
}
