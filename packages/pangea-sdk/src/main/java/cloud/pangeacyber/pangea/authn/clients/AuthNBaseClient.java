package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.authn.AuthNClient;

public class AuthNBaseClient extends BaseClient {

	public static String serviceName = "authn";

	/** @hidden */
	public AuthNBaseClient(AuthNClient.Builder builder) {
		super(builder, serviceName);
	}
}
