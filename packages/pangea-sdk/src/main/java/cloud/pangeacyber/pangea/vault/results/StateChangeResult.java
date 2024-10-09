package cloud.pangeacyber.pangea.vault.results;

import cloud.pangeacyber.pangea.vault.models.ItemType;
import cloud.pangeacyber.pangea.vault.models.ItemVersionData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class StateChangeResult {

	/** Timestamp indicating when the item was created. */
	@JsonProperty("created_at")
	String createdAt;

	/** `true` if the item is enabled. */
	@JsonProperty("enabled")
	boolean enabled;

	/** Folder where the item is stored. */
	@JsonProperty("folder")
	String folder;

	/** ID of the key. */
	@JsonProperty("id")
	String id;

	@JsonProperty("item_versions")
	List<ItemVersionData> itemVersions;

	/** Timestamp of the last rotation. */
	@JsonProperty("last_rotated")
	String lastRotated;

	/** Name of the item. */
	@JsonProperty("name")
	String name;

	/** Total number of versions of the item. */
	@JsonProperty("num_versions")
	int numVersions;

	/** Type of the Vault item. */
	@JsonProperty("type")
	ItemType type;
}
