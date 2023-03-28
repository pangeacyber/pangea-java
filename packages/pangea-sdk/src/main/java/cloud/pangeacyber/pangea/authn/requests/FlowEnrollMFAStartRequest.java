package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.authn.models.MFAProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowEnrollMFAStartRequest {

	@JsonProperty("flow_id")
	String flowID;

	@JsonProperty("mfa_provider")
	MFAProvider mfaProvider;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("phone")
	String phone;

	private FlowEnrollMFAStartRequest(FlowEnrollMFAStartRequestBuilder builder) {
		this.flowID = builder.flowID;
		this.mfaProvider = builder.mfaProvider;
		this.phone = builder.phone;
	}

	public static class FlowEnrollMFAStartRequestBuilder {

		String flowID;
		MFAProvider mfaProvider;
		String phone;

		public FlowEnrollMFAStartRequestBuilder(String flowID, MFAProvider mfaProvider) {
			this.flowID = flowID;
			this.mfaProvider = mfaProvider;
		}

		public FlowEnrollMFAStartRequest build() {
			return new FlowEnrollMFAStartRequest(this);
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}
	}
}
