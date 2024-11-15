package cloud.pangeacyber.pangea.intel.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserBreachedRequest extends IntelCommonRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("email")
	String email;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("username")
	String username;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("ip")
	String ip;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("phone_number")
	String phoneNumber;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("start")
	String start;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("end")
	String end;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("cursor")
	String cursor;

	protected UserBreachedRequest(Builder builder) {
		super(builder);
		this.email = builder.email;
		this.username = builder.username;
		this.ip = builder.ip;
		this.phoneNumber = builder.phoneNumber;
		this.start = builder.start;
		this.end = builder.end;
		this.cursor = builder.cursor;
	}

	public static class Builder extends CommonBuilder<Builder> {

		String email;
		String username;
		String ip;
		String phoneNumber;
		String start;
		String end;
		String cursor;

		public Builder() {}

		public UserBreachedRequest build() {
			return new UserBreachedRequest(this);
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder username(String username) {
			this.username = username;
			return this;
		}

		public Builder ip(String ip) {
			this.ip = ip;
			return this;
		}

		public Builder phoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public Builder start(String start) {
			this.start = start;
			return this;
		}

		public Builder end(String end) {
			this.end = end;
			return this;
		}

		public Builder cursor(String cursor) {
			this.cursor = cursor;
			return this;
		}
	}
}
