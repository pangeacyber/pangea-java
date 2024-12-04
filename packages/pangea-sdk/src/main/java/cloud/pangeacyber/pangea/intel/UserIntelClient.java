package cloud.pangeacyber.pangea.intel;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.intel.models.PasswordStatus;
import cloud.pangeacyber.pangea.intel.requests.*;
import cloud.pangeacyber.pangea.intel.responses.*;
import java.util.Map;

public class UserIntelClient extends BaseClient {

	public static final String serviceName = "user-intel";

	public UserIntelClient(Builder builder) {
		super(builder, serviceName);
	}

	public static class Builder extends BaseClient.Builder<Builder> {

		public Builder(Config config) {
			super(config);
		}

		public UserIntelClient build() {
			return new UserIntelClient(this);
		}
	}

	/**
	 * Look up breached users
	 * @pangea.description Determine if an email address, username, phone number, or IP address was exposed in a security breach.
	 * @pangea.operationId user_intel_post_v1_user_breached
	 * @param request to send to user/breached endpoint
	 * @return UserBreachedResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * UserBreachedRequest request = new UserBreachedRequest
	 * 	.Builder()
	 * 	.phoneNumber("8005550123")
	 * 	.build();
	 *
	 *	UserBreachedResponse response = client.breached(request);
	 * }
	 */
	public UserBreachedResponse breached(UserBreachedRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/user/breached", request, UserBreachedResponse.class);
	}

	/**
	 * Look up breached users V2
	 * @pangea.description Determine if an email address, username, phone number, or IP address was exposed in a security breach.
	 * @pangea.operationId user_intel_post_v2_user_breached
	 * @param request
	 * @return UserBreachedBulkResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * String[] phoneNumbers = {"8005550123"};
	 *
	 * UserBreachedBulkRequest request = new UserBreachedBulkRequest
	 * 	.Builder()
	 * 	.phoneNumbers(phoneNumbers)
	 * 	.build();
	 *
	 * UserBreachedBulkResponse response = client.breachedBulk(request);
	 * }
	 */
	public UserBreachedBulkResponse breachedBulk(UserBreachedBulkRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v2/user/breached", request, UserBreachedBulkResponse.class);
	}

	/**
	 * Look up breached passwords
	 * @pangea.description Determine if a password has been exposed in a security breach using a 5 character prefix of the password hash.
	 * @pangea.operationId user_intel_post_v1_password_breached
	 * @param request to send to password/breached endpoint
	 * @return UserPasswordBreachedResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * UserPasswordBreachedRequest request = new UserPasswordBreachedRequest
	 * 	.Builder(HashType.SHA256, "5baa6")
	 * 	.build();
	 *
	 *	UserPasswordBreachedResponse response = client.breached(request);
	 * }
	 */
	public UserPasswordBreachedResponse breached(UserPasswordBreachedRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v1/password/breached", request, UserPasswordBreachedResponse.class);
	}

	/**
	 * Look up breached passwords V2
	 * @pangea.description Determine if a password has been exposed in a security breach using a 5 character prefix of the password hash.
	 * @pangea.operationId user_intel_post_v2_password_breached
	 * @param request
	 * @return UserPasswordBreachedBulkResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 * String[] hashPrefixes = {"5baa6"};
	 *
	 * UserPasswordBreachedBulkRequest request = new UserPasswordBreachedBulkRequest
	 * 	.Builder(HashType.SHA256, hashPrefixes)
	 * 	.build();
	 *
	 * UserPasswordBreachedBulkResponse response = client.breachedBulk(request);
	 * }
	 */
	public UserPasswordBreachedBulkResponse breachedBulk(UserPasswordBreachedBulkRequest request)
		throws PangeaException, PangeaAPIException {
		return post("/v2/password/breached", request, UserPasswordBreachedBulkResponse.class);
	}

	/**
	 * Look up information about a specific breach
	 * @pangea.description Given a provider specific breach ID, find details about the breach.
	 * @pangea.operationId user_intel_post_v1_breach
	 * @param request
	 * @return BreachResponse
	 * @throws PangeaException
	 * @throws PangeaAPIException
	 * @pangea.code
	 * {@code
	 *
	 * BreachRequest request = new BreachRequest
	 * 	.Builder("66111")
	 * 	.build();
	 *
	 * BreachResponse response = client.breach(request);
	 * }
	 */
	public BreachResponse breached(BreachRequest request) throws PangeaException, PangeaAPIException {
		return post("/v1/breach", request, BreachResponse.class);
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
			// If it's not present, should check if I have all breached hashes
			// Server will return a maximum of 1000 hashes, so if breached count is greater than that,
			// I can't conclude a password is or is not breached
			return PasswordStatus.INCONCLUSIVE;
		} else {
			return PasswordStatus.UNBREACHED;
		}
	}
}
