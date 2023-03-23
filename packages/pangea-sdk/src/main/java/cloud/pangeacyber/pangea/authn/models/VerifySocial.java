package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifySocial {
    @JsonProperty("redirect_uri")
    String redirectURI;

    public String getRedirectURI() {
        return redirectURI;
    }
}
