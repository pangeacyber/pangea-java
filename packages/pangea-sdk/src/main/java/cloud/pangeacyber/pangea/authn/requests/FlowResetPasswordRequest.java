package cloud.pangeacyber.pangea.authn.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowResetPasswordRequest {
    @JsonProperty("flow_id")
    String flowID;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("password")
    String password;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("cb_state")
    String cbState;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("cb_code")
    String cbCode;

    private FlowResetPasswordRequest(FlowResetPasswordRequestBuilder builder) {
        this.password = builder.password;
        this.flowID = builder.flowID;
        this.cbState = builder.cbState;
        this.cbCode = builder.cbCode;
	}

	public static class FlowResetPasswordRequestBuilder{
        String flowID;
        String password;
        String cbState;
        String cbCode;

		public FlowResetPasswordRequestBuilder(String flowID, String password) {
            this.flowID = flowID;
            this.password = password;
        }

		public FlowResetPasswordRequest build(){
			return new FlowResetPasswordRequest(this);
		}

        public FlowResetPasswordRequestBuilder setCBState(String cbState) {
            this.cbState = cbState;
            return this;
        }

        public FlowResetPasswordRequestBuilder setCBCode(String cbCode) {
            this.cbCode = cbCode;
            return this;
        }

	}

}
