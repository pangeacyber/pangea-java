package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyMFAStart {
    @JsonProperty("mfa_providers")
    String[] mfaProviders;

    public String[] getMfaProviders() {
        return mfaProviders;
    }
}
