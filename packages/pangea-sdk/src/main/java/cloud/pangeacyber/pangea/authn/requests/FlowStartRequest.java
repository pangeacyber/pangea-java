package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.authn.models.FlowType;
import cloud.pangeacyber.pangea.authn.models.IDProvider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowStartRequest {

	@JsonProperty("cb_uri")
	String cbURI;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("email")
	String email;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("flow_types")
	FlowType flowType;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("provider")
	IDProvider provider;

	private FlowStartRequest(FlowStartRequestBuilder builder) {
		this.email = builder.email;
		this.cbURI = builder.cbURI;
		this.flowType = builder.flowType;
		this.provider = builder.provider;
	}

	public static class FlowStartRequestBuilder {

		String cbURI;
		String email;
		FlowType flowType;
		IDProvider provider;

		public FlowStartRequestBuilder(String cbURI) {
			this.cbURI = cbURI;
		}

		public FlowStartRequest build() {
			return new FlowStartRequest(this);
		}

		public FlowStartRequestBuilder setEmail(String email) {
			this.email = email;
			return this;
		}

		public FlowStartRequestBuilder setFlowType(FlowType flowType) {
			this.flowType = flowType;
			return this;
		}

		public FlowStartRequestBuilder setProvider(IDProvider provider) {
			this.provider = provider;
			return this;
		}
	}
}
