package cloud.pangeacyber.pangea.authz.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Subject {

	@JsonProperty("type")
	private String type;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id")
	private String id;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("action")
	private String action;

	public Subject() {}

	private Subject(Builder builder) {
		this.type = builder.type;
		this.id = builder.id;
		this.action = builder.action;
	}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public String getAction() {
		return action;
	}

	public static class Builder {

		private String type;
		private String id;
		private String action;

		public Builder(String type) {
			this.type = type;
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
