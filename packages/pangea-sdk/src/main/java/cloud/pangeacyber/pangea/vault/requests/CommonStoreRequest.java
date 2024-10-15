package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.vault.models.Metadata;
import cloud.pangeacyber.pangea.vault.models.Tags;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@Jacksonized
public class CommonStoreRequest extends BaseRequest {

	/** The name of this item. */
	@Builder.Default
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("name")
	String name = null;

	/** The folder where this item is stored. */
	@Builder.Default
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("folder")
	String folder = null;

	/** User-provided metadata. */
	@Builder.Default
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("metadata")
	Metadata metadata = null;

	/** A list of user-defined tags. */
	@Builder.Default
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("tags")
	Tags tags = null;

	/** Period of time between item rotations. */
	@Builder.Default
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_frequency")
	String rotationFrequency = null;

	/** State to which the previous version should transition upon rotation. */
	@Builder.Default
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_state")
	String rotationState = null;

	/** Timestamp indicating when the item will be disabled. */
	@Builder.Default
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("disabled_at")
	String disabledAt = null;
}
