package cloud.pangeacyber.pangea.authn.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class FlowUpdateDataAgreements extends FlowUpdateData {

	@JsonProperty("agreed")
	private List<String> agreed;

	private FlowUpdateDataAgreements(Builder builder) {
		this.agreed = builder.agreed;
	}

	public List<String> getAgreed() {
		return agreed;
	}

	public static class Builder {

		private List<String> agreed;

		public Builder(List<String> agreed) {
			this.agreed = agreed;
		}

		public FlowUpdateDataAgreements build() {
			return new FlowUpdateDataAgreements(this);
		}
	}
}
