package cloud.pangeacyber.pangea.audit.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DownloadResult {

	/**
	 * URL where search results can be downloaded.
	 */
	@JsonProperty("dest_url")
	String destURL;

	public String getDestURL() {
		return destURL;
	}
}
