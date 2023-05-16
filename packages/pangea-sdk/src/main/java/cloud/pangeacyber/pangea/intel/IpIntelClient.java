package cloud.pangeacyber.pangea.intel;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.intel.models.IPDomainResponse;
import cloud.pangeacyber.pangea.intel.models.IPGeolocateResponse;
import cloud.pangeacyber.pangea.intel.models.IPProxyResponse;
import cloud.pangeacyber.pangea.intel.models.IPReputationResponse;
import cloud.pangeacyber.pangea.intel.models.IPVPNResponse;
import cloud.pangeacyber.pangea.intel.models.IpLookupResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

final class IpCommonRequest {

	@JsonProperty("ip")
	String Ip;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("provider")
	String provider;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("verbose")
	Boolean verbose;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("raw")
	Boolean raw;

	IpCommonRequest(String ip, String provider, Boolean verbose, Boolean raw) {
		this.Ip = ip;
		this.provider = provider;
		this.verbose = verbose;
		this.raw = raw;
	}
}

public class IpIntelClient extends Client {

	public static String serviceName = "ip-intel";

	public IpIntelClient(Config config) {
		super(config, serviceName);
	}

	private IpLookupResponse lookupPost(String ip, String provider, Boolean verbose, Boolean raw)
		throws PangeaException, PangeaAPIException {
		IpCommonRequest request = new IpCommonRequest(ip, provider, verbose, raw);
		IpLookupResponse resp = doPost("/v1/reputation", request, IpLookupResponse.class);
		return resp;
	}

	private IPReputationResponse reputationPost(String ip, String provider, Boolean verbose, Boolean raw)
		throws PangeaException, PangeaAPIException {
		IpCommonRequest request = new IpCommonRequest(ip, provider, verbose, raw);
		IPReputationResponse resp = doPost("/v1/reputation", request, IPReputationResponse.class);
		return resp;
	}

	/**
	 * IP reputation
	 * @pangea.description Retrieve a reputation score for an IP address from a provider, including an optional detailed report.
	 * @deprecated use reputation instead.
	 * @param ip The IP to be looked up
	 * @return IpLookupResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IpLookupResponse response = client.lookup(
	 *     "93.231.182.110");
	 * }
	 */
	@Deprecated
	public IpLookupResponse lookup(String ip) throws PangeaException, PangeaAPIException {
		return lookupPost(ip, null, null, null);
	}

	/**
	 * IP reputation - provider
	 * @pangea.description Retrieve a reputation score for an IP address from a provider, including an optional detailed report.
	 * @deprecated use reputation instead.
	 * @param ip The IP to be looked up
	 * @param provider Use reputation data from this provider
	 * @return IpLookupResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IpLookupResponse response = client.lookup(
	 *     "93.231.182.110",
	 *     "crowdstrike");
	 * }
	 */
	@Deprecated
	public IpLookupResponse lookup(String ip, String provider) throws PangeaException, PangeaAPIException {
		return lookupPost(ip, provider, null, null);
	}

	/**
	 * IP reputation - verbose, raw
	 * @pangea.description Retrieve a reputation score for an IP address from a provider, including an optional detailed report.
	 * @deprecated use reputation instead.
	 * @param ip The IP to be looked up
	 * @param verbose Echo the API parameters in the response
	 * @param raw Include raw data from this provider
	 * @return IpLookupResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IpLookupResponse response = client.lookup(
	 *     "93.231.182.110",
	 *     true,
	 *     true);
	 * }
	 */
	@Deprecated
	public IpLookupResponse lookup(String ip, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
		return lookupPost(ip, null, verbose, raw);
	}

	/**
	 * IP reputation - provider, verbose, raw
	 * @pangea.description Retrieve a reputation score for an IP address from a provider, including an optional detailed report.
	 * @deprecated use reputation instead.
	 * @param ip The IP to be looked up
	 * @param provider Use reputation data from this provider
	 * @param verbose Echo the API parameters in the response
	 * @param raw Include raw data from this provider
	 * @return IpLookupResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IpLookupResponse response = client.lookup(
	 *     "93.231.182.110",
	 *     "crowdstrike",
	 *     true,
	 *     true);
	 * }
	 */
	@Deprecated
	public IpLookupResponse lookup(String ip, String provider, boolean verbose, boolean raw)
		throws PangeaException, PangeaAPIException {
		return lookupPost(ip, provider, verbose, raw);
	}

	private IPGeolocateResponse geolocatePost(String ip, String provider, Boolean verbose, Boolean raw)
		throws PangeaException, PangeaAPIException {
		IpCommonRequest request = new IpCommonRequest(ip, provider, verbose, raw);
		IPGeolocateResponse resp = doPost("/v1/geolocate", request, IPGeolocateResponse.class);
		return resp;
	}

	/**
	 * Geolocate
	 * @pangea.description Retrieve information about the location of an IP address
	 * @param ip The IP to be looked up
	 * @return IPGeolocateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IpVPNResponse response = client.geolocate(
	 *     "93.231.182.110");
	 * }
	 */
	public IPGeolocateResponse geolocate(String ip) throws PangeaException, PangeaAPIException {
		return geolocatePost(ip, null, null, null);
	}

