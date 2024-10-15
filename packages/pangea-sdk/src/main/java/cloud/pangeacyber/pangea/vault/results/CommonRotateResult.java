package cloud.pangeacyber.pangea.vault.results;

import cloud.pangeacyber.pangea.vault.models.ItemType;
import cloud.pangeacyber.pangea.vault.models.ItemVersionData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonRotateResult {

	/** Type of the Vault item. */
	@JsonProperty("type")
	ItemType type;

	/** ID of the key. */
	@JsonProperty("id")
	String id = null;

	@JsonProperty("item_versions")
	List<ItemVersionData> itemVersions;
}
