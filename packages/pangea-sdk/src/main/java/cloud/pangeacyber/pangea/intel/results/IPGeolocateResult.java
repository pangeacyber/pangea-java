package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.IPGeolocateData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IPGeolocateResult extends IntelCommonResult {

	@JsonProperty("data")
	IPGeolocateData Data;

	public IPGeolocateData getData() {
		return Data;
	}
}
