package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.vault.models.Metadata;
import cloud.pangeacyber.pangea.vault.models.Tags;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode(callSuper = true)
@Jacksonized
@SuperBuilder
@Value
public class FolderCreateRequest extends BaseRequest {

	/** The name of this folder. */
	@NonNull
	@JsonProperty("name")
	String name;

	/** The parent folder where this folder is stored. */
	@NonNull
	@JsonProperty("folder")
	String folder;

	/** User-provided metadata. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("metadata")
	Metadata metadata;

	/** A list of user-defined tags. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("tags")
	Tags tags;

	/** Period of time between item rotations. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_frequency")
	String rotationFrequency;

	/** State to which the previous version should transition upon rotation. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_state")
	String rotationState;

	/** Grace period for the previous version. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_grace_period")
	String rotationGracePeriod;

	/** Timestamp indicating when the item will be disabled. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("disabled_at")
	String disabledAt;
}
