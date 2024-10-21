package cloud.pangeacyber.pangea.authz.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authz.models.Resource;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class ListSubjectsRequest extends BaseRequest {

	@JsonProperty("resource")
	private Resource resource;

	@JsonProperty("action")
	private String action;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("attributes")
	private Map<String, Object> attributes;

	private ListSubjectsRequest(Builder builder) {
		this.resource = builder.resource;
		this.action = builder.action;
		this.attributes = builder.attributes;
	}

	public Resource getResource() {
		return resource;
	}

	public String getAction() {
		return action;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public static class Builder {

		private Resource resource;
		private String action;
		private Map<String, Object> attributes;

		public Builder(Resource resource, String action) {
			this.resource = resource;
			this.action = action;
		}

		public ListSubjectsRequest build() {
			return new ListSubjectsRequest(this);
		}

		public Builder setAttributes(Map<String, Object> attributes) {
			this.attributes = attributes;
			return this;
		}
	}
}
