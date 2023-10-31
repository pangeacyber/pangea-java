package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowUpdateDataPassword extends FlowUpdateData {

	@JsonProperty("password")
	private final String password;

	private FlowUpdateDataPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public static class Builder {

		private final String password;

		public Builder(String password) {
			this.password = password;
		}

		public FlowUpdateDataPassword build() {
			return new FlowUpdateDataPassword(password);
		}
	}
}
