package cloud.pangeacyber.pangea.authz.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authz.models.Subject;

public class ListResourcesRequest extends BaseRequest {

	private String type;
	private String action;
	private Subject subject;

	private ListResourcesRequest(Builder builder) {
		this.type = builder.type;
		this.action = builder.action;
		this.subject = builder.subject;
	}

	public String getType() {
		return type;
	}

	public String getAction() {
		return action;
	}

	public Subject getSubject() {
		return subject;
	}

	public static class Builder {

		private String type;
		private String action;
		private Subject subject;

		public Builder(String type, String action, Subject subject) {
			this.type = type;
			this.action = action;
			this.subject = subject;
		}

		public ListResourcesRequest build() {
			return new ListResourcesRequest(this);
		}
	}
}
