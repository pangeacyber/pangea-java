package cloud.pangeacyber.pangea.intel;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.intel.models.DomainLookupResponse;
import cloud.pangeacyber.pangea.intel.models.DomainReputationResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

final class DomainLookupRequest extends BaseRequest {

	@JsonProperty("domain")
	String Domain;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("provider")
	String provider;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("verbose")
	Boolean verbose;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("raw")
	Boolean raw;

	DomainLookupRequest(String domain, String provider, Boolean verbose, Boolean raw) {
		this.Domain = domain;
		this.provider = provider;
		this.verbose = verbose;
		this.raw = raw;
	}
}

final class DomainReputationRequest extends BaseRequest {

	@JsonProperty("domain")
	String Domain;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("provider")
	String provider;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("verbose")
	Boolean verbose;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("raw")
	Boolean raw;

	DomainReputationRequest(String domain, String provider, Boolean verbose, Boolean raw) {
		this.Domain = domain;
		this.provider = provider;
		this.verbose = verbose;
		this.raw = raw;
	}
}

public class DomainIntelClient extends Client {

	public static final String serviceName = "domain-intel";
	private static final boolean supportMultiConfig = false;

	public DomainIntelClient(Config config) {
		super(config, serviceName, supportMultiConfig);
	}

	private DomainLookupResponse lookupPost(String domain, String provider, Boolean verbose, Boolean raw)
		throws PangeaException, PangeaAPIException {
		DomainLookupRequest request = new DomainLookupRequest(domain, provider, verbose, raw);
		DomainLookupResponse resp = doPost("/v1/reputation", request, DomainLookupResponse.class);
		return resp;
	}

	/**
	 * Domain reputation
	 * @pangea.description Retrieve domain reputation from a default provider.
	 * @deprecated use reputation instead.
	 * @param domain domain address to be looked up
	 * @return DomainLookupResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * DomainLookupResponse response = client.lookup(
	 *     "737updatesboeing.com");
	 * }
	 */
	public DomainLookupResponse lookup(String domain) throws PangeaException, PangeaAPIException {
		return lookupPost(domain, null, null, null);
	}

	/**
	 * Domain reputation - domain, provider
	 * @pangea.description Retrieve domain reputation for a particular provider
	 * @deprecated use reputation instead.
	 * @param domain domain address to be looked up
	 * @param provider provider to get reputation from
	 * @return DomainLookupResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * DomainLookupResponse response = client.lookup(
	 *     "737updatesboeing.com",
	 *     "domaintools");
	 * }
	 */
	public DomainLookupResponse lookup(String domain, String provider) throws PangeaException, PangeaAPIException {
		return lookupPost(domain, provider, null, null);
	}

	/**
	 * Domain reputation - domain, verbose, raw
	 * @pangea.description Retrieve domain reputation from a default provider.
	 * @deprecated use reputation instead.
	 * @param domain domain address to be looked up
	 * @param verbose select a more verbose response
	 * @param raw if true response include provider raw response. This should vary from one provider to another one.
	 * @return DomainLookupResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * DomainLookupResponse response = client.lookup(
	 *     "737updatesboeing.com",
	 *     true,
	 *     true);
	 * }
	 */
	public DomainLookupResponse lookup(String domain, boolean verbose, boolean raw)
		throws PangeaException, PangeaAPIException {
		return lookupPost(domain, null, verbose, raw);
	}

	/**
	 * Domain reputation - domain, provider, verbose, raw
	 * @pangea.description Retrieve domain reputation for a particular provider
	 * @deprecated use reputation instead.
	 * @param domain domain address to be looked up
	 * @param provider provider to get reputation from
	 * @param verbose select a more verbose response
	 * @param raw if true response include provider raw response. This should vary from one provider to another one.
	 * @return DomainLookupResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * DomainLookupResponse response = client.lookup(
	 *     "737updatesboeing.com",
	 *     "domaintools",
	 *     true,
	 *     true);
	 * }
	 */
	public DomainLookupResponse lookup(String domain, String provider, boolean verbose, boolean raw)
		throws PangeaException, PangeaAPIException {
		return lookupPost(domain, provider, verbose, raw);
	}

	private DomainReputationResponse reputationPost(String domain, String provider, Boolean verbose, Boolean raw)
		throws PangeaException, PangeaAPIException {
		DomainReputationRequest request = new DomainReputationRequest(domain, provider, verbose, raw);
		DomainReputationResponse resp = doPost("/v1/reputation", request, DomainReputationResponse.class);
		return resp;
	}

	/**
	 * Reputation check
	 * @pangea.description Retrieve domain reputation from a default provider.
	 * @pangea.operationId domain_intel_post_v1_reputation
	 * @param domain domain address to be looked up
	 * @return DomainReputationResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * DomainReputationResponse response = client.reputation(
	 *     "737updatesboeing.com");
	 * }
	 */
	public DomainReputationResponse reputation(String domain) throws PangeaException, PangeaAPIException {
		return reputationPost(domain, null, null, null);
	}

	/**
	 * Reputation check - domain, provider
	 * @pangea.description Retrieve domain reputation for a particular provider
	 * @param domain domain address to be looked up
	 * @param provider provider to get reputation from
	 * @return DomainReputationResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * DomainReputationResponse response = client.reputation(
	 *     "737updatesboeing.com",
	 *     "domaintools");
	 * }
	 */
	public DomainReputationResponse reputation(String domain, String provider)
		throws PangeaException, PangeaAPIException {
		return reputationPost(domain, provider, null, null);
	}

	/**
	 * Reputation check - domain, verbose, raw
	 * @pangea.description Retrieve domain reputation from a default provider.
	 * @param domain domain address to be looked up
	 * @param verbose select a more verbose response
	 * @param raw if true response include provider raw response. This should vary from one provider to another one.
	 * @return DomainReputationResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * DomainReputationResponse response = client.reputation(
	 *     "737updatesboeing.com",
	 *     true,
	 *     true);
	 * }
	 */
	public DomainReputationResponse reputation(String domain, boolean verbose, boolean raw)
		throws PangeaException, PangeaAPIException {
		return reputationPost(domain, null, verbose, raw);
	}

	/**
	 * Reputation check - domain, provider, verbose, raw
	 * @pangea.description Retrieve domain reputation for a particular provider
	 * @param domain domain address to be looked up
	 * @param provider provider to get reputation from
	 * @param verbose select a more verbose response
	 * @param raw if true response include provider raw response. This should vary from one provider to another one.
	 * @return DomainReputationResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * DomainReputationResponse response = client.reputation(
	 *     "737updatesboeing.com",
	 *     "domaintools",
	 *     true,
	 *     true);
	 * }
	 */
	public DomainReputationResponse reputation(String domain, String provider, boolean verbose, boolean raw)
		throws PangeaException, PangeaAPIException {
		return reputationPost(domain, provider, verbose, raw);
	}
}
