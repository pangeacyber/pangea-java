package cloud.pangeacyber.pangea.authz.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Resource {

	@JsonProperty("namespace")
	private String namespace;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id")
	private String id;

	public Resource() {}

	private Resource(Builder builder) {
		this.namespace = builder.namespace;
		this.id = builder.id;
	}

	public String getNamespace() {
		return namespace;
	}

	public String getId() {
		return id;
	}

	public static class Builder {

		private String namespace;
		private String id;

		public Builder(String namespace) {
			this.namespace = namespace;
		}

		public Resource build() {
			return new Resource(this);
		}

		public Builder setId(String id) {
			this.id = id;
			return this;
		}
	}
}
