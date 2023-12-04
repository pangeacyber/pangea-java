package cloud.pangeacyber.pangea.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AcceptedResult {

	@JsonProperty("ttl_mins")
	private int ttlMins;

	@JsonProperty("retry_counter")
	private int retryCounter;

	@JsonProperty("location")
	private String location = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("post_url")
	private String postURL = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("put_url")
	private String putURL = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("post_form_data")
	private Map<String, Object> postFormData = new HashMap<>();

	public boolean hasUploadURL() {
		return putURL != null || postURL != null;
	}

	public int getTtlMins() {
		return ttlMins;
	}

	public int getRetryCounter() {
		return retryCounter;
	}

	public String getLocation() {
		return location;
	}

	public String getPostURL() {
		return postURL;
	}

	public String getPutURL() {
		return putURL;
	}

	public Map<String, Object> getPostFormData() {
		return postFormData;
	}
}
