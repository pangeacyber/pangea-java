package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowUpdateDataResetPassword extends FlowUpdateData {

	@JsonProperty("state")
	private final String state;

	@JsonProperty("code")
	private final String code;

	private FlowUpdateDataResetPassword(Builder builder) {
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

		public FlowUpdateDataResetPassword build() {
			return new FlowUpdateDataResetPassword(this);
		}
	}
}
