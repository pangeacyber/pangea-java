package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.responses.UserProfileGetResponse;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.annotation.JsonProperty;

final class UserPasswordResetRequest {

	@JsonProperty("user_id")
	String userID;

	@JsonProperty("new_password")
	String newPassword;

	public UserPasswordResetRequest(String userID, String newPassword) {
		this.userID = userID;
		this.newPassword = newPassword;
	}
}

public class UserPassword extends Client {

	public static final String serviceName = "authn";

	public UserPassword(Config config) {
		super(config, serviceName);
	}

	// TODO: Doc
	public UserProfileGetResponse reset(String userID, String newPassword) throws PangeaException, PangeaAPIException {
		UserPasswordResetRequest request = new UserPasswordResetRequest(userID, newPassword);
		return doPost("/v1/user/password/reset", request, UserProfileGetResponse.class);
	}
}