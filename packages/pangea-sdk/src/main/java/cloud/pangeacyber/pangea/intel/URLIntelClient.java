package cloud.pangeacyber.pangea.intel;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.intel.requests.URLReputationRequest;
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
	 * Reputation check - provider, verbose, raw
	 * @pangea.description Retrieve a reputation score for a URL from a provider, including an optional detailed report.
	 * @param url The URL to be looked up
	 * @param provider Use reputation data from this provider
	 * @param verbose Echo the API parameters in the response
	 * @param raw Include raw data from this provider
	 * @return URLReputationResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * URLReputationResponse response = client.reputation(
	 *     "http://113.235.101.11:54384",
	 *     "crowdstrike",
	 *     true,
	 *     true);
	 * }
	 */
	public URLReputationResponse reputation(URLReputationRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/reputation", request, URLReputationResponse.class);
	}
}
