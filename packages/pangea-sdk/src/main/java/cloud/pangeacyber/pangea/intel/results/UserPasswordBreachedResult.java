package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.UserPasswordBreachedData;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class UserPasswordBreachedResult extends IntelCommonResult {

	@JsonProperty("data")
	UserPasswordBreachedData data;

	public UserPasswordBreachedData getData() {
		return data;
	}
}
