package cloud.pangeacyber.pangea.intel.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class DomainWhoIsData {

	/**
	 * Represents information about a domain.
	 */

	@JsonProperty("domain_name")
	private String domainName;

	@JsonProperty("domain_availability")
	private String domainAvailability;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("created_date")
	private String createdDate;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("updated_date")
	private String updatedDate;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("expires_date")
	private String expiresDate;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("host_names")
	private List<String> hostNames;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("ips")
	private List<String> ips;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("registrar_name")
	private String registrarName;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("contact_email")
	private String contactEmail;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("estimated_domain_age")
	private Integer estimatedDomainAge;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("registrant_organization")
	private String registrantOrganization;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("registrant_country")
	private String registrantCountry;

	public DomainWhoIsData() {}

	public String getDomainName() {
		return domainName;
	}

	public String getDomainAvailability() {
		return domainAvailability;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public String getExpiresDate() {
		return expiresDate;
	}

	public List<String> getHostNames() {
		return hostNames;
	}

	public List<String> getIps() {
		return ips;
	}

	public String getRegistrarName() {
		return registrarName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public Integer getEstimatedDomainAge() {
		return estimatedDomainAge;
	}

	public String getRegistrantOrganization() {
		return registrantOrganization;
	}

	public String getRegistrantCountry() {
		return registrantCountry;
	}
}
