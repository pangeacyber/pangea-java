package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.vault.models.Metadata;
import cloud.pangeacyber.pangea.vault.models.Tags;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateRequest extends BaseRequest {

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
	@JsonProperty("rotation_grace_period")
	String rotationGracePeriod = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("expiration")
	String expiration = null;

	public UpdateRequest(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.folder = builder.folder;
		this.metadata = builder.metadata;
		this.tags = builder.tags;
		this.rotationFrequency = builder.rotationFrequency;
		this.rotationState = builder.rotationState;
		this.expiration = builder.expiration;
		this.rotationGracePeriod = builder.rotationGracePeriod;
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

	public static class Builder {

		String id;
		String name = null;
		String folder = null;
		Metadata metadata = null;
		Tags tags = null;
		Boolean autoRotate = null;
		String rotationFrequency = null;
		String rotationState = null;
		String expiration = null;
		String rotationGracePeriod = null;

		public Builder(String id) {
			this.id = id;
		}

		public UpdateRequest build() {
			return new UpdateRequest(this);
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder folder(String folder) {
			this.folder = folder;
			return this;
		}

		public Builder metadata(Metadata metadata) {
			this.metadata = metadata;
			return this;
		}

		public Builder tags(Tags tags) {
			this.tags = tags;
			return this;
		}

		public Builder autoRotate(Boolean autoRotate) {
			this.autoRotate = autoRotate;
			return this;
		}

		public Builder rotationFrequency(String rotationFrequency) {
			this.rotationFrequency = rotationFrequency;
			return this;
		}

		public Builder rotationState(String rotationState) {
			this.rotationState = rotationState;
			return this;
		}

		public Builder expiration(String expiration) {
			this.expiration = expiration;
			return this;
		}

		public Builder rotationGracePeriod(String rotationGracePeriod) {
			this.rotationGracePeriod = rotationGracePeriod;
			return this;
		}
	}
}
