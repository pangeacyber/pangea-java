package cloud.pangeacyber.pangea.authz.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authz.models.Resource;

public class ListSubjectsRequest extends BaseRequest {

	private Resource resource;
	private String action;

	private ListSubjectsRequest(Builder builder) {
		this.resource = builder.resource;
		this.action = builder.action;
	}

	public Resource getResource() {
		return resource;
	}

	public String getAction() {
		return action;
	}

	public static class Builder {

		private Resource resource;
		private String action;

		public Builder(Resource resource, String action) {
			this.resource = resource;
			this.action = action;
		}

		public ListSubjectsRequest build() {
			return new ListSubjectsRequest(this);
		}
	}
}
