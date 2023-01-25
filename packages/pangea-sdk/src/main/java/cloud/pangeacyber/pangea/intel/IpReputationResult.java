package cloud.pangeacyber.pangea.intel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public final class IpReputationResult {

	@JsonProperty("data")
	IntelReputationData Data;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("parameters")
	Map<String, Object> Parameters;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("raw_data")
	Map<String, Object> RawData;

	public IntelReputationData getData() {
		return Data;
	}

	public Map<String, Object> getParameters() {
		return Parameters;
	}

	public Map<String, Object> getRawData() {
		return RawData;
	}
}
