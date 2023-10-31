package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.IPGeolocateBulkData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IPGeolocateBulkResult extends IntelCommonResult {

	@JsonProperty("data")
	IPGeolocateBulkData Data;

	public IPGeolocateBulkData getData() {
		return Data;
	}
}
