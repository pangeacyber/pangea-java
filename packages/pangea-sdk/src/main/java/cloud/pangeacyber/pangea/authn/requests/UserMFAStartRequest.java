package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.models.MFAProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMFAStartRequest extends BaseRequest {

	@JsonProperty("user_id")
	String userID;

	@JsonProperty("mfa_provider")
	MFAProvider mfaProvider;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("enroll")
	Boolean enroll;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("phone")
	String phone;

	private UserMFAStartRequest(Builder builder) {
		this.userID = builder.userID;
		this.mfaProvider = builder.mfaProvider;
		this.enroll = builder.enroll;
		this.phone = builder.phone;
	}

	public static class Builder {

		String userID;
		MFAProvider mfaProvider;
		Boolean enroll;
		String phone;

		public Builder(String userID, MFAProvider mfaProvider) {
			this.userID = userID;
			this.mfaProvider = mfaProvider;
		}

		public UserMFAStartRequest build() {
			return new UserMFAStartRequest(this);
		}

		public Builder setEnroll(Boolean enroll) {
			this.enroll = enroll;
			return this;
		}

		public Builder setPhone(String phone) {
			this.phone = phone;
			return this;
		}
	}
}
