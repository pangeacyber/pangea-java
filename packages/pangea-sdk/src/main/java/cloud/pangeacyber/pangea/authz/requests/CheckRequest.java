package cloud.pangeacyber.pangea.authz.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authz.models.Resource;
import cloud.pangeacyber.pangea.authz.models.Subject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class CheckRequest extends BaseRequest {

	@JsonProperty("resource")
	private Resource resource;

	@JsonProperty("action")
	private String action;

	@JsonProperty("subject")
	private Subject subject;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("debug")
	private Boolean debug;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("attributes")
	private Map<String, Object> attributes;

	private CheckRequest(Builder builder) {
		this.resource = builder.resource;
		this.action = builder.action;
		this.subject = builder.subject;
		this.debug = builder.debug;
		this.attributes = builder.attributes;
	}

	public Resource getResource() {
		return resource;
	}

	public String getAction() {
		return action;
	}

	public Subject getSubject() {
		return subject;
	}

	public Boolean getDebug() {
		return debug;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public static class Builder {

		private Resource resource;
		private String action;
		private Subject subject;
		private Boolean debug;
		private Map<String, Object> attributes;

		public Builder() {}

		public Builder setResource(Resource resource) {
			this.resource = resource;
			return this;
		}

		public Builder setAction(String action) {
			this.action = action;
			return this;
		}

		public Builder setSubject(Subject subject) {
			this.subject = subject;
			return this;
		}

		public Builder setDebug(Boolean debug) {
			this.debug = debug;
			return this;
		}

		public Builder setAttributes(Map<String, Object> attributes) {
			this.attributes = attributes;
			return this;
		}

		public CheckRequest build() {
			return new CheckRequest(this);
		}
	}
}
