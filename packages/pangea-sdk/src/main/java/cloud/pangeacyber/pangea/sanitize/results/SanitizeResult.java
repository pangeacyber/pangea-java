package cloud.pangeacyber.pangea.sanitize.results;

import cloud.pangeacyber.pangea.sanitize.models.SanitizeData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SanitizeResult {

	@JsonProperty("dest_url")
	@JsonInclude(Include.NON_NULL)
	private String destURL;

	@JsonProperty("dest_share_id")
	@JsonInclude(Include.NON_NULL)
	private String destShareID;

	@JsonProperty("data")
	private SanitizeData data;

	@JsonProperty("parameters")
	@JsonInclude(Include.NON_NULL)
	private Map<String, Object> parameters;

	public SanitizeResult() {}

	public String getDestURL() {
		return destURL;
	}

	public String getDestShareID() {
		return destShareID;
	}

	public SanitizeData getData() {
		return data;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}
}
