package cloud.pangeacyber.pangea.authn;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.clients.ClientEndpoint;
import cloud.pangeacyber.pangea.authn.clients.Flow;
import cloud.pangeacyber.pangea.authn.clients.Password;
import cloud.pangeacyber.pangea.authn.clients.User;

public class AuthNClient extends Client {

	public static final String serviceName = "authn";
	private User user;
	private Password password;
	private Flow flow;
	private ClientEndpoint client;

	public AuthNClient(Config config) {
		super(config, serviceName);
		user = new User(config);
		password = new Password(config);
		flow = new Flow(config);
		client = new ClientEndpoint(config);
	}

	public User user() {
		return user;
	}

	public Password password() {
		return password;
	}

	public Flow flow() {
		return flow;
	}

	public ClientEndpoint client() {
		return client;
	}
}
