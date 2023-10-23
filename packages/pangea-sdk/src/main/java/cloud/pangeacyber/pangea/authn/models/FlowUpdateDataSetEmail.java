package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowUpdateDataSetEmail extends FlowUpdateData {

	@JsonProperty("email")
	private final String email;

	private FlowUpdateDataSetEmail(Builder builder) {
		this.email = builder.email;
	}

	public String getEmail() {
		return email;
	}

	public static class Builder {

		private String email;

		public Builder(String email) {
			this.email = email;
		}

		public FlowUpdateDataSetEmail build() {
			return new FlowUpdateDataSetEmail(this);
		}
	}
}
