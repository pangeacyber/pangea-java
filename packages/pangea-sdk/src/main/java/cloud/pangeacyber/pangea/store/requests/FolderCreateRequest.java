package cloud.pangeacyber.pangea.store.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.store.models.Metadata;
import cloud.pangeacyber.pangea.store.models.Tags;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FolderCreateRequest extends BaseRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("name")
	String name;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("metadata")
	Metadata metadata;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("parent_id")
	String parentId;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("path")
	String path;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("tags")
	Tags tags;

	protected FolderCreateRequest(Builder builder) {
		this.name = builder.name;
		this.metadata = builder.metadata;
		this.parentId = builder.parentId;
		this.path = builder.path;
		this.tags = builder.tags;
	}

	public static class Builder {

		String name;
		Metadata metadata;
		String parentId;
		String path;
		Tags tags;

		public Builder() {
			// Empty constructor
		}

		public FolderCreateRequest build() {
			return new FolderCreateRequest(this);
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder metadata(Metadata metadata) {
			this.metadata = metadata;
			return this;
		}

		public Builder parentId(String parentId) {
			this.parentId = parentId;
			return this;
		}

		public Builder path(String path) {
			this.path = path;
			return this;
		}

		public Builder tags(Tags tags) {
			this.tags = tags;
			return this;
		}
	}
}
