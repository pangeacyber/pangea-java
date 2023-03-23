package cloud.pangeacyber.pangea.authn.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.authn.models.LoginToken;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowCompleteResult {
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("login_token")
    LoginToken loginToken;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("refresh_token")
    LoginToken refreshToken;

    public LoginToken getLoginToken() {
        return loginToken;
    }

    public LoginToken getRefreshToken() {
        return refreshToken;
    }

}
