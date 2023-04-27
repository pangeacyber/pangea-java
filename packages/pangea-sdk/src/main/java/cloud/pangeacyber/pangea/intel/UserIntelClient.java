package cloud.pangeacyber.pangea.intel;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.intel.requests.*;
import cloud.pangeacyber.pangea.intel.responses.*;

public class UserIntelClient extends Client {

	public static String serviceName = "user-intel";

	public UserIntelClient(Config config) {
		super(config, serviceName);
	}

	/**
	 * Look up breached users
	 * @pangea.description Find out if an email address, username, phone number, or IP address was exposed in a security breach.
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
	 */
	public UserBreachedResponse breached(UserBreachedRequest request) throws PangeaException, PangeaAPIException {
		return doPost("/v1/user/breached", request, UserBreachedResponse.class);
	}

	/**
	 * Look up breached passwords
	 * @pangea.description Find out if a password has been exposed in security breaches by providing a 5 character prefix of the password hash.
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
	 */
	public UserPasswordBreachedResponse breached(UserPasswordBreachedRequest request)
		throws PangeaException, PangeaAPIException {
		return doPost("/v1/password/breached", request, UserPasswordBreachedResponse.class);
	}
}
