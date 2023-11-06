package cloud.pangeacyber.pangea.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AcceptedStatus {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("upload_url")
	private String uploadURL = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("upload_details")
	private Map<String, Object> uploadDetails = new HashMap<>();

	public String getUploadURL() {
		return uploadURL;
	}

	public Map<String, Object> getUploadDetails() {
		return uploadDetails;
	}
}
