package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.authn.AuthNClient;

public class FlowEnroll extends AuthNBaseClient {

	private FlowEnrollMFA mfa;

	public FlowEnroll(AuthNClient.Builder builder) {
		super(builder);
		mfa = new FlowEnrollMFA(builder);
	}

	public FlowEnrollMFA mfa() {
		return mfa;
	}
}
