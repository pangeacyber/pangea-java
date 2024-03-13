package cloud.pangeacyber.pangea.sanitize.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Content {

	@JsonProperty("url_intel")
	@JsonInclude(Include.NON_NULL)
	private Boolean urlIntel;

	@JsonProperty("url_intel_provider")
	@JsonInclude(Include.NON_NULL)
	private String urlIntelProvider;

	@JsonProperty("domain_intel")
	@JsonInclude(Include.NON_NULL)
	private Boolean domainIntel;

	@JsonProperty("domain_intel_provider")
	@JsonInclude(Include.NON_NULL)
	private String domainIntelProvider;

	@JsonProperty("defang")
	@JsonInclude(Include.NON_NULL)
	private Boolean defang;

	@JsonProperty("defang_threshold")
	@JsonInclude(Include.NON_NULL)
	private Integer defangThreshold;

	@JsonProperty("redact")
	@JsonInclude(Include.NON_NULL)
	private Boolean redact;

	@JsonProperty("remove_attachments")
	@JsonInclude(Include.NON_NULL)
	private Boolean removeAttachments;

	@JsonProperty("remove_interactive")
	@JsonInclude(Include.NON_NULL)
	private Boolean removeInteractive;

	private Content(Builder builder) {
		this.urlIntel = builder.urlIntel;
		this.urlIntelProvider = builder.urlIntelProvider;
		this.domainIntel = builder.domainIntel;
		this.domainIntelProvider = builder.domainIntelProvider;
		this.defang = builder.defang;
		this.defangThreshold = builder.defangThreshold;
		this.redact = builder.redact;
		this.removeAttachments = builder.removeAttachments;
		this.removeInteractive = builder.removeInteractive;
	}

	public Boolean getUrlIntel() {
		return urlIntel;
	}

	public String getUrlIntelProvider() {
		return urlIntelProvider;
	}

	public Boolean getDomainIntel() {
		return domainIntel;
	}

	public String getDomainIntelProvider() {
		return domainIntelProvider;
	}

	public Boolean getDefang() {
		return defang;
	}

	public Integer getDefangThreshold() {
		return defangThreshold;
	}

	public Boolean getRedact() {
		return redact;
	}

	public Boolean getRemoveAttachments() {
		return removeAttachments;
	}

	public Boolean getRemoveInteractive() {
		return removeInteractive;
	}

	public static class Builder {

		private Boolean urlIntel;
		private String urlIntelProvider;
		private Boolean domainIntel;
		private String domainIntelProvider;
		private Boolean defang;
		private Integer defangThreshold;
		private Boolean redact;
		private Boolean removeAttachments;
		private Boolean removeInteractive;

		public Builder() {}

		public Builder urlIntel(Boolean urlIntel) {
			this.urlIntel = urlIntel;
			return this;
		}

		public Builder urlIntelProvider(String urlIntelProvider) {
			this.urlIntelProvider = urlIntelProvider;
			return this;
		}

		public Builder domainIntel(Boolean domainIntel) {
			this.domainIntel = domainIntel;
			return this;
		}

		public Builder domainIntelProvider(String domainIntelProvider) {
			this.domainIntelProvider = domainIntelProvider;
			return this;
		}

		public Builder defang(Boolean defang) {
			this.defang = defang;
			return this;
		}

		public Builder defangThreshold(Integer defangThreshold) {
			this.defangThreshold = defangThreshold;
			return this;
		}

		public Builder redact(Boolean redact) {
			this.redact = redact;
			return this;
		}

		public Builder removeAttachments(Boolean removeAttachments) {
			this.removeAttachments = removeAttachments;
			return this;
		}

		public Builder removeInteractive(Boolean removeInteractive) {
			this.removeInteractive = removeInteractive;
			return this;
		}

		public Content build() {
			return new Content(this);
		}
	}
}
