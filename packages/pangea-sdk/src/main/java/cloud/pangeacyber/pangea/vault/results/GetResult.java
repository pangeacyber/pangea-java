package cloud.pangeacyber.pangea.vault.results;

import cloud.pangeacyber.pangea.vault.models.InheritedSettings;
import cloud.pangeacyber.pangea.vault.models.ItemData;
import cloud.pangeacyber.pangea.vault.models.ItemVersionData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetResult extends ItemData {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("versions")
	ItemVersionData[] versions;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_grace_period")
	String rotationGracePeriod = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("inherited_settings")
	InheritedSettings inheritedSettings;

	public GetResult() {
		super();
	}

	public ItemVersionData[] getVersions() {
		return versions;
	}

	public String getRotationGracePeriod() {
		return rotationGracePeriod;
	}

	public InheritedSettings getInheritedSettings() {
		return inheritedSettings;
	}
}
