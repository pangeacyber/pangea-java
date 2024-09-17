package cloud.pangeacyber.pangea.vault.results;

import cloud.pangeacyber.pangea.vault.models.ItemData;
import cloud.pangeacyber.pangea.vault.models.ItemVersionData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
public class GetResult extends ItemData {

	/** The type of the key. */
	@JsonProperty("type")
	String type;

	@JsonProperty("item_versions")
	List<ItemVersionData> itemVersions;
}
