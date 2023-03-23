package cloud.pangeacyber.pangea.authn.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowVerifyMFACompleteRequest {

	@JsonProperty("flow_id")
	String flowID;

	@JsonProperty("code")
	String code;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("cancel")
	Boolean cancel;

	private FlowVerifyMFACompleteRequest(FlowVerifyMFACompleteRequestBuilder builder) {
		this.flowID = builder.flowID;
		this.code = builder.code;
		this.cancel = builder.cancel;
	}

	public static class FlowVerifyMFACompleteRequestBuilder {

		String flowID;
		String code;
		Boolean cancel;

		public FlowVerifyMFACompleteRequestBuilder(String flowID, String code) {
			this.flowID = flowID;
			this.code = code;
		}

		public FlowVerifyMFACompleteRequest build() {
			return new FlowVerifyMFACompleteRequest(this);
		}

		public void setCancel(Boolean cancel) {
			this.cancel = cancel;
		}
	}
}
