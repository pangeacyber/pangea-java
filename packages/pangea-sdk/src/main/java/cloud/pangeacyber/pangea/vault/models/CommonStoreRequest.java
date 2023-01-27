package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class CommonStoreRequest {
    @JsonProperty("type")
    ItemType type;

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

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("managed")
    Boolean managed = null;

    public CommonStoreRequest() {
    }

    public CommonStoreRequest(ItemType type, Boolean managed, String name, String folder, Metadata metadata, Tags tags, Boolean autoRotate,
            String rotationPolicy, Boolean retainPreviousVersion, String expiration) {
        this.type = type;
        this.managed = managed;
        this.name = name;
        this.folder = folder;
        this.metadata = metadata;
        this.tags = tags;
        this.autoRotate = autoRotate;
        this.rotationPolicy = rotationPolicy;
        this.retainPreviousVersion = retainPreviousVersion;
        this.expiration = expiration;
    }

    public CommonStoreRequest(ItemType type) {
        this.type = type;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
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
