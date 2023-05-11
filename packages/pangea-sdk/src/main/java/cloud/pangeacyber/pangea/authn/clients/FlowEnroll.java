package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.BaseClient;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.authn.AuthNClient;

public class FlowEnroll extends BaseClient {

	private FlowEnrollMFA mfa;

	public FlowEnroll(AuthNClient.Builder builder) {
		super(builder);
		mfa = new FlowEnrollMFA(builder);
	}

	public FlowEnrollMFA mfa() {
		return mfa;
	}
}
