package cloud.pangeacyber.pangea.audit.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DownloadResult {

	/** URL where search results can be downloaded. */
	@JsonProperty("dest_url")
	private String destURL;

	/**
	 * The time when the results will no longer be available to page through via
	 * the results API.
	 */
	@JsonProperty("expires_at")
	private String expiresAt;

	public String getDestURL() {
		return destURL;
	}

	public String getExpiresAt() {
		return expiresAt;
	}
}
