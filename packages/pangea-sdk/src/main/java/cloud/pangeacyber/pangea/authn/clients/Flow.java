package cloud.pangeacyber.pangea.authn.clients;

import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.requests.FlowVerifyMFACompleteRequest;
import cloud.pangeacyber.pangea.authn.responses.FlowCompleteResponse;
import cloud.pangeacyber.pangea.authn.responses.FlowStartResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;

final class FlowCompleteRequest {
    @JsonProperty("flow_id")
    String flowID;

    public FlowCompleteRequest(String flowID) {
        this.flowID = flowID;
    }
}


public class Flow extends Client {
    public static String serviceName = "authn";
    FlowEnroll enroll;
    FlowSignup signup;
    FlowVerify verify;

    public Flow(Config config) {
        super(config, serviceName);
        enroll = new FlowEnroll(config);
        signup = new FlowSignup(config);
        verify = new FlowVerify(config);
    }

    // TODO: Doc
    public FlowCompleteResponse complete(String flowID) throws PangeaException, PangeaAPIException{
        FlowCompleteRequest request = new FlowCompleteRequest(flowID);
        return doPost("/v1/flow/complete", request, FlowCompleteResponse.class);
    }

    // TODO: Doc
    public FlowStartResponse start(FlowVerifyMFACompleteRequest request) throws PangeaException, PangeaAPIException{
        return doPost("/v1/flow/start", request, FlowStartResponse.class);
    }

    public FlowEnroll enroll() {
        return enroll;
    }

    public FlowSignup signup() {
        return signup;
    }

    public FlowVerify verify() {
        return verify;
    }
}
