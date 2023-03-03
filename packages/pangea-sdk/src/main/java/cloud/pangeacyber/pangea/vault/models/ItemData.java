package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemData {

	@JsonProperty("type")
	String type;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id")
	String id;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("current_version")
	ItemVersionData currentVersion;

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

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getFolder() {
		return folder;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public Tags getTags() {
		return tags;
	}

	public String getRotationFrequency() {
		return rotationFrequency;
	}

	public String getLastRotated() {
		return lastRotated;
	}

	public String getNextRotation() {
		return nextRotation;
	}

	public String getExpiration() {
		return expiration;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public ItemVersionData getCurrentVersion() {
		return currentVersion;
	}

	public String getRotationState() {
		return rotationState;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public String getPurpose() {
		return purpose;
	}
}
