package cloud.pangeacyber.pangea.authn.clients;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;

public class FlowEnroll extends Client {

	public static final String serviceName = "authn";
	private FlowEnrollMFA mfa;

	public FlowEnroll(Config config) {
		super(config, serviceName);
		mfa = new FlowEnrollMFA(config);
	}

	public FlowEnrollMFA mfa() {
		return mfa;
	}
}
