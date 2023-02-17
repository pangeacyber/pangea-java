package cloud.pangeacyber.pangea.vault.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JWKSet {
    @JsonProperty("keys")
    JWK[] keys;

    public JWKSet() {
    }

    public JWK[] getKeys() {
        return keys;
    }

}
