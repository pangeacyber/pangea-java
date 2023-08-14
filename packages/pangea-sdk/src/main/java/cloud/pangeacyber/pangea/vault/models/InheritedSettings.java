package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InheritedSettings {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_frequency")
	boolean rotationFrequency;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_state")
	boolean rotationState;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_grace_period")
	boolean rotationGracePeriod;

	public boolean isRotationFrequency() {
		return rotationFrequency;
	}

	public boolean isRotationState() {
		return rotationState;
	}

	public boolean isRotationGracePeriod() {
		return rotationGracePeriod;
	}
}
