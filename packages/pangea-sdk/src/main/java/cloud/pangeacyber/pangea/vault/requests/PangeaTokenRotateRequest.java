package cloud.pangeacyber.pangea.vault.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode(callSuper = true)
@Jacksonized
@SuperBuilder
@Value
public class PangeaTokenRotateRequest extends CommonRotateRequest {

	@JsonProperty("rotation_grace_period")
	String rotationGracePeriod;
}
