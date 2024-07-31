package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.share.models.Metadata;
import cloud.pangeacyber.pangea.share.models.Tags;
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

	/** The bucket to use, if not the default. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("bucket_id")
	String bucketId;

	protected FolderCreateRequest(Builder builder) {
		this.name = builder.name;
		this.metadata = builder.metadata;
		this.parentId = builder.parentId;
		this.path = builder.path;
		this.tags = builder.tags;
		this.bucketId = builder.bucketId;
	}

	public static class Builder {

		String name;
		Metadata metadata;
		String parentId;
		String path;
		Tags tags;
		String bucketId;

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

		/** The bucket to use, if not the default. */
		public Builder bucketId(String bucketId) {
			this.bucketId = bucketId;
			return this;
		}
	}
}
