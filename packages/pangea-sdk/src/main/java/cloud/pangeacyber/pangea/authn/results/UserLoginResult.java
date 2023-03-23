package cloud.pangeacyber.pangea.authn.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.authn.models.LoginToken;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginResult {
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("refresh_token")
    LoginToken refreshToken;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("active_token")
    LoginToken activeToken;

    public LoginToken getRefreshToken() {
        return refreshToken;
    }

    public LoginToken getActiveToken() {
        return activeToken;
    }

}
