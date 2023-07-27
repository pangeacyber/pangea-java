package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowResetPasswordRequest extends BaseRequest {

	@JsonProperty("flow_id")
	String flowID;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("password")
	String password;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("cancel")
	Boolean cancel;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("cb_state")
	String cbState;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("cb_code")
	String cbCode;

	private FlowResetPasswordRequest(Builder builder) {
		this.password = builder.password;
		this.flowID = builder.flowID;
		this.cbState = builder.cbState;
		this.cbCode = builder.cbCode;
		this.cancel = builder.cancel;
	}

	public static class Builder {

		String flowID;
		String password;
		String cbState;
		String cbCode;
		Boolean cancel;

		public Builder(String flowID, String password) {
			this.flowID = flowID;
			this.password = password;
		}

		public FlowResetPasswordRequest build() {
			return new FlowResetPasswordRequest(this);
		}

		public Builder setCBState(String cbState) {
			this.cbState = cbState;
			return this;
		}

		public Builder setCBCode(String cbCode) {
			this.cbCode = cbCode;
			return this;
		}

		public Builder setCancel(Boolean cancel) {
			this.cancel = cancel;
			return this;
		}
	}
}
