package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.vault.models.JWKSet;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JWKGetResult {
    @JsonProperty("jwk")
    JWKSet jwk;

    public JWKGetResult() {
    }

    public JWKSet getJWK() {
        return jwk;
    }

}
