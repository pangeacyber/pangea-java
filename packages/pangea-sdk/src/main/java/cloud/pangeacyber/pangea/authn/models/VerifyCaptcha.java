package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyCaptcha {
    @JsonProperty("site_key")
    String siteKey;

    public String getSiteKey() {
        return siteKey;
    }

}
