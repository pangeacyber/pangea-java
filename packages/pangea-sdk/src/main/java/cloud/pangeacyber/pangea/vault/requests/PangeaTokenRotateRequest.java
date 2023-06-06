package cloud.pangeacyber.pangea.vault.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PangeaTokenRotateRequest extends CommonRotateRequest {
	@JsonProperty("rotation_grace_period")
	String rotationGracePeriod = null;

	protected PangeaTokenRotateRequest(PangeaTokenRotateRequestBuilder builder) {
		super(builder);
		this.rotationFrequency = builder.rotationFrequency;
	}

	public String getRotationGracePeriod() {
		return rotationGracePeriod;
	}

	public static class PangeaTokenRotateRequestBuilder extends CommonRotateRequestBuilder<PangeaTokenRotateRequestBuilder> {

		String rotationGracePeriod = null;

		public PangeaTokenRotateRequest build() {
			return new PangeaTokenRotateRequest(this);
		}

		public PangeaTokenRotateRequestBuilder(String id, String rotationGracePeriod) {
			super(id);
			this.rotationGracePeriod = rotationGracePeriod;
		}
	}
}
