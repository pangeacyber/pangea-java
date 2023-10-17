package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlowRestartDataSMSOTP extends FlowRestartData {

	@JsonProperty("phone")
	private String phone;

	private FlowRestartDataSMSOTP(Builder builder) {
		this.phone = builder.phone;
	}

	public String getPhone() {
		return phone;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {

		private String phone;

		public Builder setPhone(String phone) {
			this.phone = phone;
			return this;
		}

		public FlowRestartDataSMSOTP build() {
			return new FlowRestartDataSMSOTP(this);
		}
	}
}
