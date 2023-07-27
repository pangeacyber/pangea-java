package cloud.pangeacyber.pangea.intel;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.intel.requests.DomainReputationRequest;
import cloud.pangeacyber.pangea.intel.responses.DomainReputationResponse;

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
	 * Reputation check - domain, provider, verbose, raw
	 * @pangea.description Retrieve domain reputation for a particular provider
	 * @param request
	 * @return DomainReputationResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 */
	public DomainReputationResponse reputation(DomainReputationRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/reputation", request, DomainReputationResponse.class);
	}
}
