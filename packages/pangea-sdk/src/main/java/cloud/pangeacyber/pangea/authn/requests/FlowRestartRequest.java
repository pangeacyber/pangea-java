package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.models.FlowChoice;
import cloud.pangeacyber.pangea.authn.models.FlowRestartData;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowRestartRequest extends BaseRequest {

	@JsonProperty("flow_id")
	String flowID;

	@JsonProperty("choice")
	FlowChoice choice;

	@JsonProperty("data")
	FlowRestartData data;

	private FlowRestartRequest(Builder builder) {
		this.flowID = builder.flowID;
		this.choice = builder.choice;
		this.data = builder.data;
	}

	public static class Builder {

		String flowID;
		FlowChoice choice;
		FlowRestartData data;

		public Builder(String flowID, FlowChoice choice, FlowRestartData data) {
			this.flowID = flowID;
			this.choice = choice;
			this.data = data;
		}

		public FlowRestartRequest build() {
			return new FlowRestartRequest(this);
		}
	}
}
