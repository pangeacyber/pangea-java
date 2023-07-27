package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowVerifyEmailRequest extends BaseRequest {

	@JsonProperty("flow_id")
	private String flowID;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("cb_state")
	private String cbState;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("cb_code")
	private String cbCode;

	private FlowVerifyEmailRequest(Builder builder) {
		this.flowID = builder.flowID;
		this.cbState = builder.cbState;
		this.cbCode = builder.cbCode;
	}

	public static class Builder {

		private String flowID;
		private String cbState;
		private String cbCode;

		public Builder(String flowID) {
			this.flowID = flowID;
		}

		public Builder setCbState(String cbState) {
			this.cbState = cbState;
			return this;
		}

		public Builder setCbCode(String cbCode) {
			this.cbCode = cbCode;
			return this;
		}

		public FlowVerifyEmailRequest build() {
			return new FlowVerifyEmailRequest(this);
		}
	}
}
