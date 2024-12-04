package cloud.pangeacyber.pangea.intel.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BreachResult {

	/**
	 * A flag indicating if the lookup was successful.
	 */
	@JsonProperty("found")
	boolean found;

	/**
	 * Breach details given by the provider.
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("data")
	Map<String, Object> data;

	/**
	 * The parameters, which were passed in the request, echoed back.
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("parameters")
	Map<String, Object> parameters;

	public boolean getFound() {
		return found;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}
}
