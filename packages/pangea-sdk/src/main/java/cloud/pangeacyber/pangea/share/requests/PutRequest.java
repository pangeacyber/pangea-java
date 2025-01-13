package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.share.models.FileFormat;
import cloud.pangeacyber.pangea.share.models.Metadata;
import cloud.pangeacyber.pangea.share.models.Tags;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@EqualsAndHashCode(callSuper = true)
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
public class PutRequest extends BaseRequest {

	/** The name of the object to store. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("name")
	String name;

	/** The format of the file, which will be verified by the server if provided. Uploads not matching the supplied format will be rejected. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("format")
	FileFormat format;

	/** A set of string-based key/value pairs used to provide additional data about an object. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("metadata")
	Metadata metadata;

	/** The MIME type of the file, which will be verified by the server if provided. Uploads not matching the supplied MIME type will be rejected. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("mimetype")
	String mimetype;

	/** The parent ID of the object (a folder). Leave blank to keep in the root folder. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("parent_id")
	String parentID;

	/**
	 * The path to the parent folder. Leave blank for the root folder. Path must
	 * resolve to `parent_id` if also set.
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("folder")
	String folder;

	/** The TTL before expiry for the file. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("file_ttl")
	String fileTtl;

	/** An optional password to protect the file with. Downloading the file will require this password. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("password")
	String password;

	/** An optional password algorithm to protect the file with. See symmetric vault password_algorithm. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("password_algorithm")
	String passwordAlgorithm;

	/** The hexadecimal-encoded CRC32C hash of the file data, which will be verified by the server if provided. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("crc32c")
	String crc32c;

	/** The hexadecimal-encoded MD5 hash of the file data, which will be verified by the server if provided. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("md5")
	String md5;

	/** The hexadecimal-encoded SHA1 hash of the file data, which will be verified by the server if provided. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("sha1")
	String sha1;

	/** The SHA256 hash of the file data, which will be verified by the server if provided. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("sha256")
	String sha256;

	/** The hexadecimal-encoded SHA512 hash of the file data, which will be verified by the server if provided. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("sha512")
	String sha512;

	/** The size (in bytes) of the file. If the upload doesn't match, the call will fail. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("size")
	Integer size;

	/** The URL to fetch the file payload from (for transfer_method source-url). */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("source_url")
	String sourceURL;

	/** A list of user-defined tags */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("tags")
	Tags tags;

	/** The bucket to use, if not the default. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("bucket_id")
	String bucketId;

	// Legacy alias.
	public static class Builder extends PutRequestBuilder<PutRequest, PutRequest.Builder> {

		@Override
		protected Builder self() {
			return this;
		}

		@Override
		public PutRequest build() {
			return new PutRequest(this);
		}
	}

	/**
	 * The hexadecimal-encoded CRC32C hash of the file data, which will be
	 * verified by the server if provided.
	 */
	// Weird bug if making that trailing c uppercase, it print duplicated crc32C and crc32c in json
	public String getCRC32c() {
		return crc32c;
	}

	/**
	 * The hexadecimal-encoded CRC32C hash of the file data, which will be
	 * verified by the server if provided.
	 */
	public void setCRC32c(String crc32c) {
		this.crc32c = crc32c;
	}

	/**
	 * The SHA256 hash of the file data, which will be verified by the server if
	 * provided.
	 */
	public String getSHA256() {
		return sha256;
	}

	/**
	 * The SHA256 hash of the file data, which will be verified by the server if
	 * provided.
	 */
	public void setSHA256(String sha256) {
		this.sha256 = sha256;
	}

	/**
	 * The hexadecimal-encoded MD5 hash of the file data, which will be verified
	 * by the server if provided.
	 */
	public String getMD5() {
		return md5;
	}

	/**
	 * The hexadecimal-encoded SHA1 hash of the file data, which will be
	 * verified by the server if provided.
	 */
	public String getSHA1() {
		return sha1;
	}

	/**
	 * The hexadecimal-encoded SHA512 hash of the file data, which will be
	 * verified by the server if provided.
	 */
	public String getSHA512() {
		return sha512;
	}
}
