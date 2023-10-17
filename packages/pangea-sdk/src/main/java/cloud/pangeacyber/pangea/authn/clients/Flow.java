package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.requests.FlowRestartRequest;
import cloud.pangeacyber.pangea.authn.requests.FlowStartRequest;
import cloud.pangeacyber.pangea.authn.requests.FlowUpdateRequest;
import cloud.pangeacyber.pangea.authn.responses.FlowCompleteResponse;
import cloud.pangeacyber.pangea.authn.responses.FlowRestartResponse;
import cloud.pangeacyber.pangea.authn.responses.FlowStartResponse;
import cloud.pangeacyber.pangea.authn.responses.FlowUpdateResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;

final class FlowCompleteRequest extends BaseRequest {

	@JsonProperty("flow_id")
	String flowID;

	public FlowCompleteRequest(String flowID) {
		this.flowID = flowID;
	}
}

public class Flow extends AuthNBaseClient {

	public Flow(AuthNClient.Builder builder) {
		super(builder);
	}

	/**
	 * Complete Sign-up/in
	 * @pangea.description Complete a login or signup flow.
	 * @pangea.operationId authn_post_v1_flow_complete
	 * @param flowID An ID for a login or signup flow
	 * @return FlowCompleteResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * FlowCompleteResponse response = client.flow().complete("pfl_dxiqyuq7ndc5ycjwdgmguwuodizcaqhh");
	 * }
	 */
	public FlowCompleteResponse complete(String flowID) throws PangeaException, PangeaAPIException {
		FlowCompleteRequest request = new FlowCompleteRequest(flowID);
		return post("/v2/flow/complete", request, FlowCompleteResponse.class);
	}

	/**
	 * Start a sign-up/in
	 * @pangea.description Start a new signup or signin flow.
	 * @pangea.operationId authn_post_v1_flow_start
	 * @param request
	 * @return FlowStartResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * FlowStartResponse response = client.flow().start(
	 * 	new FlowStartRequest.Builder()
	 * 		.setCbURI("https://www.myserver.com/callback")
	 * 		.setEmail("joe.user@email.com")
	 * 		.setFlowType(FlowType.SIGNIN)
	 * 		.setProvider(IDProvider.PASSWORD)
	 * 		.build());
	 * }
	 */
	public FlowStartResponse start(FlowStartRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/flow/start", request, FlowStartResponse.class);
	}

	public FlowRestartResponse restart(FlowRestartRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/flow/restart", request, FlowRestartResponse.class);
	}

	public FlowUpdateResponse update(FlowUpdateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/flow/update", request, FlowUpdateResponse.class);
	}
}
