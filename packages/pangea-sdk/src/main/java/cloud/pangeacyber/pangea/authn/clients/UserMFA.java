package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.models.MFAProvider;
import cloud.pangeacyber.pangea.authn.requests.UserMFAStartRequest;
import cloud.pangeacyber.pangea.authn.responses.*;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

final class UserMFADeleteRequest extends BaseRequest {

	@JsonProperty("user_id")
	String userID;

	@JsonProperty("mfa_provider")
	MFAProvider mfaProvider;

	public UserMFADeleteRequest(String userID, MFAProvider mfaProvider) {
		this.userID = userID;
		this.mfaProvider = mfaProvider;
	}
}

final class UserMFAEnrollRequest extends BaseRequest {

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

final class UserMFAVerifyRequest extends BaseRequest {

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

	public static final String serviceName = "authn";
	private static final boolean supportMultiConfig = false;

	public UserMFA(Config config) {
		super(config, serviceName, supportMultiConfig);
	}

	// TODO: Doc
	public UserMFADeleteResponse delete(String userID, MFAProvider mfaProvider)
		throws PangeaException, PangeaAPIException {
		UserMFADeleteRequest request = new UserMFADeleteRequest(userID, mfaProvider);
		return doPost("/v1/user/mfa/delete", request, UserMFADeleteResponse.class);
	}

	// TODO: Doc
	public UserMFAEnrollResponse enroll(String userID, MFAProvider mfaProvider, String code)
		throws PangeaException, PangeaAPIException {
		UserMFAEnrollRequest request = new UserMFAEnrollRequest(userID, mfaProvider, code);
		return doPost("/v1/user/mfa/enroll", request, UserMFAEnrollResponse.class);
	}

	// TODO: Doc
	public UserMFAStartResponse start(UserMFAStartRequest request) throws PangeaException, PangeaAPIException {
		return doPost("/v1/user/mfa/start", request, UserMFAStartResponse.class);
	}

	// TODO: Doc
	public UserMFAVerifyResponse verify(String userID, MFAProvider mfaProvider, String code)
		throws PangeaException, PangeaAPIException {
		UserMFAVerifyRequest request = new UserMFAVerifyRequest(userID, mfaProvider, code);
		return doPost("/v1/user/mfa/verify", request, UserMFAVerifyResponse.class);
	}
}
