package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.share.models.Metadata;
import cloud.pangeacyber.pangea.share.models.Tags;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FolderCreateRequest extends BaseRequest {

	/** The name of an object. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("name")
	String name;

	/** A set of string-based key/value pairs used to provide additional data about an object. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("metadata")
	Metadata metadata;

	/** The ID of a stored object. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("parent_id")
	String parentId;

	/** The folder to place the folder in. Must match `parent_id` if also set. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("folder")
	String folder;

	/** A list of user-defined tags */
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
		this.folder = builder.folder;
		this.tags = builder.tags;
		this.bucketId = builder.bucketId;
	}

	public static class Builder {

		String name;
		Metadata metadata;
		String parentId;
		String folder;
		Tags tags;
		String bucketId;

		public Builder() {
			// Empty constructor
		}

		public FolderCreateRequest build() {
			return new FolderCreateRequest(this);
		}

		/** The name of an object. */
		public Builder name(String name) {
			this.name = name;
			return this;
		}

		/** A set of string-based key/value pairs used to provide additional data about an object. */
		public Builder metadata(Metadata metadata) {
			this.metadata = metadata;
			return this;
		}

		/** The ID of a stored object. */
		public Builder parentId(String parentId) {
			this.parentId = parentId;
			return this;
		}

		/** The folder to place the folder in. Must match `parent_id` if also set. */
		public Builder folder(String folder) {
			this.folder = folder;
			return this;
		}

		/** A list of user-defined tags */
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
