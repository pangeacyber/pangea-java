package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.share.models.Metadata;
import cloud.pangeacyber.pangea.share.models.Tags;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode(callSuper = true)
@Jacksonized
@SuperBuilder
@Value
public class UpdateRequest extends BaseRequest {

	/** An identifier for the file to update. */
	@NonNull
	@JsonProperty("id")
	String id;

	/**
	 * Set the parent (folder). Leave blank for the root folder. Path must
	 * resolve to `parent_id` if also set.
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("folder")
	String folder;

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

	/** Set the file TTL. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("file_ttl")
	String fileTtl;

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

	// Legacy alias.
	public static class Builder extends UpdateRequestBuilder<UpdateRequest, UpdateRequest.Builder> {

		public Builder(String id) {
			this.id(id);
		}

		@Override
		protected Builder self() {
			return this;
		}

		@Override
		public UpdateRequest build() {
			return new UpdateRequest(this);
		}
	}
}
