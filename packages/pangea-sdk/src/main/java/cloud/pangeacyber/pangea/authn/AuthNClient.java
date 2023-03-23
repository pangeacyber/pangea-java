package cloud.pangeacyber.pangea.authn;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.clients.Flow;
import cloud.pangeacyber.pangea.authn.clients.Password;
import cloud.pangeacyber.pangea.authn.clients.User;

public class AuthNClient extends Client {

	public static final String serviceName = "authn";
	User user;
	Password password;
	Flow flow;

	public AuthNClient(Config config) {
		super(config, serviceName);
		user = new User(config);
		password = new Password(config);
		flow = new Flow(config);
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
}
