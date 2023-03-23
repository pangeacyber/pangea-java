package cloud.pangeacyber.pangea.intel.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class URLReputationResult extends IntelReputationResult {

	@JsonProperty("data")
	URLReputationData data;

	public URLReputationData getData() {
		return data;
	}
}
