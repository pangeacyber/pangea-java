package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowUpdateDataSMSOTP extends FlowUpdateData {

	@JsonProperty("code")
	private final String code;

	private FlowUpdateDataSMSOTP(Builder builder) {
		this.code = builder.code;
	}

	public String getCode() {
		return code;
	}

	public static class Builder {

		private final String code;

		public Builder(String code) {
			this.code = code;
		}

		public FlowUpdateDataSMSOTP build() {
			return new FlowUpdateDataSMSOTP(this);
		}
	}
}
