package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cloud.pangeacyber.pangea.vault.models.ItemVersionData;
import cloud.pangeacyber.pangea.vault.models.Metadata;
import cloud.pangeacyber.pangea.vault.models.Tags;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonGetResult {
    @JsonProperty("id")
    String id;

    @JsonProperty("type")
    String type;

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
    @JsonProperty("destroyed_at")
    String destroyedAt = null;

    @JsonProperty("versions")
    ItemVersionData[] versions;

    public ItemVersionData[] getVersions() {
        return versions;
    }

    public CommonGetResult() {
    }

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

    public String getRotationState() {
        return rotationState;
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

    public String getDestroyedAt() {
        return destroyedAt;
    }
}
