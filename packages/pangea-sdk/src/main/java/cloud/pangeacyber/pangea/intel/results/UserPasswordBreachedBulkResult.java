package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.UserPasswordBreachedBulkData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class UserPasswordBreachedBulkResult extends IntelCommonResult {

	@JsonProperty("data")
	UserPasswordBreachedBulkData data;

	public UserPasswordBreachedBulkData getData() {
		return data;
	}
}
