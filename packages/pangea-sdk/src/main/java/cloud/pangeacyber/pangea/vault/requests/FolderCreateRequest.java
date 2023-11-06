package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.vault.models.Metadata;
import cloud.pangeacyber.pangea.vault.models.Tags;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FolderCreateRequest extends BaseRequest {

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

	protected FolderCreateRequest(Builder builder) {
		this.name = builder.name;
		this.folder = builder.folder;
		this.metadata = builder.metadata;
		this.tags = builder.tags;
		this.rotationFrequency = builder.rotationFrequency;
		this.rotationGracePeriod = builder.rotationGracePeriod;
		this.rotationState = builder.rotationState;
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

	public static class Builder {

		String name = null;
		String folder = null;
		Metadata metadata = null;
		Tags tags = null;
		String rotationFrequency = null;
		String rotationState = null;
		String rotationGracePeriod = null;

		public Builder(String name, String folder) {
			this.name = name;
			this.folder = folder;
		}

		public FolderCreateRequest build() {
			return new FolderCreateRequest(this);
		}

		public Builder metadata(Metadata metadata) {
			this.metadata = metadata;
			return this;
		}

		public Builder tags(Tags tags) {
			this.tags = tags;
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

		public Builder rotationGracePeriod(String rotationGracePeriod) {
			this.rotationGracePeriod = rotationGracePeriod;
			return this;
		}

	}
}
