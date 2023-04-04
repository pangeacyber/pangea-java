package cloud.pangeacyber.pangea.intel.models;

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

	protected UserBreachedRequest(UserBreachedRequestBuilder builder) {
		super(builder);
		this.email = builder.email;
		this.username = builder.username;
		this.ip = builder.ip;
		this.phoneNumber = builder.phoneNumber;
		this.start = builder.start;
		this.end = builder.end;
	}

	public static class UserBreachedRequestBuilder extends IntelCommonRequestBuilder<UserBreachedRequestBuilder> {

		String email;
		String username;
		String ip;
		String phoneNumber;
		String start;
		String end;

		public UserBreachedRequestBuilder() {}

		public UserBreachedRequest build() {
			return new UserBreachedRequest(this);
		}

		public UserBreachedRequestBuilder setEmail(String email) {
			this.email = email;
			return this;
		}

		public UserBreachedRequestBuilder setUsername(String username) {
			this.username = username;
			return this;
		}

		public UserBreachedRequestBuilder setIp(String ip) {
			this.ip = ip;
			return this;
		}

		public UserBreachedRequestBuilder setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public UserBreachedRequestBuilder setStart(String start) {
			this.start = start;
			return this;
		}

		public UserBreachedRequestBuilder setEnd(String end) {
			this.end = end;
			return this;
		}
	}
}
