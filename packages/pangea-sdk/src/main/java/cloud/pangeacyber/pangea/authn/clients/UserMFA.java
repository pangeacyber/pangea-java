package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.models.MFAProvider;
import cloud.pangeacyber.pangea.authn.requests.UserMFAStartRequest;
import cloud.pangeacyber.pangea.authn.responses.*;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
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

public class UserMFA extends AuthNBaseClient {

	public UserMFA(AuthNClient.Builder builder) {
		super(builder);
	}

	/**
	 * Delete MFA Enrollment
	 * @pangea.description Delete MFA enrollment for a user.
	 * @pangea.operationId authn_post_v1_user_mfa_delete
	 * @param userID The identity of a user or a service
	 * @param mfaProvider Additional mechanism for authenticating a user's identity
	 * @return UserMFADeleteResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * client.user().mfa().delete(
	 * 	"pui_zgp532cx6opljeavvllmbi3iwmq72f7f",
	 * 	MFAProvider.TOTP);
	 * }
	 */
	public UserMFADeleteResponse delete(String userID, MFAProvider mfaProvider)
		throws PangeaException, PangeaAPIException {
		UserMFADeleteRequest request = new UserMFADeleteRequest(userID, mfaProvider);
		return post("/v1/user/mfa/delete", request, UserMFADeleteResponse.class);
	}

	/**
	 * Enroll In MFA
	 * @pangea.description Enroll in MFA for a user by proving the user has access to an MFA verification code.
	 * @pangea.operationId authn_post_v1_user_mfa_enroll
	 * @param userID The identity of a user or a service
	 * @param mfaProvider Additional mechanism for authenticating a user's identity
	 * @param code A six digit MFA code
	 * @return UserMFAEnrollResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * client.user().mfa().enroll(
	 * 	"pui_zgp532cx6opljeavvllmbi3iwmq72f7f",
	 * 	MFAProvider.TOTP,
	 * 	"999999");
	 * }
	 */
	public UserMFAEnrollResponse enroll(String userID, MFAProvider mfaProvider, String code)
		throws PangeaException, PangeaAPIException {
		UserMFAEnrollRequest request = new UserMFAEnrollRequest(userID, mfaProvider, code);
		return post("/v1/user/mfa/enroll", request, UserMFAEnrollResponse.class);
	}

	/**
	 * Start MFA Verification
	 * @pangea.description Start MFA verification for a user, generating a new one-time code, and sending it if necessary. When enrolling TOTP, this returns the TOTP secret.
	 * @pangea.operationId authn_post_v1_user_mfa_start
	 * @param request
	 * @return UserMFAStartResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * UserMFAStartResponse response = client.user().mfa().start(
	 * 	new UserMFAStartRequest().Builder(
	 * 		"pfl_dxiqyuq7ndc5ycjwdgmguwuodizcaqhh",
	 * 		MFAProvider.TOTP).build());
	 * }
	 */
	public UserMFAStartResponse start(UserMFAStartRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/user/mfa/start", request, UserMFAStartResponse.class);
	}

	/**
	 * Verify An MFA Code
	 * @pangea.description Verify that the user has access to an MFA verification code.
	 * @pangea.operationId authn_post_v1_user_mfa_verify
	 * @param userID The identity of a user or a service
	 * @param mfaProvider Additional mechanism for authenticating a user's identity
	 * @param code A six digit MFA code
	 * @return UserMFAVerifyResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * client.user().mfa().verify(
	 * 	"pui_zgp532cx6opljeavvllmbi3iwmq72f7f",
	 * 	MFAProvider.TOTP,
	 * 	"999999");
	 * }
	 */
	public UserMFAVerifyResponse verify(String userID, MFAProvider mfaProvider, String code)
		throws PangeaException, PangeaAPIException {
		UserMFAVerifyRequest request = new UserMFAVerifyRequest(userID, mfaProvider, code);
		return post("/v1/user/mfa/verify", request, UserMFAVerifyResponse.class);
	}
}
