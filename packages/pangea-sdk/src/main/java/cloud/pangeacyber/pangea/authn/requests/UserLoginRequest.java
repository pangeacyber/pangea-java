package cloud.pangeacyber.pangea.authn.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cloud.pangeacyber.pangea.authn.models.Scopes;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginRequest {
    @JsonProperty("email")
    String email;

    @JsonProperty("secret")
    String secret;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("scopes")
    Scopes scopes;


    private UserLoginRequest(UserLoginRequestBuilder builder) {
        this.email = builder.email;
        this.secret = builder.secret;
        this.scopes = builder.scopes;
	}

	public static class UserLoginRequestBuilder{
        String email;
        String secret;
        Scopes scopes;

		public UserLoginRequestBuilder(String email, String secret) {
            this.email = email;
            this.secret = secret;
        }

		public UserLoginRequest build(){
			return new UserLoginRequest(this);
		}

        public UserLoginRequestBuilder setScopes(Scopes scopes) {
            this.scopes = scopes;
            return this;
        }

	}

}
