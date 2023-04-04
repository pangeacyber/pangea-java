package cloud.pangeacyber.pangea.intel.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class UserPasswordBreachedResult extends IntelCommonResult {

	@JsonProperty("data")
	UserPasswordBreachedData data;

	public UserPasswordBreachedData getData() {
		return data;
	}
}
