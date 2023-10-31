package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.UserBreachedBulkData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class UserBreachedBulkResult extends IntelCommonResult {

	@JsonProperty("data")
	UserBreachedBulkData data;

	public UserBreachedBulkData getData() {
		return data;
	}
}
