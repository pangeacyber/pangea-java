package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowUpdateDataEmailOTP extends FlowUpdateData {

	@JsonProperty("code")
	private String code;

	private FlowUpdateDataEmailOTP(Builder builder) {
		this.code = builder.code;
	}

	public String getCode() {
		return code;
	}

	public static class Builder {

		private String code;

		public Builder(String code) {
			this.code = code;
		}

		public FlowUpdateDataEmailOTP build() {
			return new FlowUpdateDataEmailOTP(this);
		}
	}
}
