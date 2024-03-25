package cloud.pangeacyber.pangea.sanitize.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SanitizeFile {

	@JsonProperty("scan_provider")
	@JsonInclude(Include.NON_NULL)
	private String scanProvider;

	@JsonProperty("cdr_provider")
	@JsonInclude(Include.NON_NULL)
	private String cdrProvider;

	private SanitizeFile(Builder builder) {
		this.scanProvider = builder.scanProvider;
		this.cdrProvider = builder.cdrProvider;
	}

	public String getScanProvider() {
		return scanProvider;
	}

	public String getCDRProvider() {
		return cdrProvider;
	}

	public static class Builder {

		private String scanProvider;
		private String cdrProvider;

		public Builder() {}

		public Builder scanProvider(String scanProvider) {
			this.scanProvider = scanProvider;
			return this;
		}

		public Builder cdrProvider(String cdrProvider) {
			this.cdrProvider = cdrProvider;
			return this;
		}

		public SanitizeFile build() {
			return new SanitizeFile(this);
		}
	}
}
