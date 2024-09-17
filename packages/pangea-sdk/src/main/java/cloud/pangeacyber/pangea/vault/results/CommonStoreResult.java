package cloud.pangeacyber.pangea.vault.results;

import cloud.pangeacyber.pangea.vault.models.ItemVersionData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class CommonStoreResult {

	/** The type of the secret. */
	@JsonProperty("type")
	String type;

	/** The ID of the item. */
	@JsonProperty("id")
	String id;

	@JsonProperty("item_versions")
	List<ItemVersionData> itemVersions;
}
