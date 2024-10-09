package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemData {

	@JsonProperty("type")
	String type;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id")
	String id;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("current_version")
	Integer currentVersion = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("name")
	String name = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("folder")
	String folder = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("metadata")
	Metadata metadata = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("tags")
	Tags tags = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_frequency")
	String rotationFrequency = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_state")
	String rotationState = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("last_rotated")
	String lastRotated = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("next_rotation")
	String nextRotation = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("expiration")
	String expiration = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("created_at")
	String createdAt = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("algorithm")
	String algorithm = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("purpose")
	String purpose = null;

	/** Whether the key is exportable or not. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("exportable")
	Boolean exportable = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("item_versions")
	List<ItemVersionData> itemVersions = null;
}
