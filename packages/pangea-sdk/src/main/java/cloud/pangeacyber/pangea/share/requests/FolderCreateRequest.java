package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.share.models.Metadata;
import cloud.pangeacyber.pangea.share.models.Tags;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@EqualsAndHashCode(callSuper = true)
@Jacksonized
@SuperBuilder
@Value
public class FolderCreateRequest extends BaseRequest {

	/** The name of an object. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("name")
	String name;

	/** Duration until files within this folder are automatically deleted. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("file_ttl")
	String fileTtl;

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

	// Legacy alias.
	public static class Builder extends FolderCreateRequestBuilder<FolderCreateRequest, FolderCreateRequest.Builder> {

		@Override
		protected Builder self() {
			return this;
		}

		@Override
		public FolderCreateRequest build() {
			return new FolderCreateRequest(this);
		}
	}
}
