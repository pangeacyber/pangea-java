package cloud.pangeacyber.pangea.authn.models;

import cloud.pangeacyber.pangea.intel.models.IPGeolocateData;
import cloud.pangeacyber.pangea.intel.models.IPReputationData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IPIntelligence {

	@JsonProperty("is_bad")
	private boolean isBad;

	@JsonProperty("is_vpn")
	private boolean isVPN;

	@JsonProperty("is_proxy")
	private boolean isProxy;

	@JsonProperty("reputation")
	private IPReputationData reputation;

	@JsonProperty("geolocation")
	private IPGeolocateData geolocation;

	public boolean isBad() {
		return isBad;
	}

	public boolean isVPN() {
		return isVPN;
	}

	public boolean isProxy() {
		return isProxy;
	}

	public IPReputationData getReputation() {
		return reputation;
	}

	public IPGeolocateData getGeolocation() {
		return geolocation;
	}
}
