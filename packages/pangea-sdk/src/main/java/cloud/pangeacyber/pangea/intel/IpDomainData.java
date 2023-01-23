package cloud.pangeacyber.pangea.intel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IpDomainData {
    @JsonProperty("domain_found")
    boolean domainFound;

    @JsonProperty("domain")
    String domain;

    public boolean isDomainFound() {
        return domainFound;
    }

    public String getDomain() {
        return domain;
    }
}
