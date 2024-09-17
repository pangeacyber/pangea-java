package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.vault.models.ItemState;
import cloud.pangeacyber.pangea.vault.models.Metadata;
import cloud.pangeacyber.pangea.vault.models.Tags;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode(callSuper = true)
@Jacksonized
@SuperBuilder
@Value
public class UpdateRequest extends BaseRequest {

	@NonNull
	@JsonProperty("id")
	String id;

	@Builder.Default
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("name")
	String name = null;

	@Builder.Default
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("folder")
	String folder = null;

	@Builder.Default
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("metadata")
	Metadata metadata = null;

	@Builder.Default
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("tags")
	Tags tags = null;

	@Builder.Default
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_frequency")
	String rotationFrequency = null;

	@Builder.Default
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_state")
	String rotationState = null;

	@Builder.Default
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_grace_period")
	String rotationGracePeriod = null;

	@Builder.Default
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("expiration")
	String expiration = null;

	/** The new state of the item. */
	@Builder.Default
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("item_state")
	ItemState itemState = null;
}
