package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Intelligence {

	@JsonProperty("embargo")
	private boolean embargo;

	@JsonProperty("ip_intel")
	private IPIntelligence ipIntel;

	@JsonProperty("domain_intel")
	private DomainIntelligence domainIntel;

	@JsonProperty("user_intel")
	private boolean userIntel;

	public boolean getEmbargo() {
		return embargo;
	}

	public IPIntelligence getIPIntelligence() {
		return ipIntel;
	}

	public DomainIntelligence getDomainIntelligence() {
		return domainIntel;
	}

	public boolean getUserIntel() {
		return userIntel;
	}
}
