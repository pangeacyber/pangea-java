package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrollMFAComplete {
    @JsonProperty("totp_secret")
    TOTPsecret totpSecret;

    public TOTPsecret getTotpSecret() {
        return totpSecret;
    }
}
