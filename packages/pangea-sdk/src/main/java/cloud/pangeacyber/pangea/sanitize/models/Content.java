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

	/**
	 * If redact is enabled, avoids redacting the file and instead returns the
	 * PII analysis engine results. Only works if redact is enabled.
	 */
	@JsonProperty("redact_detect_only")
	@JsonInclude(Include.NON_NULL)
	private Boolean redactDetectOnly;

	private Content(Builder builder) {
		this.urlIntel = builder.urlIntel;
		this.urlIntelProvider = builder.urlIntelProvider;
		this.domainIntel = builder.domainIntel;
		this.domainIntelProvider = builder.domainIntelProvider;
		this.defang = builder.defang;
		this.defangThreshold = builder.defangThreshold;
		this.redact = builder.redact;
		this.redactDetectOnly = builder.redactDetectOnly;
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

	public static class Builder {

		private Boolean urlIntel;
		private String urlIntelProvider;
		private Boolean domainIntel;
		private String domainIntelProvider;
		private Boolean defang;
		private Integer defangThreshold;
		private Boolean redact;
		private Boolean redactDetectOnly;

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

		public Builder redactDetectOnly(boolean redactDetectOnly) {
			this.redactDetectOnly = redactDetectOnly;
			return this;
		}

		public Content build() {
			return new Content(this);
		}
	}
}
