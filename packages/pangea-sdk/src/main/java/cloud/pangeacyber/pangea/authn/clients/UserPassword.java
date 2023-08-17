package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.AuthNClient;
import cloud.pangeacyber.pangea.authn.responses.UserPasswordResetResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;

final class UserPasswordResetRequest extends BaseRequest {

	@JsonProperty("user_id")
	String userID;

	@JsonProperty("new_password")
	String newPassword;

	public UserPasswordResetRequest(String userID, String newPassword) {
		this.userID = userID;
		this.newPassword = newPassword;
	}
}

public class UserPassword extends AuthNBaseClient {

	public UserPassword(AuthNClient.Builder builder) {
		super(builder);
	}

	/**
	 * User Password Reset
	 * @pangea.description Manually reset a user's password.
	 * @pangea.operationId authn_post_v1_user_password_reset
	 * @param userID The identity of a user or a service
	 * @param newPassword
	 * @return UserPasswordResetResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * client.user().password().reset(
	 * 	"pui_xpkhwpnz2cmegsws737xbsqnmnuwtvm5",
	 * 	"My2n+Password");
	 * }
	 */
	public UserPasswordResetResponse reset(String userID, String newPassword)
		throws PangeaException, PangeaAPIException {
		UserPasswordResetRequest request = new UserPasswordResetRequest(userID, newPassword);
		return post("/v1/user/password/reset", request, UserPasswordResetResponse.class);
	}
}
