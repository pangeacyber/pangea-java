package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowVerifySocialRequest extends BaseRequest {

	@JsonProperty("flow_id")
	private String flowID;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("cb_state")
	private String cbState;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("cb_code")
	private String cbCode;

	private FlowVerifySocialRequest(Builder builder) {
		this.flowID = builder.flowID;
		this.cbState = builder.cbState;
		this.cbCode = builder.cbCode;
	}

	public String getFlowID() {
		return flowID;
	}

	public String getCBState() {
		return cbState;
	}

	public String getCBCode() {
		return cbCode;
	}

	public static class Builder {

		private String flowID;
		private String cbState;
		private String cbCode;

		public Builder(String flowID) {
			this.flowID = flowID;
		}

		public Builder withCBState(String cbState) {
			this.cbState = cbState;
			return this;
		}

		public Builder withCBCode(String cbCode) {
			this.cbCode = cbCode;
			return this;
		}

		public FlowVerifySocialRequest build() {
			return new FlowVerifySocialRequest(this);
		}
	}
}
