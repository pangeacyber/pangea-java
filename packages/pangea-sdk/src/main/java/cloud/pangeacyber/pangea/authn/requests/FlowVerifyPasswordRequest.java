package cloud.pangeacyber.pangea.authn.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowVerifyPasswordRequest {

	@JsonProperty("flow_id")
	String flowID;

	@JsonProperty("password")
	String password;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("cancel")
	Boolean cancel;

	private FlowVerifyPasswordRequest(FlowVerifyPasswordRequestBuilder builder) {
		this.flowID = builder.flowID;
		this.password = builder.password;
		this.cancel = builder.cancel;
	}

	public static class FlowVerifyPasswordRequestBuilder {

		String flowID;
		String password;
		Boolean cancel;

		public FlowVerifyPasswordRequestBuilder(String flowID, String password) {
			this.flowID = flowID;
			this.password = password;
		}

		public FlowVerifyPasswordRequest build() {
			return new FlowVerifyPasswordRequest(this);
		}

		public void setCancel(Boolean cancel) {
			this.cancel = cancel;
		}
	}
}
