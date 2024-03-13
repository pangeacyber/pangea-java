package cloud.pangeacyber.pangea.sanitize.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DefangData {

	@JsonProperty("external_urls_count")
	@JsonInclude(Include.NON_NULL)
	private Integer externalURLsCount;

	@JsonProperty("external_domains_count")
	@JsonInclude(Include.NON_NULL)
	private Integer externalDomainsCount;

	@JsonProperty("defanged_count")
	@JsonInclude(Include.NON_NULL)
	private Integer defangedCount;

	@JsonProperty("url_intel_summary")
	@JsonInclude(Include.NON_NULL)
	private String urlIntelSummary;

	@JsonProperty("domain_intel_summary")
	@JsonInclude(Include.NON_NULL)
	private String domainIntelSummary;

	public DefangData() {}

	public Integer getExternalURLsCount() {
		return externalURLsCount;
	}

	public Integer getExternalDomainsCount() {
		return externalDomainsCount;
	}

	public Integer getDefangedCount() {
		return defangedCount;
	}

	public String getURLIntelSummary() {
		return urlIntelSummary;
	}

	public String getDomainIntelSummary() {
		return domainIntelSummary;
	}
}
