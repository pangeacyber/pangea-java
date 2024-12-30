package cloud.pangeacyber.pangea.share.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class ItemData {

	/** The ID of a stored object. */
	@JsonProperty("id")
	String id;

	/** The type of the item (file or dir). Cannot be written to. */
	@JsonProperty("type")
	String type;

	/** The name of the object. */
	@JsonProperty("name")
	String name;

	/** The date and time the object was created. */
	@JsonProperty("created_at")
	String createdAt;

	/** The full path to the folder the object is stored in. */
	@JsonProperty("folder")
	String folder;

	/** The date and time the object was last updated. */
	@JsonProperty("updated_at")
	String updatedAt;

	/** The size of the object in bytes. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("size")
	Integer size;

	/** The number of billable bytes (includes Metadata, Tags, etc.) for the object. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("billable_size")
	Integer billableSize;

	/** A list of user-defined tags */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("tags")
	Tags tags;

	/** Protected (read-only) flags. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("tags_protected")
	Tags tagsProtected;

	/** A set of string-based key/value pairs used to provide additional data about an object. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("metadata")
	Metadata metadata;

	/** Protected (read-only) metadata. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("metadata_protected")
	Metadata metadataProtected;

	/** The MD5 hash of the file contents. Cannot be written to. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("md5")
	String md5;

	/** The SHA256 hash of the file contents. Cannot be written to. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("sha256")
	String sha256;

	/** The SHA512 hash of the file contents. Cannot be written to. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("sha512")
	String sha512;

	/** The parent ID (a folder). Blanks means the root folder. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("parent_id")
	String parentId;

	/** The key in the external bucket that contains this file. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("external_bucket_key")
	String externalBucketKey;

	/** The explicit file TTL setting for this object. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("file_ttl")
	String fileTtl;

	/**
	 * The effective file TTL setting for this object, either explicitly set or
	 * inherited (see file_ttl_from_id.)
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("file_ttl_effective")
	String fileTtlEffective;

	/**
	 * The ID of the object the expiry / TTL is set from. Either a service
	 * configuration, the object itself, or a parent folder.
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("file_ttl_from_id")
	String fileTtlFromId;

	/** The ID of a stored object. */
	public String getID() {
		return id;
	}

	/** The MD5 hash of the file contents. Cannot be written to. */
	public String getMD5() {
		return md5;
	}

	/** The SHA256 hash of the file contents. Cannot be written to. */
	public String getSHA256() {
		return sha256;
	}

	/** The SHA512 hash of the file contents. Cannot be written to. */
	public String getSHA512() {
		return sha512;
	}

	/** The parent ID (a folder). Blanks means the root folder. */
	public String getParentID() {
		return parentId;
	}
}
