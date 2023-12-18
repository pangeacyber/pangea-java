package cloud.pangeacyber.pangea.intel.results;

import cloud.pangeacyber.pangea.intel.models.UserBreachedData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class UserBreachedResult extends IntelCommonResult {

	@JsonProperty("data")
	UserBreachedData data;

	public UserBreachedData getData() {
		return data;
	}
}
