package cloud.pangeacyber.pangea.authn;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.clients.Client;
import cloud.pangeacyber.pangea.authn.clients.Flow;
import cloud.pangeacyber.pangea.authn.clients.User;

public class AuthNClient extends BaseClient {
	private User user;
	private Flow flow;
	private Client client;

	public AuthNClient(Builder builder) {
		super(builder);
		user = new User(builder);
		flow = new Flow(builder);
		client = new Client(builder);
	}

	public static class Builder extends BaseClient.Builder<Builder> {
		public Builder(Config config) {
			super(config, "authn");
		}

		public AuthNClient build() {
			return new AuthNClient(this);
		}
	}

	public User user() {
		return user;
	}

	public Flow flow() {
		return flow;
	}

	public Client client() {
		return client;
	}
}
