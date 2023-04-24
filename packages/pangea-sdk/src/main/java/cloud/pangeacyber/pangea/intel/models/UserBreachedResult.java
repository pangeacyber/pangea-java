package cloud.pangeacyber.pangea.intel.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class UserBreachedResult extends IntelCommonResult {

	@JsonProperty("data")
	UserBreachedData data;

	public UserBreachedData getData() {
		return data;
	}
}
