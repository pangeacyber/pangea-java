package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.models.FlowChoice;
import cloud.pangeacyber.pangea.authn.models.FlowUpdateData;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowUpdateRequest extends BaseRequest {

	@JsonProperty("flow_id")
	String flowID;

	@JsonProperty("choice")
	FlowChoice choice;

	@JsonProperty("data")
	FlowUpdateData data;

	private FlowUpdateRequest(Builder builder) {
		this.flowID = builder.flowID;
		this.choice = builder.choice;
		this.data = builder.data;
	}

	public static class Builder {

		String flowID;
		FlowChoice choice;
		FlowUpdateData data;

		public Builder(String flowID, FlowChoice choice, FlowUpdateData data) {
			this.flowID = flowID;
			this.choice = choice;
			this.data = data;
		}

		public FlowUpdateRequest build() {
			return new FlowUpdateRequest(this);
		}
	}
}
