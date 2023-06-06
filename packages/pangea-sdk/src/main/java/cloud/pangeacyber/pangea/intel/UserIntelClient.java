package cloud.pangeacyber.pangea.intel;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.intel.models.PasswordStatus;
import cloud.pangeacyber.pangea.intel.models.UserBreachedRequest;
import cloud.pangeacyber.pangea.intel.models.UserBreachedResponse;
import cloud.pangeacyber.pangea.intel.models.UserPasswordBreachedRequest;
import cloud.pangeacyber.pangea.intel.models.UserPasswordBreachedResponse;
import java.util.Map;

public class UserIntelClient extends Client {

	public static String serviceName = "user-intel";

	public UserIntelClient(Config config) {
		super(config, serviceName);
	}

	/**
	 * Look up breached users
	 * @pangea.description Find out if an email address, username, phone number, or IP address was exposed in a security breach.
	 * @pangea.operationId user_intel_post_v1_user_breached
	 * @param request to send to user/breached endpoint
	 * @return UserBreachedResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 *	UserBreachedResponse response = client.breached(
	 *		new UserBreachedRequest.UserBreachedRequestBuilder()
	 *		.setPhoneNumber("8005550123")
	 *		.setVerbose(true)
	 *		.build()
	 *	);
	 * }
	 */
	public UserBreachedResponse breached(UserBreachedRequest request) throws PangeaException, PangeaAPIException {
		return doPost("/v1/user/breached", request, UserBreachedResponse.class);
	}

	/**
	 * Look up breached passwords
	 * @pangea.description Find out if a password has been exposed in security breaches by providing a 5 character prefix of the password hash.
	 * @pangea.operationId user_intel_post_v1_password_breached
	 * @param request to send to password/breached endpoint
	 * @return UserPasswordBreachedResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 *	UserPasswordBreachedResponse response = client.breached(
	 *		new UserPasswordBreachedRequest.UserPasswordBreachedRequestBuilder(HashType.SHA256, "5baa6")
	 *		.setProvider("spycloud")
	 *		.build()
	 *	);
	 * }
	 */
	public UserPasswordBreachedResponse breached(UserPasswordBreachedRequest request)
		throws PangeaException, PangeaAPIException {
		return doPost("/v1/password/breached", request, UserPasswordBreachedResponse.class);
	}

	public static PasswordStatus isPasswordBreached(UserPasswordBreachedResponse response, String hash)
		throws PangeaException {
		Map<String, Object> rawData = response.getResult().getRawData();
		if (rawData == null) {
			throw new PangeaException("Need raw data to check if hash is breached. Send request with raw=true", null);
		}

		Object hashData = rawData.get(hash);
		if (hashData != null) {
			// If hash is present in raw data, it's because it was breached
			return PasswordStatus.BREACHED;
		} else if (rawData.size() >= 1000) {
			// If it's not present, should check if I have all breached hash
			// Server will return a maximum of 1000 hash, so if breached count is greater than that,
			// I can't conclude is password is or is not breached
			return PasswordStatus.INCONCLUSIVE;
		} else {
			return PasswordStatus.UNBREACHED;
		}
	}
}
