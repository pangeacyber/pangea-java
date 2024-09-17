package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.vault.models.Metadata;
import cloud.pangeacyber.pangea.vault.models.Tags;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@SuperBuilder
public class CommonGenerateRequest extends BaseRequest {

	/** The name of this item. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("name")
	String name;

	/** The folder where this item is stored. */
	@JsonInclude(Include.NON_NULL)
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

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_state")
	String rotationState;

	/** State to which the previous version should transition upon rotation. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("expiration")
	String expiration;

	/** Whether the key is exportable or not. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("exportable")
	boolean exportable;
}
