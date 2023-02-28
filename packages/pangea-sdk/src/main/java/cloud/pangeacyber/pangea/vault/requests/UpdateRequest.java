package cloud.pangeacyber.pangea.vault.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cloud.pangeacyber.pangea.vault.models.Metadata;
import cloud.pangeacyber.pangea.vault.models.Tags;

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
    @JsonProperty("rotation_frequency")
    String rotationFrequency = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("rotation_state")
    String rotationState = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("expiration")
    String expiration = null;

    public UpdateRequest(UpdateRequestBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.folder = builder.folder;
        this.metadata = builder.metadata;
        this.tags = builder.tags;
        this.rotationFrequency = builder.rotationFrequency;
        this.rotationState = builder.rotationState;
        this.expiration = builder.expiration;
    }

    public String getId() {
        return id;
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

    public String getExpiration() {
        return expiration;
    }

    public static class UpdateRequestBuilder {
        String id;
        String name = null;
        String folder = null;
        Metadata metadata = null;
        Tags tags = null;
        Boolean autoRotate = null;
        String rotationFrequency = null;
        String rotationState = null;
        String expiration = null;

        public UpdateRequestBuilder(String id) {
            this.id = id;
        }

        public UpdateRequest build(){
            return new UpdateRequest(this);
        }

        public UpdateRequestBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UpdateRequestBuilder setFolder(String folder) {
            this.folder = folder;
            return this;
        }

        public UpdateRequestBuilder setMetadata(Metadata metadata) {
            this.metadata = metadata;
            return this;
        }

        public UpdateRequestBuilder setTags(Tags tags) {
            this.tags = tags;
            return this;
        }

        public UpdateRequestBuilder setAutoRotate(Boolean autoRotate) {
            this.autoRotate = autoRotate;
            return this;
        }

        public UpdateRequestBuilder setRotationFrequency(String rotationFrequency) {
            this.rotationFrequency = rotationFrequency;
            return this;
        }

        public UpdateRequestBuilder setRotationState(String rotationState) {
            this.rotationState = rotationState;
            return this;
        }

        public UpdateRequestBuilder setExpiration(String expiration) {
            this.expiration = expiration;
            return this;
        }

    }

}
