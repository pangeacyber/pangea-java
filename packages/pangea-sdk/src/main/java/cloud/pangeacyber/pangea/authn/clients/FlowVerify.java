package cloud.pangeacyber.pangea.authn.clients;

import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.responses.FlowVerifyCaptchaResponse;
import cloud.pangeacyber.pangea.authn.responses.FlowVerifyEmailResponse;
import cloud.pangeacyber.pangea.authn.responses.FlowVerifyPasswordResponse;
import cloud.pangeacyber.pangea.authn.responses.FlowVerifySocialResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;

final class FlowVerifyCaptchaRequest {
    @JsonProperty("flow_id")
    String flowID;

    @JsonProperty("code")
    String code;

    public FlowVerifyCaptchaRequest(String flowID, String code) {
        this.flowID = flowID;
        this.code = code;
    }
}

final class FlowVerifyEmailRequest {
    @JsonProperty("flow_id")
    String flowID;

    @JsonProperty("cb_state")
    String cbState;

    @JsonProperty("cb_code")
    String cbCode;

    public FlowVerifyEmailRequest(String flowID, String cbState, String cbCode) {
        this.flowID = flowID;
        this.cbState = cbState;
        this.cbCode = cbCode;
    }
}

final class FlowVerifyPasswordRequest {
    @JsonProperty("flow_id")
    String flowID;

    @JsonProperty("password")
    String password;

    public FlowVerifyPasswordRequest(String flowID, String password) {
        this.flowID = flowID;
        this.password = password;
    }
}

final class FlowVerifySocialRequest {
    @JsonProperty("flow_id")
    String flowID;

    @JsonProperty("cb_state")
    String cbState;

    @JsonProperty("cb_code")
    String cbCode;

    public FlowVerifySocialRequest(String flowID, String cbState, String cbCode) {
        this.flowID = flowID;
        this.cbState = cbState;
        this.cbCode = cbCode;
    }
}


public class FlowVerify extends Client {
    public static final String serviceName = "authn";
    private FlowVerifyMFA mfa;

    public FlowVerify(Config config) {
        super(config, serviceName);
        mfa = new FlowVerifyMFA(config);
    }

    // TODO: doc
    public FlowVerifyCaptchaResponse captcha(String flowId, String code) throws PangeaException, PangeaAPIException{
        FlowVerifyCaptchaRequest request = new FlowVerifyCaptchaRequest(flowId, code);
        return doPost("/v1/flow/verify/captcha", request, FlowVerifyCaptchaResponse.class);
    }

    // TODO: doc
    public FlowVerifyEmailResponse email(String flowId, String cbState, String cbCode) throws PangeaException, PangeaAPIException{
        FlowVerifyEmailRequest request = new FlowVerifyEmailRequest(flowId, cbState, cbCode);
        return doPost("/v1/flow/verify/email", request, FlowVerifyEmailResponse.class);
    }

    // TODO: doc
    public FlowVerifyPasswordResponse password(String flowId, String password) throws PangeaException, PangeaAPIException{
        FlowVerifyPasswordRequest request = new FlowVerifyPasswordRequest(flowId, password);
        return doPost("/v1/flow/verify/password", request, FlowVerifyPasswordResponse.class);
    }

    // TODO: doc
    public FlowVerifySocialResponse social(String flowId, String cbState, String cbCode) throws PangeaException, PangeaAPIException{
        FlowVerifySocialRequest request = new FlowVerifySocialRequest(flowId, cbState, cbCode);
        return doPost("/v1/flow/verify/social", request, FlowVerifySocialResponse.class);
    }

    public FlowVerifyMFA mfa() {
        return mfa;
    }

}
