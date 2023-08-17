package cloud.pangeacyber.pangea.intel;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.intel.requests.*;
import cloud.pangeacyber.pangea.intel.responses.*;

public class IPIntelClient extends BaseClient {

	public static String serviceName = "ip-intel";
	private static final boolean supportMultiConfig = false;

	public IPIntelClient(Builder builder) {
		super(builder, serviceName, supportMultiConfig);
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
	 * Geolocate
	 * @pangea.description Retrieve location information associated with an IP address.
	 * @pangea.operationId ip_intel_post_v1_geolocate
	 * @param request
	 * @return IPGeolocateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IPGeolocateRequest request = new IPGeolocateRequest
	 * 	.Builder("93.231.182.110")
	 * 	.provider("digitalelement")
	 * 	.verbose(true)
	 * 	.raw(true)
	 * 	.build();
	 *
	 * IPGeolocateResponse response = client.geolocate(request);
	 * }
	 */
	public IPGeolocateResponse geolocate(IPGeolocateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/geolocate", request, IPGeolocateResponse.class);
	}

	/**
	 * Domain
	 * @pangea.description Retrieve the domain name associated with an IP address.
	 * @pangea.operationId ip_intel_post_v1_domain
	 * @param request
	 * @return IpDomainResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IPDomainRequest request = new IPDomainRequest
	 * 	.Builder("93.231.182.110")
	 * 	.provider("digitalelement")
	 * 	.verbose(true)
	 * 	.raw(true)
	 * 	.build();
	 *
	 * IPDomainResponse response = client.getDomain(request);
	 * }
	 */
	public IPDomainResponse getDomain(IPDomainRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/domain", request, IPDomainResponse.class);
	}

	/**
	 * VPN
	 * @pangea.description Determine if an IP address originates from a VPN.
	 * @pangea.operationId ip_intel_post_v1_vpn
	 * @param request
	 * @return IpVPNResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IPVPNRequest request = new IPVPNRequest
	 * 	.Builder("93.231.182.110")
	 * 	.provider("digitalelement")
	 * 	.verbose(true)
	 * 	.raw(true)
	 * 	.build();
	 *
	 * IPVPNResponse response = client.isVPN(request);
	 * }
	 */
	public IPVPNResponse isVPN(IPVPNRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/vpn", request, IPVPNResponse.class);
	}

	/**
	 * Proxy
	 * @pangea.description Determine if an IP address originates from a proxy.
	 * @pangea.operationId ip_intel_post_v1_proxy
	 * @param request
	 * @return IpProxyResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IPProxyRequest request = new IPProxyRequest
	 * 	.Builder("34.201.32.172")
	 * 	.provider("digitalelement")
	 * 	.verbose(true)
	 * 	.raw(true)
	 * 	.build();
	 *
	 * IPProxyResponse response = client.isProxy(request);
	 * }
	 */
	public IPProxyResponse isProxy(IPProxyRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/proxy", request, IPProxyResponse.class);
	}

	/**
	 * Reputation
	 * @pangea.description Retrieve a reputation score for an IP address from a provider, including an optional detailed report.
	 * @pangea.operationId ip_intel_post_v1_reputation
	 * @param request
	 * @return IpReputationResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IPReputationRequest request = new IPReputationRequest
	 * 	.Builder("93.231.182.110")
	 * 	.provider("crowdstrike")
	 * 	.verbose(true)
	 * 	.raw(true)
	 * 	.build();
	 *
	 * IPReputationResponse response = client.reputation(request);
	 * }
	 */
	public IPReputationResponse reputation(IPReputationRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/reputation", request, IPReputationResponse.class);
	}
}