	/**
	 * Geolocate - provider
	 * @pangea.description Retrieve information about the location of an IP address
	 * @pangea.operationId ip_intel_post_v1_geolocate
	 * @param ip The IP to be looked up
	 * @param provider Use reputation data from this provider
	 * @return IPGeolocateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IpVPNResponse response = client.geolocate(
	 *     "93.231.182.110",
	 *     "digitalelement");
	 * }
	 */
	public IPGeolocateResponse geolocate(String ip, String provider) throws PangeaException, PangeaAPIException {
		return geolocatePost(ip, provider, null, null);
	}

	/**
	 * Geolocate - verbose, raw
	 * @pangea.description Retrieve information about the location of an IP address
	 * @param ip The IP to be looked up
	 * @param verbose Echo the API parameters in the response
	 * @param raw Include raw data from this provider
	 * @return IPGeolocateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IpVPNResponse response = client.geolocate(
	 *     "93.231.182.110",
	 *     true,
	 *     true);
	 * }
	 */
	public IPGeolocateResponse geolocate(String ip, boolean verbose, boolean raw)
		throws PangeaException, PangeaAPIException {
		return geolocatePost(ip, null, verbose, raw);
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
	 * IpVPNResponse response = client.geolocate(
	 *     "93.231.182.110",
	 *     "digitalelement",
	 *     true,
	 *     true);
	 * }
	 */
	public IPGeolocateResponse geolocate(String ip, String provider, boolean verbose, boolean raw)
		throws PangeaException, PangeaAPIException {
		return geolocatePost(ip, provider, verbose, raw);
	}

	private IPDomainResponse domainPost(String ip, String provider, Boolean verbose, Boolean raw)
		throws PangeaException, PangeaAPIException {
		IpCommonRequest request = new IpCommonRequest(ip, provider, verbose, raw);
		IPDomainResponse resp = doPost("/v1/domain", request, IPDomainResponse.class);
		return resp;
	}

	/**
	 * Domain
	 * @pangea.description Retrieve the domain name associated with an IP address
	 * @param ip The IP to be looked up
	 * @return IpDomainResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IpVPNResponse response = client.getDomain(
	 *     "93.231.182.110");
	 * }
	 */
	public IPDomainResponse getDomain(String ip) throws PangeaException, PangeaAPIException {
		return domainPost(ip, null, null, null);
	}

	/**
	 * Domain - ip, provider
	 * @pangea.description Retrieve the domain name associated with an IP address
	 * @operationId ip_intel_post_v1_domain
	 * @param ip The IP to be looked up
	 * @return IpDomainResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IpVPNResponse response = client.getDomain(
	 *     "93.231.182.110",
	 *      "digitalelement");
	 * }
	 */
	public IPDomainResponse getDomain(String ip, String provider) throws PangeaException, PangeaAPIException {
		return domainPost(ip, provider, null, null);
	}

	/**
	 * Domain - ip, verbose, raw
	 * @pangea.description Retrieve the domain name associated with an IP address
	 * @param ip The IP to be looked up
	 * @return IpDomainResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IpVPNResponse response = client.getDomain(
	 *     "93.231.182.110",
	 *      true,
	 *      true);
	 * }
	 */
	public IPDomainResponse getDomain(String ip, boolean verbose, boolean raw)
		throws PangeaException, PangeaAPIException {
		return domainPost(ip, null, verbose, raw);
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
	 * IpVPNResponse response = client.getDomain(
	 *     "93.231.182.110",
	 *      "digitalelement",
	 *      true,
	 *      true);
	 * }
	 */
	public IPDomainResponse getDomain(String ip, String provider, boolean verbose, boolean raw)
		throws PangeaException, PangeaAPIException {
		return domainPost(ip, provider, verbose, raw);
	}

	private IPVPNResponse vpnPost(String ip, String provider, Boolean verbose, Boolean raw)
		throws PangeaException, PangeaAPIException {
		IpCommonRequest request = new IpCommonRequest(ip, provider, verbose, raw);
		IPVPNResponse resp = doPost("/v1/vpn", request, IPVPNResponse.class);
		return resp;
	}

	/**
	 * VPN
	 * @pangea.description Determine if an IP address is provided by a VPN service
	 * @param ip The IP to be looked up
	 * @return IpVPNResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IpVPNResponse response = client.isVPN(
	 *     "93.231.182.110");
	 * }
	 */
	public IPVPNResponse isVPN(String ip) throws PangeaException, PangeaAPIException {
		return vpnPost(ip, null, null, null);
	}

	/**
	 * VPN - ip, provider
	 * @pangea.description Determine if an IP address is provided by a VPN service
	 * @pangea.operationId ip_intel_post_v1_vpn
	 * @param ip The IP to be looked up
	 * @return IpVPNResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IpVPNResponse response = client.isVPN(
	 *     "93.231.182.110",
	 *      "digitalelement");
	 * }
	 */
	public IPVPNResponse isVPN(String ip, String provider) throws PangeaException, PangeaAPIException {
		return vpnPost(ip, provider, null, null);
	}

