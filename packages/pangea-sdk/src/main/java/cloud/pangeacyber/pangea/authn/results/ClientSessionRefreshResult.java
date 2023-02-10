package cloud.pangeacyber.pangea.authn.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.authn.models.LoginToken;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientSessionRefreshResult {
    @JsonProperty("refresh_token")
    LoginToken refreshToken;

    @JsonProperty("active_token")
    LoginToken activeToken;

    public LoginToken getRefreshToken() {
        return refreshToken;
    }

    public LoginToken getActiveToken() {
        return activeToken;
    }
}
