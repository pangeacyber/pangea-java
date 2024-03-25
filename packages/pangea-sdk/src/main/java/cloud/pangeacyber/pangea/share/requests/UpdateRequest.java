package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.share.models.Metadata;
import cloud.pangeacyber.pangea.share.models.Tags;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateRequest extends BaseRequest {

	@JsonProperty("id")
	String id;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("path")
	String path;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("add_metadata")
	Metadata addMetadata;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("remove_metadata")
	Metadata removeMetadata;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("metadata")
	Metadata metadata;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("add_tags")
	Tags addTags;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("remove_tags")
	Tags removeTags;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("tags")
	Tags tags;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("parent_id")
	String parentId;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("updated_at")
	String updatedAt;

	protected UpdateRequest(Builder builder) {
		this.id = builder.id;
		this.path = builder.path;
		this.addMetadata = builder.addMetadata;
		this.removeMetadata = builder.removeMetadata;
		this.metadata = builder.metadata;
		this.addTags = builder.addTags;
		this.removeTags = builder.removeTags;
		this.tags = builder.tags;
		this.parentId = builder.parentId;
		this.updatedAt = builder.updatedAt;
	}

	public static class Builder {

		String id;
		String path;
		Metadata addMetadata;
		Metadata removeMetadata;
		Metadata metadata;
		Tags addTags;
		Tags removeTags;
		Tags tags;
		String parentId;
		String updatedAt;

		public Builder(String id) {
			this.id = id;
		}

		public UpdateRequest build() {
			return new UpdateRequest(this);
		}

		public Builder path(String path) {
			this.path = path;
			return this;
		}

		public Builder addMetadata(Metadata addMetadata) {
			this.addMetadata = addMetadata;
			return this;
		}

		public Builder removeMetadata(Metadata removeMetadata) {
			this.removeMetadata = removeMetadata;
			return this;
		}

		public Builder metadata(Metadata metadata) {
			this.metadata = metadata;
			return this;
		}

		public Builder addTags(Tags addTags) {
			this.addTags = addTags;
			return this;
		}

		public Builder removeTags(Tags removeTags) {
			this.removeTags = removeTags;
			return this;
		}

		public Builder tags(Tags tags) {
			this.tags = tags;
			return this;
		}

		public Builder parentId(String parentId) {
			this.parentId = parentId;
			return this;
		}

		public Builder updatedAt(String updatedAt) {
			this.updatedAt = updatedAt;
			return this;
		}
	}
}