	/**
	 * VPN - ip, verbose, raw
	 * @pangea.description Determine if an IP address is provided by a VPN service
	 * @param ip The IP to be looked up
	 * @return IpVPNResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IpVPNResponse response = client.isVPN(
	 *     "93.231.182.110",
	 *      true,
	 *      true);
	 * }
	 */
	public IPVPNResponse isVPN(String ip, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
		return vpnPost(ip, null, verbose, raw);
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
	 * IpVPNResponse response = client.isVPN(
	 *     "93.231.182.110",
	 *      "digitalelement",
	 *      true,
	 *      true);
	 * }
	 */
	public IPVPNResponse isVPN(String ip, String provider, boolean verbose, boolean raw)
		throws PangeaException, PangeaAPIException {
		return vpnPost(ip, provider, verbose, raw);
	}

	private IPProxyResponse proxyPost(String ip, String provider, Boolean verbose, Boolean raw)
		throws PangeaException, PangeaAPIException {
		IpCommonRequest request = new IpCommonRequest(ip, provider, verbose, raw);
		IPProxyResponse resp = doPost("/v1/proxy", request, IPProxyResponse.class);
		return resp;
	}

	/**
	 * Proxy
	 * @pangea.description Determine if an IP address is provided by a proxy service
	 * @param ip The IP to be looked up
	 * @return IpProxyResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IpProxyResponse response = client.isProxy(
	 *     "93.231.182.110");
	 * }
	 */
	public IPProxyResponse isProxy(String ip) throws PangeaException, PangeaAPIException {
		return proxyPost(ip, null, null, null);
	}

	/**
	 * Proxy - provider
	 * @pangea.description Determine if an IP address is provided by a proxy service
	 * @pangea.operationId ip_intel_post_v1_proxy
	 * @param ip The IP to be looked up
	 * @param provider Use reputation data from this provider
	 * @return IpProxyResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IpProxyResponse response = client.isProxy(
	 *     "93.231.182.110",
	 *      "digitalelement");
	 * }
	 */
	public IPProxyResponse isProxy(String ip, String provider) throws PangeaException, PangeaAPIException {
		return proxyPost(ip, provider, null, null);
	}

	/**
	 * Proxy - verbose, raw
	 * @pangea.description Determine if an IP address is provided by a proxy service
	 * @param ip The IP to be looked up
	 * @param verbose Echo the API parameters in the response
	 * @param raw Include raw data from this provider
	 * @return IpProxyResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IpProxyResponse response = client.isProxy(
	 *     "93.231.182.110",
	 *      true,
	 *      true);
	 * }
	 */
	public IPProxyResponse isProxy(String ip, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
		return proxyPost(ip, null, verbose, raw);
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
	 * IpProxyResponse response = client.isProxy(
	 *     "93.231.182.110",
	 *      "digitalelement",
	 *      true,
	 *      true);
	 * }
	 */
	public IPProxyResponse isProxy(String ip, String provider, boolean verbose, boolean raw)
		throws PangeaException, PangeaAPIException {
		return proxyPost(ip, provider, verbose, raw);
	}

	/**
	 * Reputation
	 * @pangea.description Retrieve a reputation score for an IP address from a provider, including an optional detailed report.
	 * @param ip The IP to be looked up
	 * @return IpReputationResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IpReputationResponse response = client.reputation(
	 *     "93.231.182.110");
	 * }
	 */
	public IPReputationResponse reputation(String ip) throws PangeaException, PangeaAPIException {
		return reputationPost(ip, null, null, null);
	}

	/**
	 * Reputation - provider
	 * @pangea.description Retrieve a reputation score for an IP address from a provider, including an optional detailed report.
	 * @pangea.@operationId ip_intel_post_v1_reputation
	 * @param ip The IP to be looked up
	 * @param provider Use reputation data from this provider
	 * @return IpReputationResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IpReputationResponse response = client.reputation(
	 *     "93.231.182.110",
	 *     "crowdstrike");
	 * }
	 */
	public IPReputationResponse reputation(String ip, String provider) throws PangeaException, PangeaAPIException {
		return reputationPost(ip, provider, null, null);
	}

	/**
	 * Reputation - verbose, raw
	 * @pangea.description Retrieve a reputation score for an IP address from a provider, including an optional detailed report.
	 * @param ip The IP to be looked up
	 * @param verbose Echo the API parameters in the response
	 * @param raw Include raw data from this provider
	 * @return IpReputationResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * IpReputationResponse response = client.reputation(
	 *     "93.231.182.110",
	 *     true,
	 *     true);
	 * }
	 */
	public IPReputationResponse reputation(String ip, boolean verbose, boolean raw)
		throws PangeaException, PangeaAPIException {
		return reputationPost(ip, null, verbose, raw);
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
	 * IpReputationResponse response = client.reputation(
	 *     "93.231.182.110",
	 *     "crowdstrike",
	 *     true,
	 *     true);
	 * }
	 */
	public IPReputationResponse reputation(String ip, String provider, boolean verbose, boolean raw)
		throws PangeaException, PangeaAPIException {
		return reputationPost(ip, provider, verbose, raw);
	}
}
