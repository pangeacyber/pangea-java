package cloud.pangeacyber.pangea.intel;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.intel.requests.DomainReputationRequest;
import cloud.pangeacyber.pangea.intel.responses.DomainReputationResponse;

public class DomainIntelClient extends Client {

	public static String serviceName = "domain-intel";

	public DomainIntelClient(Config config) {
		super(config, serviceName);
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
	 * // FIXME:
	 */
	public DomainReputationResponse reputation(DomainReputationRequest request)
		throws PangeaException, PangeaAPIException {
		return doPost("/v1/reputation", request, DomainReputationResponse.class);
	}
}
