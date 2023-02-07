package cloud.pangeacyber.pangea.authn;

import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.clients.Flow;
import cloud.pangeacyber.pangea.authn.clients.Password;
import cloud.pangeacyber.pangea.authn.clients.User;
import cloud.pangeacyber.pangea.authn.responses.UserinfoResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;

final class UserinfoResquest {
    @JsonProperty("code")
    String code;

    public UserinfoResquest(String code) {
        this.code = code;
    }
}

public class AuthNClient extends Client {
    public static String serviceName = "authn";
    User user;
    Password password;
    Flow flow;

    public AuthNClient(Config config) {
        super(config, serviceName);
        user = new User(config);
        password = new Password(config);
        flow = new Flow(config);
    }

    // TODO: Doc
    public UserinfoResponse userinfo(String code) throws PangeaException, PangeaAPIException{
        UserinfoResquest request = new UserinfoResquest(code);
        UserinfoResponse resp = doPost("/v1/userinfo", request, UserinfoResponse.class);
        return resp;
    }

    public User user() {
        return user;
    }

    public Password password() {
        return password;
    }

    public Flow flow() {
        return flow;
    }

}
