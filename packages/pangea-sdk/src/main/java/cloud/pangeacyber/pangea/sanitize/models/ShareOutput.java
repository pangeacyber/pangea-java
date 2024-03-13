package cloud.pangeacyber.pangea.sanitize.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShareOutput {

	@JsonProperty("enabled")
	@JsonInclude(Include.NON_NULL)
	private Boolean enabled;

	@JsonProperty("output_folder")
	@JsonInclude(Include.NON_NULL)
	private String outputFolder;

	private ShareOutput(Builder builder) {
		this.enabled = builder.enabled;
		this.outputFolder = builder.outputFolder;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public String getOutputFolder() {
		return outputFolder;
	}

	public static class Builder {

		private Boolean enabled;
		private String outputFolder;

		public Builder() {}

		public Builder enabled(Boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public Builder outputFolder(String outputFolder) {
			this.outputFolder = outputFolder;
			return this;
		}

		public ShareOutput build() {
			return new ShareOutput(this);
		}
	}
}
