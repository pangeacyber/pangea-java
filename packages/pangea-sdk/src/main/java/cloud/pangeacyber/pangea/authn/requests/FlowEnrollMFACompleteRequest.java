package cloud.pangeacyber.pangea.authn.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowEnrollMFACompleteRequest {
    @JsonProperty("flow_id")
    String flowID;

    @JsonProperty("code")
    String code;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("cancel")
    Boolean cancel;

    private FlowEnrollMFACompleteRequest(FlowEnrollMFACompleteRequestBuilder builder) {
        this.flowID = builder.flowID;
        this.code = builder.code;
        this.cancel = builder.cancel;
	}

	public static class FlowEnrollMFACompleteRequestBuilder{
        String flowID;
        String code;
        Boolean cancel;

		public FlowEnrollMFACompleteRequestBuilder(String flowID, String code) {
            this.flowID = flowID;
            this.code = code;
        }

        public FlowEnrollMFACompleteRequest build(){
			return new FlowEnrollMFACompleteRequest(this);
		}

        public void setCancel(Boolean cancel) {
            this.cancel = cancel;
        }
	}
}
