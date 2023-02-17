package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.vault.models.JWKSet;

public class JWKGetResult {
    @JsonProperty("jwk")
    JWKSet jwk;

    public JWKGetResult() {
    }

    public JWKSet getJWK() {
        return jwk;
    }

}
