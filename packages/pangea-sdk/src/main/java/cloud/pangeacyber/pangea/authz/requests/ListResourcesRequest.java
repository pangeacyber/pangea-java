package cloud.pangeacyber.pangea.authz.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authz.models.Subject;

public class ListResourcesRequest extends BaseRequest {

	private String namespace;
	private String action;
	private Subject subject;

	private ListResourcesRequest(Builder builder) {
		this.namespace = builder.namespace;
		this.action = builder.action;
		this.subject = builder.subject;
	}

	public String getNamespace() {
		return namespace;
	}

	public String getAction() {
		return action;
	}

	public Subject getSubject() {
		return subject;
	}

	public static class Builder {

		private String namespace;
		private String action;
		private Subject subject;

		public Builder(String namespace, String action, Subject subject) {
			this.namespace = namespace;
			this.action = action;
			this.subject = subject;
		}

		public ListResourcesRequest build() {
			return new ListResourcesRequest(this);
		}
	}
}
