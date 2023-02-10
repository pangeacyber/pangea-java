package cloud.pangeacyber.pangea.authn.clients;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.responses.UserInviteDeleteResponse;
import cloud.pangeacyber.pangea.authn.responses.UserInviteListResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;

final class UserInviteDeleteRequest {
    @JsonProperty("id")
    String id;

    public UserInviteDeleteRequest(String id) {
        this.id = id;
    }
}


public class UserInvite extends Client {
    public static final String serviceName = "authn";


    public UserInvite(Config config) {
        super(config, serviceName);
    }

    // TODO: Doc
    public UserInviteListResponse list() throws PangeaException, PangeaAPIException{
        return doPost("/v1/user/invite/list", new HashMap<String, String>(), UserInviteListResponse.class);
    }

    // TODO: Doc
    public UserInviteDeleteResponse delete(String id) throws PangeaException, PangeaAPIException{
        UserInviteDeleteRequest request = new UserInviteDeleteRequest(id);
        return doPost("/v1/user/invite/delete", request, UserInviteDeleteResponse.class);
    }

}
