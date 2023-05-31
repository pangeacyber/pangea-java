package cloud.pangeacyber.pangea.embargo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class EmbargoSanction {

	@JsonProperty("embargoed_country_iso_code")
	String embargoedCountryISOCode;

	@JsonProperty("issuing_country")
	String issuingCountry;

	@JsonProperty("list_name")
	String listName;

	@JsonProperty("embargoed_country_name")
	String embargoedCountryName;

	@JsonProperty("annotations")
	EmbargoSanctionAnnotation annotations;

	public String getEmbargoedCountryISOCode() {
		return embargoedCountryISOCode;
	}

	public String getIssuingCountry() {
		return issuingCountry;
	}

	public String getListName() {
		return listName;
	}

	public String getEmbargoedCountryName() {
		return embargoedCountryName;
	}

	public EmbargoSanctionAnnotation getAnnotations() {
		return annotations;
	}
}
