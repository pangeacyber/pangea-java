package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.TransferMethod;
import cloud.pangeacyber.pangea.share.models.FileFormat;
import cloud.pangeacyber.pangea.share.models.Metadata;
import cloud.pangeacyber.pangea.share.models.Tags;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
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

	/** An optional path where the file should be placed. It will auto-create directories if necessary. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("path")
	String path;

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

	protected PutRequest(Builder builder) {
		this.name = builder.name;
		this.format = builder.format;
		this.metadata = builder.metadata;
		this.mimetype = builder.mimetype;
		this.parentID = builder.parentID;
		this.path = builder.path;
		this.password = builder.password;
		this.passwordAlgorithm = builder.passwordAlgorithm;
		this.crc32c = builder.crc32c;
		this.md5 = builder.md5;
		this.sha1 = builder.sha1;
		this.sha256 = builder.sha256;
		this.sha512 = builder.sha512;
		this.size = builder.size;
		this.tags = builder.tags;
		this.bucketId = builder.bucketId;
		this.setTransferMethod(builder.transferMethod);
	}

	public static class Builder {

		String name;
		FileFormat format;
		Metadata metadata;
		String mimetype;
		String parentID;
		String path;
		String password;
		String passwordAlgorithm;
		String crc32c;
		String md5;
		String sha1;
		String sha256;
		String sha512;
		String sourceURL;
		Integer size;
		Tags tags;
		String bucketId;
		TransferMethod transferMethod;

		public Builder() {}

		public PutRequest build() {
			return new PutRequest(this);
		}

		/** The name of the object to store. */
		public Builder name(String name) {
			this.name = name;
			return this;
		}

		/** The format of the file, which will be verified by the server if provided. Uploads not matching the supplied format will be rejected. */
		public Builder format(FileFormat format) {
			this.format = format;
			return this;
		}

		/** A set of string-based key/value pairs used to provide additional data about an object. */
		public Builder metadata(Metadata metadata) {
			this.metadata = metadata;
			return this;
		}

		/** The MIME type of the file, which will be verified by the server if provided. Uploads not matching the supplied MIME type will be rejected. */
		public Builder mimetype(String mimetype) {
			this.mimetype = mimetype;
			return this;
		}

		/** The parent ID of the object (a folder). Leave blank to keep in the root folder. */
		public Builder parentID(String parentID) {
			this.parentID = parentID;
			return this;
		}

		/** An optional path where the file should be placed. It will auto-create directories if necessary. */
		public Builder path(String path) {
			this.path = path;
			return this;
		}

		/** An optional password to protect the file with. Downloading the file will require this password. */
		public Builder password(String password) {
			this.password = password;
			return this;
		}

		/** An optional password algorithm to protect the file with. See symmetric vault password_algorithm. */
		public Builder passwordAlgorithm(String passwordAlgorithm) {
			this.passwordAlgorithm = passwordAlgorithm;
			return this;
		}

		/** The hexadecimal-encoded CRC32C hash of the file data, which will be verified by the server if provided. */
		public Builder crc32c(String crc32c) {
			this.crc32c = crc32c;
			return this;
		}

		/** The hexadecimal-encoded MD5 hash of the file data, which will be verified by the server if provided. */
		public Builder md5(String md5) {
			this.md5 = md5;
			return this;
		}

		/** The hexadecimal-encoded SHA1 hash of the file data, which will be verified by the server if provided. */
		public Builder sha1(String sha1) {
			this.sha1 = sha1;
			return this;
		}

		/** The SHA256 hash of the file data, which will be verified by the server if provided. */
		public Builder sha256(String sha256) {
			this.sha256 = sha256;
			return this;
		}

		/** The hexadecimal-encoded SHA512 hash of the file data, which will be verified by the server if provided. */
		public Builder sha512(String sha512) {
			this.sha512 = sha512;
			return this;
		}

		/** The URL to fetch the file payload from (for transfer_method source-url).*/
		public Builder sourceURL(String sourceURL) {
			this.sourceURL = sourceURL;
			return this;
		}

		/** The size (in bytes) of the file. If the upload doesn't match, the call will fail. */
		public Builder size(Integer size) {
			this.size = size;
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

		/** The transfer method used to upload the file data. */
		public Builder transferMethod(TransferMethod transferMethod) {
			this.transferMethod = transferMethod;
			return this;
		}
	}

	// Weird bug if making that trailing c uppercase, it print duplicated crc32C and crc32c in json
	public String getCRC32c() {
		return crc32c;
	}

	public void setCRC32c(String crc32c) {
		this.crc32c = crc32c;
	}

	public String getSHA256() {
		return sha256;
	}

	public void setSHA256(String sha256) {
		this.sha256 = sha256;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public FileFormat getFormat() {
		return format;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public String getMimetype() {
		return mimetype;
	}

	public String getParentID() {
		return parentID;
	}

	public String getPath() {
		return path;
	}

	public String getMD5() {
		return md5;
	}

	public String getSHA1() {
		return sha1;
	}

	public String getSHA512() {
		return sha512;
	}

	public Tags getTags() {
		return tags;
	}
}
