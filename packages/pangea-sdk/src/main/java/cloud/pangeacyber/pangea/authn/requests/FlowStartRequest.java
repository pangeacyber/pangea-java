package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.models.FlowType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowStartRequest extends BaseRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("cb_uri")
	String cbURI;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("email")
	String email;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("flow_types")
	FlowType[] flowType;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("invitation")
	String invitation;

	private FlowStartRequest(Builder builder) {
		this.cbURI = builder.cbURI;
		this.email = builder.email;
		this.flowType = builder.flowType;
		this.invitation = builder.invitation;
	}

	public static class Builder {

		private String cbURI;
		private String email;
		private FlowType[] flowType;
		private String invitation;

		public Builder() {}

		public Builder setCbURI(String cbURI) {
			this.cbURI = cbURI;
			return this;
		}

		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}

		public Builder setFlowType(FlowType[] flowType) {
			this.flowType = flowType;
			return this;
		}

		public Builder setInvitation(String invitation) {
			this.invitation = invitation;
			return this;
		}

		public FlowStartRequest build() {
			return new FlowStartRequest(this);
		}
	}
}
