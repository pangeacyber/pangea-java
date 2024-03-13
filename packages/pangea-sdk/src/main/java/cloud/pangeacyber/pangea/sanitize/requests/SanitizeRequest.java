package cloud.pangeacyber.pangea.sanitize.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.TransferMethod;
import cloud.pangeacyber.pangea.sanitize.models.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SanitizeRequest extends BaseRequest {

	@JsonProperty("source_url")
	@JsonInclude(Include.NON_NULL)
	private String sourceUrl;

	@JsonProperty("share_id")
	@JsonInclude(Include.NON_NULL)
	private String shareId;

	@JsonProperty("file")
	@JsonInclude(Include.NON_NULL)
	private SanitizeFile file;

	@JsonProperty("content")
	@JsonInclude(Include.NON_NULL)
	private Content content;

	@JsonProperty("share_output")
	@JsonInclude(Include.NON_NULL)
	private ShareOutput shareOutput;

	@JsonProperty("size")
	@JsonInclude(Include.NON_NULL)
	private Integer size;

	@JsonProperty("crc32c")
	@JsonInclude(Include.NON_NULL)
	private String crc32c;

	@JsonProperty("sha256")
	@JsonInclude(Include.NON_NULL)
	private String sha256;

	@JsonProperty("uploaded_file_name")
	@JsonInclude(Include.NON_NULL)
	private String uploadedFileName;

	private SanitizeRequest(Builder builder) {
		this.setTransferMethod(builder.transferMethod);
		this.sourceUrl = builder.sourceUrl;
		this.shareId = builder.shareId;
		this.file = builder.file;
		this.content = builder.content;
		this.shareOutput = builder.shareOutput;
		this.size = builder.size;
		this.crc32c = builder.crc32c;
		this.sha256 = builder.sha256;
		this.uploadedFileName = builder.uploadedFileName;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public String getShareId() {
		return shareId;
	}

	public SanitizeFile getFile() {
		return file;
	}

	public Content getContent() {
		return content;
	}

	public ShareOutput getShareOutput() {
		return shareOutput;
	}

	public Integer getSize() {
		return size;
	}

	public String getCRC32c() {
		return crc32c;
	}

	public String getSHA256() {
		return sha256;
	}

	public String getUploadedFileName() {
		return uploadedFileName;
	}

	public static class Builder {

		private TransferMethod transferMethod = TransferMethod.POST_URL;
		private String sourceUrl;
		private String shareId;
		private SanitizeFile file;
		private Content content;
		private ShareOutput shareOutput;
		private Integer size;
		private String crc32c;
		private String sha256;
		private String uploadedFileName;

		public Builder() {}

		public Builder transferMethod(TransferMethod transferMethod) {
			this.transferMethod = transferMethod;
			return this;
		}

		public Builder sourceUrl(String sourceUrl) {
			this.sourceUrl = sourceUrl;
			return this;
		}

		public Builder shareId(String shareId) {
			this.shareId = shareId;
			return this;
		}

		public Builder file(SanitizeFile file) {
			this.file = file;
			return this;
		}

		public Builder content(Content content) {
			this.content = content;
			return this;
		}

		public Builder shareOutput(ShareOutput shareOutput) {
			this.shareOutput = shareOutput;
			return this;
		}

		public Builder size(Integer size) {
			this.size = size;
			return this;
		}

		public Builder crc32c(String crc32c) {
			this.crc32c = crc32c;
			return this;
		}

		public Builder sha256(String sha256) {
			this.sha256 = sha256;
			return this;
		}

		public Builder uploadedFileName(String uploadedFileName) {
			this.uploadedFileName = uploadedFileName;
			return this;
		}

		public SanitizeRequest build() {
			return new SanitizeRequest(this);
		}
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setCRC32C(String crc32c) {
		this.crc32c = crc32c;
	}

	public void setSHA256(String sha256) {
		this.sha256 = sha256;
	}
}
