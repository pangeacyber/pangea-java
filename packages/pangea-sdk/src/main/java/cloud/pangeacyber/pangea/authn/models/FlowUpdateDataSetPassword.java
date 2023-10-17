package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowUpdateDataSetPassword extends FlowUpdateData {

	@JsonProperty("password")
	private final String password;

	private FlowUpdateDataSetPassword(Builder builder) {
		this.password = builder.password;
	}

	public String getPassword() {
		return password;
	}

	public static class Builder {

		private String password;

		public Builder(String password) {
			this.password = password;
		}

		public FlowUpdateDataSetPassword build() {
			return new FlowUpdateDataSetPassword(this);
		}
	}
}
