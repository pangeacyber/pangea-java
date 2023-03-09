package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListItemData extends ItemData {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("compromised_versions")
	ItemVersionData[] compromisedVersions;

	public ListItemData() {
		super();
	}

	public ItemVersionData[] getCompromisedVersions() {
		return compromisedVersions;
	}
}
