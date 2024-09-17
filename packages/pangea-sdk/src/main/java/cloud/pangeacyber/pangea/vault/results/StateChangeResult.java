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

	@JsonProperty("created_at")
	String createdAt;

	@JsonProperty("enabled")
	boolean enabled;

	@JsonProperty("folder")
	String folder;

	@JsonProperty("id")
	String id;

	@JsonProperty("item_versions")
	List<ItemVersionData> itemVersions;

	@JsonProperty("last_rotated")
	String lastRotated;

	@JsonProperty("name")
	String name;

	@JsonProperty("num_versions")
	int numVersions;

	@JsonProperty("type")
	ItemType type;
}
