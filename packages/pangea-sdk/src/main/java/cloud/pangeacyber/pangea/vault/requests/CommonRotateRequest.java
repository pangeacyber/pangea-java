package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.vault.models.ItemVersionState;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@JsonInclude(Include.NON_NULL)
@SuperBuilder
public class CommonRotateRequest extends BaseRequest {

	/** The item ID. */
	@NonNull
	@JsonProperty("id")
	String id;

	/** State to which the previous version should transition upon rotation. */
	@JsonProperty("rotation_state")
	ItemVersionState rotationState;

	@JsonProperty("rotation_frequency")
	String rotationFrequency;
}
