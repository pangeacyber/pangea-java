package cloud.pangeacyber.pangea.audit.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StandardEvent implements IEvent {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("actor")
	private String actor;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("action")
	private String action;

	@JsonProperty("message")
	private String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("new")
	private String newField;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("old")
	private String old;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("source")
	private String source;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("status")
	private String status;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("target")
	private String target;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("timestamp")
	private String timestamp;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("tenant_id")
	private String tenantID;

	public StandardEvent() {}

	public StandardEvent(String message) {
		this.message = message;
	}

	private StandardEvent(Builder builder) {
		this.actor = builder.actor;
		this.action = builder.action;
		this.message = builder.message;
		this.newField = builder.newField;
		this.old = builder.old;
		this.source = builder.source;
		this.status = builder.status;
		this.target = builder.target;
		this.timestamp = builder.timestamp;
		this.tenantID = builder.tenantID;
	}

	public static class Builder {

		private String actor;
		private String action;
		private String message;
		private String newField;
		private String old;
		private String source;
		private String status;
		private String target;
		private String timestamp;
		private String tenantID;

		public Builder(String message) {
			this.message = message;
		}

		public Builder actor(String actor) {
			this.actor = actor;
			return this;
		}

		public Builder action(String action) {
			this.action = action;
			return this;
		}

		public Builder newField(String newField) {
			this.newField = newField;
			return this;
		}

		public Builder old(String old) {
			this.old = old;
			return this;
		}

		public Builder source(String source) {
			this.source = source;
			return this;
		}

		public Builder status(String status) {
			this.status = status;
			return this;
		}

		public Builder target(String target) {
			this.target = target;
			return this;
		}

		public Builder timestamp(String timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public Builder tenantID(String tenantID) {
			this.tenantID = tenantID;
			return this;
		}

		public StandardEvent build() {
			return new StandardEvent(this);
		}
	}

	public String getActor() {
		return actor;
	}

	public String getAction() {
		return action;
	}

	public String getMessage() {
		return message;
	}

	public String getNewField() {
		return newField;
	}

	public String getOld() {
		return old;
	}

	public String getSource() {
		return source;
	}

	public String getStatus() {
		return status;
	}

	public String getTarget() {
		return target;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getTenantID() {
		return tenantID;
	}

	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setNewField(String newField) {
		this.newField = newField;
	}

	public void setOld(String old) {
		this.old = old;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
