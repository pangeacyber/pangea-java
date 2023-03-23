package cloud.pangeacyber.pangea.authn.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.authn.models.TOTPsecret;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMFAStartResult extends CommonFlowResult{
    @JsonProperty("totp_secret")
    TOTPsecret totpSecret;

    public TOTPsecret getTotpSecret() {
        return totpSecret;
    }
}
