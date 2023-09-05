package cloud.pangeacyber.pangea.file_scan.requests;

import cloud.pangeacyber.pangea.BaseRequest;
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

	protected FileScanRequest(Builder builder) {
		this.provider = builder.provider;
		this.verbose = builder.verbose;
		this.raw = builder.raw;
	}

	public static class Builder {

		String provider;
		Boolean verbose;
		Boolean raw;

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
}
