package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowUpdateDataCaptcha extends FlowUpdateData {

	@JsonProperty("code")
	private String code;

	private FlowUpdateDataCaptcha(Builder builder) {
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

		public FlowUpdateDataCaptcha build() {
			return new FlowUpdateDataCaptcha(this);
		}
	}
}
