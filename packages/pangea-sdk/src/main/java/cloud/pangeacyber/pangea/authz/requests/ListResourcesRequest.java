package cloud.pangeacyber.pangea.authz.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authz.models.Subject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class ListResourcesRequest extends BaseRequest {

	@JsonProperty("type")
	private String type;

	@JsonProperty("action")
	private String action;

	@JsonProperty("subject")
	private Subject subject;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("attributes")
	private Map<String, Object> attributes;

	private ListResourcesRequest(Builder builder) {
		this.type = builder.type;
		this.action = builder.action;
		this.subject = builder.subject;
		this.attributes = builder.attributes;
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

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public static class Builder {

		private String type;
		private String action;
		private Subject subject;
		private Map<String, Object> attributes;

		public Builder(String type, String action, Subject subject) {
			this.type = type;
			this.action = action;
			this.subject = subject;
		}

		public ListResourcesRequest build() {
			return new ListResourcesRequest(this);
		}

		public Builder setAttributes(Map<String, Object> attributes) {
			this.attributes = attributes;
			return this;
		}
	}
}
