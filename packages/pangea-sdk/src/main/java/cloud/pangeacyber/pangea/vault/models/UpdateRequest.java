package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class UpdateRequest {
    @JsonProperty("id")
    String id;

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
    @JsonProperty("retain_previous_version")
    Boolean retainPreviousVersion = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("expiration")
    String expiration = null;

    public UpdateRequest(String id, String name, String folder, Metadata metadata, Tags tags, Boolean autoRotate,
            String rotationPolicy, Boolean retainPreviousVersion, String expiration) {
        this.id = id;
        this.name = name;
        this.folder = folder;
        this.metadata = metadata;
        this.tags = tags;
        this.autoRotate = autoRotate;
        this.rotationPolicy = rotationPolicy;
        this.retainPreviousVersion = retainPreviousVersion;
        this.expiration = expiration;
    }

    public UpdateRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String type) {
        this.id = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }

    public Boolean getAutoRotate() {
        return autoRotate;
    }

    public void setAutoRotate(Boolean autoRotate) {
        this.autoRotate = autoRotate;
    }

    public String getRotationPolicy() {
        return rotationPolicy;
    }

    public void setRotationPolicy(String rotationPolicy) {
        this.rotationPolicy = rotationPolicy;
    }

    public Boolean getRetainPreviousVersion() {
        return retainPreviousVersion;
    }

    public void setRetainPreviousVersion(Boolean retainPreviousVersion) {
        this.retainPreviousVersion = retainPreviousVersion;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

}
