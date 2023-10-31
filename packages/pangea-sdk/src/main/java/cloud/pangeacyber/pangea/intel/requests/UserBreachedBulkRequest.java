package cloud.pangeacyber.pangea.intel.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserBreachedBulkRequest extends IntelCommonRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("emails")
	String[] emails;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("usernames")
	String[] usernames;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("ips")
	String[] ips;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("phone_numbers")
	String[] phoneNumbers;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("start")
	String start;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("end")
	String end;

	protected UserBreachedBulkRequest(Builder builder) {
		super(builder);
		this.emails = builder.emails;
		this.usernames = builder.usernames;
		this.ips = builder.ips;
		this.phoneNumbers = builder.phoneNumbers;
		this.start = builder.start;
		this.end = builder.end;
	}

	public static class Builder extends CommonBuilder<Builder> {

		String[] emails;
		String[] usernames;
		String[] ips;
		String[] phoneNumbers;
		String start;
		String end;

		public Builder() {}

		public UserBreachedBulkRequest build() {
			return new UserBreachedBulkRequest(this);
		}

		public Builder emails(String[] emails) {
			this.emails = emails;
			return this;
		}

		public Builder usernames(String[] usernames) {
			this.usernames = usernames;
			return this;
		}

		public Builder ips(String[] ips) {
			this.ips = ips;
			return this;
		}

		public Builder phoneNumbers(String[] phoneNumbers) {
			this.phoneNumbers = phoneNumbers;
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
	}
}
