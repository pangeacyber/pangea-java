package cloud.pangeacyber.pangea.intel.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBreachedData {

	@JsonProperty("found_in_breach")
	boolean foundInBreach;

	@JsonProperty("breach_count")
	int breachCount;

	public boolean getFoundInBreach() {
		return foundInBreach;
	}

	public int getBreachCount() {
		return breachCount;
	}
}
