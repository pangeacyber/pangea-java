package cloud.pangeacyber.pangea.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("actor")
	String actor = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("action")
	String action = null;

	@JsonProperty("message")
	String message;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("new")
	String newField = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("old")
	String old = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("source")
	String source = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("status")
	String status = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("target")
	String target = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("timestamp")
	String timestamp = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("tenant_id")
	String tenantID = null;

	public Event() {}

	public Event(String message) {
		this.message = message;
	}

	public Event(
		String actor,
		String action,
		String message,
		String newField,
		String old,
		String source,
		String status,
		String target,
		String timestamp
	) {
		this.actor = actor;
		this.action = action;
		this.message = message;
		this.newField = newField;
		this.old = old;
		this.source = source;
		this.status = status;
		this.target = target;
		this.timestamp = timestamp;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNewField() {
		return newField;
	}

	public void setNewField(String newField) {
		this.newField = newField;
	}

	public String getOld() {
		return old;
	}

	public void setOld(String old) {
		this.old = old;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public static String canonicalize(Event event) throws JsonProcessingException {
		ObjectMapper mapper = JsonMapper
			.builder()
			.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
			.build();
		return mapper.writeValueAsString(event);
	}

	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	public String getTenantID() {
		return tenantID;
	}
}
