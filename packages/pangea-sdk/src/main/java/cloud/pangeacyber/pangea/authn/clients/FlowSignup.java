package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.responses.FlowSignupPasswordResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;

final class FlowSignupPasswordRequest extends BaseRequest {

	@JsonProperty("flow_id")
	String flow_id;

	@JsonProperty("password")
	String password;

	@JsonProperty("first_name")
	String firstName;

	@JsonProperty("last_name")
	String lastName;

	public FlowSignupPasswordRequest(String flow_id, String password, String firstName, String lastName) {
		this.flow_id = flow_id;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
}

final class FlowSignupSocialRequest extends BaseRequest {

	@JsonProperty("flow_id")
	String flow_id;

	@JsonProperty("cb_state")
	String cbState;

	@JsonProperty("cb_code")
	String cbCode;

	public FlowSignupSocialRequest(String flow_id, String cbState, String cbCode) {
		this.flow_id = flow_id;
		this.cbState = cbState;
		this.cbCode = cbCode;
	}
}

public class FlowSignup extends AuthNBaseClient {

	public FlowSignup(AuthNClient.Builder builder) {
		super(builder);
	}

	/**
	 * Password Sign-up
	 * @pangea.description Signup a new account using a password.
	 * @pangea.operationId authn_post_v1_flow_signup_password
	 * @param flow_id An ID for a login or signup flow
	 * @param password A password
	 * @param firstName
	 * @param lastName
	 * @return FlowSignupPasswordResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * FlowSignupPasswordResponse response = client.flow().signup().password(
	 * 	"pfl_dxiqyuq7ndc5ycjwdgmguwuodizcaqhh",
	 * 	"My1s+Password",
	 * 	"Joe",
	 * 	"User");
	 * }
	 */
	public FlowSignupPasswordResponse password(String flow_id, String password, String firstName, String lastName)
		throws PangeaException, PangeaAPIException {
		FlowSignupPasswordRequest request = new FlowSignupPasswordRequest(flow_id, password, firstName, lastName);
		return post("/v1/flow/signup/password", request, FlowSignupPasswordResponse.class);
	}

	/**
	 * Social Sign-up
	 * @pangea.description Signup a new account using a social provider.
	 * @pangea.operationId authn_post_v1_flow_signup_social
	 * @param flow_id An ID for a login or signup flow
	 * @param cbState State tracking string for login callbacks
	 * @param cbCode A social oauth callback code
	 * @return FlowSignupPasswordResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * {@code
	 * FlowSignupPasswordResponse response = client.flow().signup().social(
	 * 	"pfl_dxiqyuq7ndc5ycjwdgmguwuodizcaqhh",
	 * 	"pcb_zurr3lkcwdp5keq73htsfpcii5k4zgm7",
	 * 	"poc_fwg3ul4db1jpivexru3wyj354u9ej5e2");
	 * }
	 */
	public FlowSignupPasswordResponse social(String flow_id, String cbState, String cbCode)
		throws PangeaException, PangeaAPIException {
		FlowSignupSocialRequest request = new FlowSignupSocialRequest(flow_id, cbState, cbCode);
		return post("/v1/flow/signup/social", request, FlowSignupPasswordResponse.class);
	}
}
