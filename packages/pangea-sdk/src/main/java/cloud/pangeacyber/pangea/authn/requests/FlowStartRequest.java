package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.models.FlowType;
import cloud.pangeacyber.pangea.authn.models.IDProvider;
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
	@JsonProperty("provider")
	IDProvider provider;

	private FlowStartRequest(Builder builder) {
		this.cbURI = builder.cbURI;
		this.email = builder.email;
		this.flowType = builder.flowType;
		this.provider = builder.provider;
	}

	public static class Builder {

		private String cbURI;
		private String email;
		private FlowType[] flowType;
		private IDProvider provider;

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

		public Builder setProvider(IDProvider provider) {
			this.provider = provider;
			return this;
		}

		public FlowStartRequest build() {
			return new FlowStartRequest(this);
		}
	}
}
