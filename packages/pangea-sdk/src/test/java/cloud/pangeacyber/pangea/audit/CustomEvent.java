package cloud.pangeacyber.pangea.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomEvent implements IEvent {

	@JsonProperty("message")
	private String message;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("field_int")
	private Integer fieldInt;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("field_bool")
	private Boolean fieldBool;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("field_str_short")
	private String fieldStrShort;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("field_str_long")
	private String fieldStrLong;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("field_time")
	private String fieldTime;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("tenant_id")
	private String tenantID;

	private CustomEvent(Builder builder) {
		this.message = builder.message;
		this.fieldInt = builder.fieldInt;
		this.fieldBool = builder.fieldBool;
		this.fieldStrShort = builder.fieldStrShort;
		this.fieldStrLong = builder.fieldStrLong;
		this.fieldTime = builder.fieldTime;
		this.tenantID = builder.tenantID;
	}

	public CustomEvent() {} //Needed for Jackson library

	public String getMessage() {
		return message;
	}

	public Integer getFieldInt() {
		return fieldInt;
	}

	public Boolean getFieldBool() {
		return fieldBool;
	}

	public String getFieldStrShort() {
		return fieldStrShort;
	}

	public String getFieldStrLong() {
		return fieldStrLong;
	}

	public String getFieldTime() {
		return fieldTime;
	}

	public String getTenantID() {
		return tenantID;
	}

	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	public static class Builder {

		private String message;
		private Integer fieldInt;
		private Boolean fieldBool;
		private String fieldStrShort;
		private String fieldStrLong;
		private String fieldTime;
		private String tenantID;

		public Builder(String message) {
			this.message = message;
		}

		public Builder fieldInt(Integer fieldInt) {
			this.fieldInt = fieldInt;
			return this;
		}

		public Builder fieldBool(Boolean fieldBool) {
			this.fieldBool = fieldBool;
			return this;
		}

		public Builder fieldStrShort(String fieldStrShort) {
			this.fieldStrShort = fieldStrShort;
			return this;
		}

		public Builder fieldStrLong(String fieldStrLong) {
			this.fieldStrLong = fieldStrLong;
			return this;
		}

		public Builder fieldTime(String fieldTime) {
			this.fieldTime = fieldTime;
			return this;
		}

		public Builder tenantID(String tenantID) {
			this.tenantID = tenantID;
			return this;
		}

		public CustomEvent build() {
			return new CustomEvent(this);
		}
	}
}
