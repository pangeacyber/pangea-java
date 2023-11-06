package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowUpdateDataProvisionalEnrollment extends FlowUpdateData {

	@JsonProperty("state")
	private final String state;

	@JsonProperty("code")
	private final String code;

	private FlowUpdateDataProvisionalEnrollment(Builder builder) {
		this.state = builder.state;
		this.code = builder.code;
	}

	public String getState() {
		return state;
	}

	public String getCode() {
		return code;
	}

	public static class Builder {

		private final String state;
		private final String code;

		public Builder(String state, String code) {
			this.state = state;
			this.code = code;
		}

		public FlowUpdateDataProvisionalEnrollment build() {
			return new FlowUpdateDataProvisionalEnrollment(this);
		}
	}
}
