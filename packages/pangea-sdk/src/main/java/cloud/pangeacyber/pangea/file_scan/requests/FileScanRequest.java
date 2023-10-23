package cloud.pangeacyber.pangea.file_scan.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.TransferMethod;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FileScanRequest extends BaseRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("provider")
	String provider;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("verbose")
	Boolean verbose;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("raw")
	Boolean raw;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("transfer_size")
	Integer size;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("transfer_crc32c")
	String crc32c;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("transfer_sha256")
	String sha256;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("transfer_method")
	TransferMethod method;

	protected FileScanRequest(Builder builder) {
		this.provider = builder.provider;
		this.verbose = builder.verbose;
		this.raw = builder.raw;
		this.crc32c = builder.crc32c;
		this.sha256 = builder.sha256;
		this.size = builder.size;
		this.method = builder.method;
	}

	public static class Builder {

		String provider;
		Boolean verbose;
		Boolean raw;
		Integer size;
		String crc32c;
		String sha256;
		TransferMethod method = TransferMethod.DIRECT;

		public Builder() {}

		public FileScanRequest build() {
			return new FileScanRequest(this);
		}

		public Builder provider(String provider) {
			this.provider = provider;
			return this;
		}

		public Builder verbose(Boolean verbose) {
			this.verbose = verbose;
			return this;
		}

		public Builder raw(Boolean raw) {
			this.raw = raw;
			return this;
		}

		public Builder size(Integer size) {
			this.size = size;
			return this;
		}

		public Builder crc32c(String crc) {
			this.crc32c = crc;
			return this;
		}

		public Builder sha256(String sha256) {
			this.sha256 = sha256;
			return this;
		}

		public Builder method(TransferMethod method) {
			this.method = method;
			return this;
		}
	}

	public String getProvider() {
		return provider;
	}

	public Boolean getVerbose() {
		return verbose;
	}

	public Boolean getRaw() {
		return raw;
	}

	public Integer getSize() {
		return size;
	}

	public String getCrc32c() {
		return crc32c;
	}

	public String getSha256() {
		return sha256;
	}

	public TransferMethod getMethod() {
		return method;
	}
}
