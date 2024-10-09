package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Value
public class InheritedSettings {

	@JsonProperty("rotation_frequency")
	String rotationFrequency;

	@JsonProperty("rotation_state")
	String rotationState;

	@JsonProperty("rotation_grace_period")
	String rotationGracePeriod;
}
