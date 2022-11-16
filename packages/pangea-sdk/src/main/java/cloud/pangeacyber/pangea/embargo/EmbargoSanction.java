package cloud.pangeacyber.pangea.embargo;

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

    public void setEmbargoedCountryISOCode(String embargoedCountryISOCode) {
        this.embargoedCountryISOCode = embargoedCountryISOCode;
    }

    public String getIssuingCountry() {
        return issuingCountry;
    }

    public void setIssuingCountry(String issuingCountry) {
        this.issuingCountry = issuingCountry;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getEmbargoedCountryName() {
        return embargoedCountryName;
    }

    public void setEmbargoedCountryName(String embargoedCountryName) {
        this.embargoedCountryName = embargoedCountryName;
    }

    public EmbargoSanctionAnnotation getAnnotations() {
        return annotations;
    }

    public void setAnnotations(EmbargoSanctionAnnotation annotations) {
        this.annotations = annotations;
    }
}
