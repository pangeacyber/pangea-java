package cloud.pangeacyber.pangea.authn.clients;

import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.responses.PasswordUpdateResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;

final class PasswordUpdateResquest {
    @JsonProperty("email")
    String email;

    @JsonProperty("old_secret")
    String oldSecret;

    @JsonProperty("new_secret")
    String newSecret;

    public PasswordUpdateResquest(String email, String oldSecret, String newSecret) {
        this.email = email;
        this.oldSecret = oldSecret;
        this.newSecret = newSecret;
    }
}

public class Password extends Client {
    public static final String serviceName = "authn";

    public Password(Config config) {
        super(config, serviceName);
    }

    // TODO: Doc
    public PasswordUpdateResponse update(String email, String oldSecret, String newSecret) throws PangeaException, PangeaAPIException{
        PasswordUpdateResquest request = new PasswordUpdateResquest(email, oldSecret, newSecret);
        PasswordUpdateResponse resp = doPost("/v1/password/update", request, PasswordUpdateResponse.class);
        return resp;
    }

}
