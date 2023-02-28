package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListItemData {
    @JsonProperty("id")
    String id;

    @JsonProperty("type")
    String type;

    @JsonProperty("current_version")
    ItemVersionData currentVersion;

    @JsonProperty("compromised_versions")
    ItemVersionData[] compromisedVersions;

    @JsonProperty("identity")
    String identity;

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

    public String getIdentity() {
        return identity;
    }

    public ItemVersionData getCurrentVersion() {
        return currentVersion;
    }

    public ItemVersionData[] getVersions() {
        return compromisedVersions;
    }

}
