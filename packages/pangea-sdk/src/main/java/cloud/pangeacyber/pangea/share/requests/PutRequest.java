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

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("name")
	String name;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("format")
	FileFormat format;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("metadata")
	Metadata metadata;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("mimetype")
	String mimetype;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("parent_id")
	String parentID;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("path")
	String path;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("crc32c")
	String crc32c;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("md5")
	String md5;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("sha1")
	String sha1;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("sha256")
	String sha256;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("sha512")
	String sha512;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("size")
	Integer size;

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
		String crc32c;
		String md5;
		String sha1;
		String sha256;
		String sha512;
		Integer size;
		Tags tags;
		String bucketId;
		TransferMethod transferMethod;

		public Builder() {}

		public PutRequest build() {
			return new PutRequest(this);
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder format(FileFormat format) {
			this.format = format;
			return this;
		}

		public Builder metadata(Metadata metadata) {
			this.metadata = metadata;
			return this;
		}

		public Builder mimetype(String mimetype) {
			this.mimetype = mimetype;
			return this;
		}

		public Builder parentID(String parentID) {
			this.parentID = parentID;
			return this;
		}

		public Builder path(String path) {
			this.path = path;
			return this;
		}

		public Builder crc32c(String crc32c) {
			this.crc32c = crc32c;
			return this;
		}

		public Builder md5(String md5) {
			this.md5 = md5;
			return this;
		}

		public Builder sha1(String sha1) {
			this.sha1 = sha1;
			return this;
		}

		public Builder sha256(String sha256) {
			this.sha256 = sha256;
			return this;
		}

		public Builder sha512(String sha512) {
			this.sha512 = sha512;
			return this;
		}

		public Builder size(Integer size) {
			this.size = size;
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
