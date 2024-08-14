package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.share.models.Metadata;
import cloud.pangeacyber.pangea.share.models.Tags;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateRequest extends BaseRequest {

	/** An identifier for the file to update. */
	@JsonProperty("id")
	String id;

	/** An alternative to ID for identifying the target file. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("path")
	String path;

	/** A list of Metadata key/values to set in the object. If a provided key exists, the value will be replaced. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("add_metadata")
	Metadata addMetadata;

	/** Protect the file with the supplied password. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("add_password")
	String addPassword;

	/** The algorithm to use to password protect the file. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("add_password_algorithm")
	String addPasswordAlgorithm;

	/** Set the object's metadata. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("metadata")
	Metadata metadata;

	/** A list of Tags to add. It is not an error to provide a tag which already exists. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("add_tags")
	Tags addTags;

	/** Sets the object's Name. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("name")
	String name;

	/** A list of metadata key/values to remove in the object. It is not an error for a provided key to not exist. If a provided key exists but doesn't match the provided value, it will not be removed. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("remove_metadata")
	Metadata removeMetadata;

	/** Remove the supplied password from the file. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("remove_password")
	String removePassword;

	/** A list of tags to remove. It is not an error to provide a tag which is not present. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("remove_tags")
	Tags removeTags;

	/** Set the object's tags. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("tags")
	Tags tags;

	/** Set the parent (folder) of the object. Can be an empty string for the root folder. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("parent_id")
	String parentId;

	/** The date and time the object was last updated. If included, the update will fail if this doesn't match the date and time of the last update for the object. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("updated_at")
	String updatedAt;

	/** The bucket to use, if not the default. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("bucket_id")
	String bucketId;

	protected UpdateRequest(Builder builder) {
		this.id = builder.id;
		this.path = builder.path;
		this.addMetadata = builder.addMetadata;
		this.addPassword = builder.addPassword;
		this.addPasswordAlgorithm = builder.addPasswordAlgorithm;
		this.metadata = builder.metadata;
		this.addTags = builder.addTags;
		this.name = builder.name;
		this.removeMetadata = builder.removeMetadata;
		this.removePassword = builder.removePassword;
		this.removeTags = builder.removeTags;
		this.tags = builder.tags;
		this.parentId = builder.parentId;
		this.updatedAt = builder.updatedAt;
		this.bucketId = builder.bucketId;
	}

	public static class Builder {

		String id;
		String path;
		Metadata addMetadata;
		String addPassword;
		String addPasswordAlgorithm;
		Metadata metadata;
		Tags addTags;
		String name;
		Metadata removeMetadata;
		String removePassword;
		Tags removeTags;
		Tags tags;
		String parentId;
		String updatedAt;
		String bucketId;

		public Builder(String id) {
			this.id = id;
		}

		public UpdateRequest build() {
			return new UpdateRequest(this);
		}

		/** An alternative to ID for identifying the target file. */
		public Builder path(String path) {
			this.path = path;
			return this;
		}

		/** A list of Metadata key/values to set in the object. If a provided key exists, the value will be replaced. */
		public Builder addMetadata(Metadata addMetadata) {
			this.addMetadata = addMetadata;
			return this;
		}

		/** Protect the file with the supplied password. */
		public Builder addPassword(String addPassword) {
			this.addPassword = addPassword;
			return this;
		}

		/** The algorithm to use to password protect the file. */
		public Builder addPasswordAlgorithm(String addPasswordAlgorithm) {
			this.addPasswordAlgorithm = addPasswordAlgorithm;
			return this;
		}

		/** A list of metadata key/values to remove in the object. It is not an error for a provided key to not exist. If a provided key exists but doesn't match the provided value, it will not be removed. */
		public Builder removeMetadata(Metadata removeMetadata) {
			this.removeMetadata = removeMetadata;
			return this;
		}

		/** Set the object's metadata. */
		public Builder metadata(Metadata metadata) {
			this.metadata = metadata;
			return this;
		}

		/** A list of Tags to add. It is not an error to provide a tag which already exists. */
		public Builder addTags(Tags addTags) {
			this.addTags = addTags;
			return this;
		}

		/** Sets the object's Name. */
		public Builder name(String name) {
			this.name = name;
			return this;
		}

		/** Remove the supplied password from the file. */
		public Builder removePassword(String removePassword) {
			this.removePassword = removePassword;
			return this;
		}

		/** A list of tags to remove. It is not an error to provide a tag which is not present. */
		public Builder removeTags(Tags removeTags) {
			this.removeTags = removeTags;
			return this;
		}

		/** Set the object's tags. */
		public Builder tags(Tags tags) {
			this.tags = tags;
			return this;
		}

		/** Set the parent (folder) of the object. Can be an empty string for the root folder. */
		public Builder parentId(String parentId) {
			this.parentId = parentId;
			return this;
		}

		/** The date and time the object was last updated. If included, the update will fail if this doesn't match the date and time of the last update for the object. */
		public Builder updatedAt(String updatedAt) {
			this.updatedAt = updatedAt;
			return this;
		}

		/** The bucket to use, if not the default. */
		public Builder bucketId(String bucketId) {
			this.bucketId = bucketId;
			return this;
		}
	}
}
