package cloud.pangeacyber.pangea.audit.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchRestriction {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("actor")
	private String[] actor;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("source")
	private String[] source;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("target")
	private String[] target;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("status")
	private String[] status;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("action")
	private String[] action;

	private SearchRestriction(Builder builder) {
		this.actor = builder.actor;
		this.source = builder.source;
		this.target = builder.target;
		this.status = builder.status;
		this.action = builder.action;
	}

	public static class Builder {

		private String[] actor;
		private String[] source;
		private String[] target;
		private String[] status;
		private String[] action;

		public Builder() {}

		public Builder actor(String[] actor) {
			this.actor = actor;
			return this;
		}

		public Builder source(String[] source) {
			this.source = source;
			return this;
		}

		public Builder target(String[] target) {
			this.target = target;
			return this;
		}

		public Builder status(String[] status) {
			this.status = status;
			return this;
		}

		public Builder action(String[] action) {
			this.action = action;
			return this;
		}

		public SearchRestriction build() {
			return new SearchRestriction(this);
		}
	}

	public String[] getActor() {
		return actor;
	}

	public String[] getSource() {
		return source;
	}

	public String[] getTarget() {
		return target;
	}

	public String[] getStatus() {
		return status;
	}

	public String[] getAction() {
		return action;
	}
}
