package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListItemData extends ItemData {

	@JsonProperty("compromised_versions")
	ItemVersionData[] compromisedVersions;

	public ListItemData() {
		super();
	}

	public ItemVersionData[] getCompromisedVersions() {
		return compromisedVersions;
	}
}
