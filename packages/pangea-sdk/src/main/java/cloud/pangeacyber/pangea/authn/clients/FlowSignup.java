package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.responses.FlowSignupPasswordResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;

final class FlowSignupPasswordRequest {

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

final class FlowSignupSocialRequest {

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

public class FlowSignup extends Client {

	public static final String serviceName = "authn";

	public FlowSignup(Config config) {
		super(config, serviceName);
	}

	// TODO: doc
	public FlowSignupPasswordResponse password(String flow_id, String password, String firstName, String lastName)
		throws PangeaException, PangeaAPIException {
		FlowSignupPasswordRequest request = new FlowSignupPasswordRequest(flow_id, password, firstName, lastName);
		return doPost("/v1/flow/signup/password", request, FlowSignupPasswordResponse.class);
	}

	// TODO: doc
	public FlowSignupPasswordResponse social(String flow_id, String cbState, String cbCode)
		throws PangeaException, PangeaAPIException {
		FlowSignupSocialRequest request = new FlowSignupSocialRequest(flow_id, cbState, cbCode);
		return doPost("/v1/flow/signup/social", request, FlowSignupPasswordResponse.class);
	}
}
