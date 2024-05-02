package cloud.pangeacyber.pangea.authz.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Resource {

	@JsonProperty("type")
	private String type;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id")
	private String id;

	public Resource() {}

	private Resource(Builder builder) {
		this.type = builder.type;
		this.id = builder.id;
	}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public static class Builder {

		private String type;
		private String id;

		public Builder(String type) {
			this.type = type;
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
