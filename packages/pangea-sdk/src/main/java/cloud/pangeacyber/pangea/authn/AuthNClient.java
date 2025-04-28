package cloud.pangeacyber.pangea.authn;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.clients.Agreements;
import cloud.pangeacyber.pangea.authn.clients.AuthNBaseClient;
import cloud.pangeacyber.pangea.authn.clients.Client;
import cloud.pangeacyber.pangea.authn.clients.Flow;
import cloud.pangeacyber.pangea.authn.clients.Group;
import cloud.pangeacyber.pangea.authn.clients.Session;
import cloud.pangeacyber.pangea.authn.clients.User;

public class AuthNClient extends AuthNBaseClient {

	private Agreements agreements;
	private Client client;
	private Flow flow;
	private Group group;
	private Session session;
	private User user;

	public AuthNClient(final Builder builder) {
		super(builder);
		this.agreements = new Agreements(builder);
		this.client = new Client(builder);
		this.flow = new Flow(builder);
		this.group = new Group(builder);
		this.session = new Session(builder);
		this.user = new User(builder);
	}

	public static class Builder extends AuthNBaseClient.Builder<Builder> {

		public Builder(Config config) {
			super(config);
		}

		public AuthNClient build() {
			return new AuthNClient(this);
		}
	}

	public Agreements agreements() {
		return this.agreements;
	}

	public Client client() {
		return this.client;
	}

	public Flow flow() {
		return this.flow;
	}

	public Group group() {
		return this.group;
	}

	public Session session() {
		return this.session;
	}

	public User user() {
		return this.user;
	}
}
