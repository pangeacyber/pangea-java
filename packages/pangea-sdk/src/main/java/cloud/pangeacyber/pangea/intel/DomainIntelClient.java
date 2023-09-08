package cloud.pangeacyber.pangea.intel;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.intel.requests.DomainReputationRequest;
import cloud.pangeacyber.pangea.intel.requests.DomainWhoIsRequest;
import cloud.pangeacyber.pangea.intel.responses.DomainReputationResponse;
import cloud.pangeacyber.pangea.intel.responses.DomainWhoIsResponse;

public class DomainIntelClient extends BaseClient {

	public static final String serviceName = "domain-intel";
	private static final boolean supportMultiConfig = false;

	public DomainIntelClient(Builder builder) {
		super(builder, serviceName, supportMultiConfig);
	}

	public static class Builder extends BaseClient.Builder<Builder> {

		public Builder(Config config) {
			super(config);
		}

		public DomainIntelClient build() {
			return new DomainIntelClient(this);
		}
	}

	/**
	 * Reputation
	 * @pangea.description Retrieve domain reputation for a particular provider
	 * @pangea.operationId domain_intel_post_v1_reputation
	 * @param request
	 * @return DomainReputationResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * DomainReputationRequest request = new DomainReputationRequest
	 * 	.Builder("737updatesboeing.com")
	 * 	.provider("domaintools")
	 * 	.verbose(true)
	 * 	.raw(true)
	 * 	.build();
	 *
	 * DomainReputationResponse response = client.reputation(request);
	 * }
	 */
	public DomainReputationResponse reputation(DomainReputationRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/reputation", request, DomainReputationResponse.class);
	}

	/**
	 * WhoIs
	 * @pangea.description Retrieve who is for a domain from a provider, including an optional detailed report.
	 * @pangea.operationId domain_intel_post_v1_who_is
	 * @param request
	 * @return DomainWhoIsResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * DomainWhoIsRequest request = new DomainWhoIsRequest
	 * 	.Builder("737updatesboeing.com")
	 * 	.provider("domaintools")
	 * 	.verbose(true)
	 * 	.raw(true)
	 * 	.build();
	 *
	 * DomainWhoIsResponse response = client.whoIs(request);
	 * }
	 */
	public DomainWhoIsResponse whoIs(DomainWhoIsRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/whois", request, DomainWhoIsResponse.class);
	}
}
