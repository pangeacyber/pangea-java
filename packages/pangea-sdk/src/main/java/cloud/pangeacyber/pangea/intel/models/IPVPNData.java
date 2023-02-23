package cloud.pangeacyber.pangea.intel.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IPVPNData {

	@JsonProperty("is_vpn")
	boolean isVPN;

	public boolean isVPN() {
		return isVPN;
	}
}
