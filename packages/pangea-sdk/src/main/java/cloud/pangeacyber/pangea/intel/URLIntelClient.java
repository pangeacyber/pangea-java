package cloud.pangeacyber.pangea.intel;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.intel.requests.URLReputationBulkRequest;
import cloud.pangeacyber.pangea.intel.requests.URLReputationRequest;
import cloud.pangeacyber.pangea.intel.responses.URLReputationBulkResponse;
import cloud.pangeacyber.pangea.intel.responses.URLReputationResponse;

public class URLIntelClient extends BaseClient {

	public static String serviceName = "url-intel";

	public URLIntelClient(Builder builder) {
		super(builder, serviceName);
	}

	public static class Builder extends BaseClient.Builder<Builder> {

		public Builder(Config config) {
			super(config);
		}

		public URLIntelClient build() {
			return new URLIntelClient(this);
		}
	}

	/**
	 * Reputation
	 * @pangea.description Retrieve a reputation score for a URL from a provider, including an optional detailed report.
	 * @pangea.operationId url_intel_post_v1_reputation
	 * @param request
	 * @return URLReputationResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * URLReputationRequest request = new URLReputationRequest
	 * 	.Builder("http://113.235.101.11:54384")
	 * 	.provider("crowdstrike")
	 * 	.verbose(true)
	 * 	.raw(true)
	 * 	.build();
	 *
	 * URLReputationResponse response = client.reputation(request);
	 * }
	 */
	public URLReputationResponse reputation(URLReputationRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/reputation", request, URLReputationResponse.class);
	}

	/**
	 * Reputation V2
	 * @pangea.description Retrieve a reputation scores for a list of URLs from a provider, including an optional detailed report.
	 * @pangea.operationId url_intel_post_v2_reputation
	 * @param request
	 * @return URLReputationBulkResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * String[] urls = {"http://113.235.101.11:54384"};
	 *
	 * URLReputationBulkRequest request = new URLReputationBulkRequest
	 * 	.Builder(urls)
	 * 	.provider("crowdstrike")
	 * 	.verbose(true)
	 * 	.raw(true)
	 * 	.build();
	 *
	 * URLReputationBulkResponse response = client.reputationBulk(request);
	 * }
	 */
	public URLReputationBulkResponse reputationBulk(URLReputationBulkRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v2/reputation", request, URLReputationBulkResponse.class);
	}
}
