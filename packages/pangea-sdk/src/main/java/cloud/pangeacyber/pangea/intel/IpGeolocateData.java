package cloud.pangeacyber.pangea.intel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IpGeolocateData {
    @JsonProperty("country")
    String Country;

    @JsonProperty("city")
    String City;

    @JsonProperty("latitude")
    Float Latitude;

    @JsonProperty("longitude")
    Float Longitude;

    @JsonProperty("postal_code")
    String PostalCode;

    @JsonProperty("country_code")
    String CountryCode;

    public String getCountry() {
        return Country;
    }

    public String getCity() {
        return City;
    }

    public Float getLatitude() {
        return Latitude;
    }

    public Float getLongitude() {
        return Longitude;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public String getCountryCode() {
        return CountryCode;
    }
}
