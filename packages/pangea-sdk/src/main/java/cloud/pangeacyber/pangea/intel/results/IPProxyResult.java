package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.IPProxyData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IPProxyResult {

	@JsonProperty("data")
	IPProxyData Data;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("parameters")
	Map<String, Object> Parameters;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("raw_data")
	Map<String, Object> RawData;

	public IPProxyData getData() {
		return Data;
	}

	public Map<String, Object> getParameters() {
		return Parameters;
	}

	public Map<String, Object> getRawData() {
		return RawData;
	}
}
