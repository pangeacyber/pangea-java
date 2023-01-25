package cloud.pangeacyber.pangea.intel;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

final class UrlLookupRequest {

	@JsonProperty("url")
	String Url;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("provider")
	String provider;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("verbose")
	Boolean verbose;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("raw")
	Boolean raw;

	UrlLookupRequest(String url, String provider, Boolean verbose, Boolean raw) {
		this.Url = url;
		this.provider = provider;
		this.verbose = verbose;
		this.raw = raw;
	}
}

public class UrlIntelClient extends Client {

	public static String serviceName = "url-intel";

	public UrlIntelClient(Config config) {
		super(config, serviceName);
	}

	private UrlLookupResponse lookupPost(String url, String provider, Boolean verbose, Boolean raw)
		throws PangeaException, PangeaAPIException {
		UrlLookupRequest request = new UrlLookupRequest(url, provider, verbose, raw);
		UrlLookupResponse resp = doPost("/v1/lookup", request, UrlLookupResponse.class);
		return resp;
	}

	/**
	 * Look up a URL
	 * @pangea.description Retrieve a reputation score for a URL from a provider, including an optional detailed report.
	 * @param url The URL to be looked up
	 * @return UrlLookupResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * UrlLookupResponse response = client.lookup(
	 *     "http://113.235.101.11:54384");
	 * }
	 */
	public UrlLookupResponse lookup(String url) throws PangeaException, PangeaAPIException {
		return lookupPost(url, null, null, null);
	}

	/**
	 * Look up a URL - provider
	 * @pangea.description Retrieve a reputation score for a URL from a provider, including an optional detailed report.
	 * @param url The URL to be looked up
	 * @param provider Use reputation data from this provider
	 * @return UrlLookupResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * UrlLookupResponse response = client.lookup(
	 *     "http://113.235.101.11:54384",
	 *     "crowdstrike");
	 * }
	 */
	public UrlLookupResponse lookup(String url, String provider) throws PangeaException, PangeaAPIException {
		return lookupPost(url, provider, null, null);
	}

	/**
	 * Look up a URL - verbose, raw
	 * @pangea.description Retrieve a reputation score for a URL from a provider, including an optional detailed report.
	 * @param url The URL to be looked up
	 * @param verbose Echo the API parameters in the response
	 * @param raw Include raw data from this provider
	 * @return UrlLookupResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * UrlLookupResponse response = client.lookup(
	 *     "http://113.235.101.11:54384",
	 *     true,
	 *     true);
	 * }
	 */
	public UrlLookupResponse lookup(String url, boolean verbose, boolean raw)
		throws PangeaException, PangeaAPIException {
		return lookupPost(url, null, verbose, raw);
	}

	/**
	 * Look up a URL - provider, verbose, raw
	 * @pangea.description Retrieve a reputation score for a URL from a provider, including an optional detailed report.
	 * @param url The URL to be looked up
	 * @param provider Use reputation data from this provider
	 * @param verbose Echo the API parameters in the response
	 * @param raw Include raw data from this provider
	 * @return UrlLookupResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * UrlLookupResponse response = client.lookup(
	 *     "http://113.235.101.11:54384",
	 *     "crowdstrike",
	 *     true,
	 *     true);
	 * }
	 */
	public UrlLookupResponse lookup(String url, String provider, boolean verbose, boolean raw)
		throws PangeaException, PangeaAPIException {
		return lookupPost(url, provider, verbose, raw);
	}
}
