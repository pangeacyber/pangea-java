package cloud.pangeacyber.pangea.authn;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.clients.ClientEndpoint;
import cloud.pangeacyber.pangea.authn.clients.Flow;
import cloud.pangeacyber.pangea.authn.clients.User;

public class AuthNClient extends Client {

	public static final String serviceName = "authn";
	private static final boolean supportMultiConfig = false;
	private User user;
	private Flow flow;
	private ClientEndpoint client;

	public AuthNClient(Config config) {
		super(config, serviceName, supportMultiConfig);
		user = new User(config);
		flow = new Flow(config);
		client = new ClientEndpoint(config);
	}

	public User user() {
		return user;
	}

	public Flow flow() {
		return flow;
	}

	public ClientEndpoint client() {
		return client;
	}
}
