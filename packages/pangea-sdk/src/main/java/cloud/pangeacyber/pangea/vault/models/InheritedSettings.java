package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InheritedSettings {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_frequency")
	String rotationFrequency;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_state")
	String rotationState;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_grace_period")
	String rotationGracePeriod;

	public String getRotationFrequency() {
		return rotationFrequency;
	}

	public String getRotationState() {
		return rotationState;
	}

	public String getRotationGracePeriod() {
		return rotationGracePeriod;
	}
}
