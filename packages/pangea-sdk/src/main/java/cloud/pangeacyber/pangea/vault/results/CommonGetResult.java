package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cloud.pangeacyber.pangea.vault.models.Metadata;
import cloud.pangeacyber.pangea.vault.models.Tags;

public class CommonGetResult {
    @JsonProperty("id")
    String id;

    @JsonProperty("type")
    String type;

    @JsonProperty("version")
    Boolean version;

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
    @JsonProperty("auto_rotate")
    Boolean autoRotate = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("rotation_policy")
    String rotationPolicy = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("last_rotated")
    String lastRotated = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("next_rotation")
    String nextRotation = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("retain_previous_version")
    Boolean retainPreviousVersion = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("expiration")
    String expiration = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("created_at")
    String createdAt = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("revoked_at")
    String revokedAt = null;

    public CommonGetResult() {
    }

    public CommonGetResult(String id, String type, Boolean version, String name, String folder, Metadata metadata,
            Tags tags, Boolean autoRotate, String rotationPolicy, String lastRotated, String nextRotation,
            Boolean retainPreviousVersion, String expiration, String createdAt, String revokedAt) {
        this.id = id;
        this.type = type;
        this.version = version;
        this.name = name;
        this.folder = folder;
        this.metadata = metadata;
        this.tags = tags;
        this.autoRotate = autoRotate;
        this.rotationPolicy = rotationPolicy;
        this.lastRotated = lastRotated;
        this.nextRotation = nextRotation;
        this.retainPreviousVersion = retainPreviousVersion;
        this.expiration = expiration;
        this.createdAt = createdAt;
        this.revokedAt = revokedAt;
    }

    public CommonGetResult(String id, String type, Boolean version) {
        this.id = id;
        this.type = type;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Boolean getVersion() {
        return version;
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

    public Boolean getAutoRotate() {
        return autoRotate;
    }

    public String getRotationPolicy() {
        return rotationPolicy;
    }

    public String getLastRotated() {
        return lastRotated;
    }

    public String getNextRotation() {
        return nextRotation;
    }

    public Boolean getRetainPreviousVersion() {
        return retainPreviousVersion;
    }

    public String getExpiration() {
        return expiration;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getRevokedAt() {
        return revokedAt;
    }
}
