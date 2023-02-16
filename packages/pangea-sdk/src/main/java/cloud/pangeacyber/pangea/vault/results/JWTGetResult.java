package cloud.pangeacyber.pangea.vault.results;

import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.vault.models.JWKSet;

public class JWTGetResult {
    @JsonProperty("jwk")
    JWKSet jwk;

    public JWTGetResult() {
    }

    public JWKSet getJWK() {
        return jwk;
    }

}
