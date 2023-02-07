package cloud.pangeacyber.pangea.authn.clients;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.models.MFAProvider;
import cloud.pangeacyber.pangea.authn.responses.UserMFADeleteResponse;
import cloud.pangeacyber.pangea.authn.responses.UserMFAEnrollResponse;
import cloud.pangeacyber.pangea.authn.responses.UserMFAStartResponse;
import cloud.pangeacyber.pangea.authn.responses.UserMFAVerifyResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;

final class UserMFADeleteRequest {
    @JsonProperty("user_id")
    String userID;

    @JsonProperty("mfa_provider")
    MFAProvider mfaProvider;

    public UserMFADeleteRequest(String userID, MFAProvider mfaProvider) {
        this.userID = userID;
        this.mfaProvider = mfaProvider;
    }
}

final class UserMFAEnrollRequest {
    @JsonProperty("user_id")
    String userID;

    @JsonProperty("mfa_provider")
    MFAProvider mfaProvider;

    @JsonProperty("code")
    String code;

    public UserMFAEnrollRequest(String userID, MFAProvider mfaProvider, String code) {
        this.userID = userID;
        this.mfaProvider = mfaProvider;
        this.code = code;
    }
}

final class UserMFAStartRequest {
    @JsonProperty("user_id")
    String userID;

    @JsonProperty("mfa_provider")
    MFAProvider mfaProvider;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("enroll")
    Boolean enroll;

    public UserMFAStartRequest(String userID, MFAProvider mfaProvider) {
        this.userID = userID;
        this.mfaProvider = mfaProvider;
        this.enroll = null;
    }

    public UserMFAStartRequest(String userID, MFAProvider mfaProvider, Boolean enroll) {
        this.userID = userID;
        this.mfaProvider = mfaProvider;
        this.enroll = enroll;
    }
}

final class UserMFAVerifyRequest {
    @JsonProperty("user_id")
    String userID;

    @JsonProperty("mfa_provider")
    MFAProvider mfaProvider;

    @JsonProperty("code")
    String code;

    public UserMFAVerifyRequest(String userID, MFAProvider mfaProvider, String code) {
        this.userID = userID;
        this.mfaProvider = mfaProvider;
        this.code = code;
    }
}

public class UserMFA extends Client {
    public static String serviceName = "authn";

    public UserMFA(Config config) {
        super(config, serviceName);
    }

    // TODO: Doc
    public UserMFADeleteResponse delete(String userID, MFAProvider mfaProvider) throws PangeaException, PangeaAPIException{
        UserMFADeleteRequest request = new UserMFADeleteRequest(userID, mfaProvider);
        return doPost("/v1/user/mfa/delete", request, UserMFADeleteResponse.class);
    }

    // TODO: Doc
    public UserMFAEnrollResponse enroll(String userID, MFAProvider mfaProvider, String code) throws PangeaException, PangeaAPIException{
        UserMFAEnrollRequest request = new UserMFAEnrollRequest(userID, mfaProvider, code);
        return doPost("/v1/user/mfa/enroll", request, UserMFAEnrollResponse.class);
    }

    // TODO: Doc
    public UserMFAStartResponse start(String userID, MFAProvider mfaProvider, Boolean enroll) throws PangeaException, PangeaAPIException{
        UserMFAStartRequest request = new UserMFAStartRequest(userID, mfaProvider, enroll);
        return doPost("/v1/user/mfa/start", request, UserMFAStartResponse.class);
    }

    // TODO: Doc
    public UserMFAStartResponse start(String userID, MFAProvider mfaProvider) throws PangeaException, PangeaAPIException{
        UserMFAStartRequest request = new UserMFAStartRequest(userID, mfaProvider, null);
        return doPost("/v1/user/mfa/start", request, UserMFAStartResponse.class);
    }

    // TODO: Doc
    public UserMFAVerifyResponse verify(String userID, MFAProvider mfaProvider, String code) throws PangeaException, PangeaAPIException{
        UserMFAVerifyRequest request = new UserMFAVerifyRequest(userID, mfaProvider, code);
        return doPost("/v1/user/mfa/verify", request, UserMFAVerifyResponse.class);
    }

}
