package cloud.pangeacyber.pangea.intel.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IntelReputationDataItem extends IntelReputationData {

	@JsonProperty("indicator")
	String indicator;

	public String getIndicator() {
		return indicator;
	}
}
