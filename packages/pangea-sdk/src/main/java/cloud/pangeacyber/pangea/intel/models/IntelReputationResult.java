package cloud.pangeacyber.pangea.intel.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IntelReputationResult {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("parameters")
	Map<String, Object> parameters;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("raw_data")
	Map<String, Object> rawData;

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public Map<String, Object> getRawData() {
		return rawData;
	}
}
