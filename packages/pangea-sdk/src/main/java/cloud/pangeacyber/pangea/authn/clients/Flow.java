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
	 * Complete sign-up/sign-in
	 * @pangea.description Complete a sign-up or sign-in flow.
	 * @pangea.operationId authn_post_v2_flow_complete
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
	 * Start a sign-up/sign-in flow
	 * @pangea.description Start a new sign-up or sign-in flow.
	 * @pangea.operationId authn_post_v2_flow_start
	 * @param request
	 * @return FlowStartResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * FlowType[] flowTypes = new FlowType[]{FlowType.SIGNIN,FlowType.SIGNUP};
	 * FlowStartRequest request = new FlowStartRequest.Builder()
	 * 	.setEmail("joe.user@email.com")
	 * 	.setCbURI("https://www.myserver.com/callback")
	 * 	.setFlowType(flowTypes)
	 * 	.setInvitation("pmc_wuk7tvtpswyjtlsx52b7yyi2l7zotv4a")
	 * 	.build();
	 * 
	 * FlowStartResponse response = client.flow().start(request);
	 * }
	 */
	public FlowStartResponse start(FlowStartRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/flow/start", request, FlowStartResponse.class);
	}

	/**
	 * Restart a sign-up/sign-in flow
	 * @pangea.description Restart a sign-up/sign-in flow choice.
	 * @pangea.operationId authn_post_v2_flow_restart
	 * @param request
	 * @return FlowRestartResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * FlowRestartRequest request = new FlowRestartRequest.Builder(
	 * 	"pfl_dxiqyuq7ndc5ycjwdgmguwuodizcaqhh",
	 * 	FlowChoice.PASSWORD,
	 * 	new FlowRestartData()
	 * ).build();
	 * 
	 * FlowRestartResponse response = client.flow().restart(request);
	 * }
	 */
	public FlowRestartResponse restart(FlowRestartRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/flow/restart", request, FlowRestartResponse.class);
	}

	/**
	 * Update a sign-up/sign-in flow
	 * @pangea.description Update a sign-up/sign-in flow.
	 * @pangea.operationId authn_post_v2_flow_update
	 * @param request
	 * @return FlowUpdateResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * FlowUpdateData data = new FlowUpdateData();
	 * data.password = "somenewpassword";
	 * 
	 * FlowUpdateRequest request = new FlowUpdateRequest.Builder(
	 * 	"pfl_dxiqyuq7ndc5ycjwdgmguwuodizcaqhh",
	 * 	FlowChoice.PASSWORD,
	 * 	data
	 * ).build();
	 * 
	 * FlowUpdateResponse response = client.flow().update(request);
	 * }
	 */
	public FlowUpdateResponse update(FlowUpdateRequest request) throws PangeaException, PangeaAPIException {
		return post("/v2/flow/update", request, FlowUpdateResponse.class);
	}
}
