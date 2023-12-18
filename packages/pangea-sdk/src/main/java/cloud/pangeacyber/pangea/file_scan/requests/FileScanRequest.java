package cloud.pangeacyber.pangea.file_scan.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.TransferMethod;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FileScanRequest extends BaseRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("provider")
	protected String provider;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("verbose")
	protected Boolean verbose;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("raw")
	protected Boolean raw;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("source_url")
	protected String sourceURL;

	protected FileScanRequest(FileScanRequest request) {
		this.provider = request.getProvider();
		this.verbose = request.getVerbose();
		this.raw = request.getRaw();
		this.setTransferMethod(request.getTransferMethod());
	}

	protected FileScanRequest(Builder builder) {
		this.provider = builder.provider;
		this.verbose = builder.verbose;
		this.raw = builder.raw;
		this.setTransferMethod(builder.transferMethod);
	}

	protected FileScanRequest() {}

	public static class Builder {

		String provider;
		Boolean verbose;
		Boolean raw;
		String sourceURL;
		TransferMethod transferMethod = TransferMethod.POST_URL;

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

		public Builder sourceURL(String sourceURL) {
			this.sourceURL = sourceURL;
			return this;
		}

		public Builder transferMethod(TransferMethod transferMethod) {
			this.transferMethod = transferMethod;
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

	public String getSourceURL() {
		return sourceURL;
	}
}
