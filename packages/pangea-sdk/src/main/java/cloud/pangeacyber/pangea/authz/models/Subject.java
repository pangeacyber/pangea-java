package cloud.pangeacyber.pangea.authz.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Subject {

	@JsonProperty("namespace")
	private String namespace;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id")
	private String id;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("action")
	private String action;

	public Subject() {}

	private Subject(Builder builder) {
		this.namespace = builder.namespace;
		this.id = builder.id;
		this.action = builder.action;
	}

	public String getNamespace() {
		return namespace;
	}

	public String getId() {
		return id;
	}

	public String getAction() {
		return action;
	}

	public static class Builder {

		private String namespace;
		private String id;
		private String action;

		public Builder(String namespace) {
			this.namespace = namespace;
		}

		public Subject build() {
			return new Subject(this);
		}

		public Builder setId(String id) {
			this.id = id;
			return this;
		}

		public Builder setAction(String action) {
			this.action = action;
			return this;
		}
	}
}
