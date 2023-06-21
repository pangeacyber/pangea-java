package cloud.pangeacyber.pangea.intel;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.intel.requests.*;
import cloud.pangeacyber.pangea.intel.responses.*;

public class IPIntelClient extends BaseClient {

	public static String serviceName = "ip-intel";

	public IPIntelClient(Builder builder) {
		super(builder, serviceName);
	}

	public static class Builder extends BaseClient.Builder<Builder> {

		public Builder(Config config) {
			super(config);
		}

		public IPIntelClient build() {
			return new IPIntelClient(this);
		}
	}

	/**
	 * Geolocate - provider, verbose, raw
	 * @pangea.description Retrieve information about the location of an IP address
	 * @param ip The IP to be looked up
	 * @param provider Use reputation data from this provider
	 * @param verbose Echo the API parameters in the response
	 * @param raw Include raw data from this provider
	 * @return IPGeolocateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
		// FIXME:
	 */
	public IPGeolocateResponse geolocate(IPGeolocateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/geolocate", request, IPGeolocateResponse.class);
	}

	/**
	 * Domain - ip, provider, verbose, raw
	 * @pangea.description Retrieve the domain name associated with an IP address
	 * @param ip The IP to be looked up
	 * @return IpDomainResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
		// FIXME:
	 */
	public IPDomainResponse getDomain(IPDomainRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/domain", request, IPDomainResponse.class);
	}

	/**
	 * VPN - ip, provider, verbose, raw
	 * @pangea.description Determine if an IP address is provided by a VPN service
	 * @param ip The IP to be looked up
	 * @return IpVPNResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
		// FIXME:
	 */
	public IPVPNResponse isVPN(IPVPNRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/vpn", request, IPVPNResponse.class);
	}

	/**
	 * Proxy - provider, verbose, raw
	 * @pangea.description Determine if an IP address is provided by a proxy service
	 * @param ip The IP to be looked up
	 * @param provider Use reputation data from this provider
	 * @param verbose Echo the API parameters in the response
	 * @param raw Include raw data from this provider
	 * @return IpProxyResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
		//FIXME:
	 */
	public IPProxyResponse isProxy(IPProxyRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/proxy", request, IPProxyResponse.class);
	}

	/**
	 * Reputation - provider, verbose, raw
	 * @pangea.description Retrieve a reputation score for an IP address from a provider, including an optional detailed report.
	 * @param ip The IP to be looked up
	 * @param provider Use reputation data from this provider
	 * @param verbose Echo the API parameters in the response
	 * @param raw Include raw data from this provider
	 * @return IpReputationResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
		//FIXME:
	 */
	public IPReputationResponse reputation(IPReputationRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/reputation", request, IPReputationResponse.class);
	}
}
