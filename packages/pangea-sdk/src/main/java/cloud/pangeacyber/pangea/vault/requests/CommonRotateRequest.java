package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.vault.models.ItemVersionState;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CommonRotateRequest {

	@JsonProperty("id")
	String id;

	@JsonProperty("rotation_state")
	ItemVersionState rotationState;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_frequency")
	String rotationFrequency;

	protected CommonRotateRequest(CommonRotateRequestBuilder<?> builder) {
		this.id = builder.id;
		this.rotationState = builder.rotationState;
		this.rotationFrequency = builder.rotationFrequency;
	}

	public String getRotationFrequency() {
		return rotationFrequency;
	}

	public String getId() {
		return id;
	}

	public ItemVersionState getRotationState() {
		return rotationState;
	}

	public static class CommonRotateRequestBuilder<B extends CommonRotateRequestBuilder<B>> {

		String id;
		ItemVersionState rotationState = null;
		String rotationFrequency = null;

		public CommonRotateRequestBuilder(String id) {
			this.id = id;
		}

		@SuppressWarnings("unchecked")
		final B self() {
			return (B) this;
		}

		public CommonRotateRequest build() {
			return new CommonRotateRequest(this);
		}

		public B setRotationState(ItemVersionState rotationState) {
			this.rotationState = rotationState;
			return self();
		}

		public B setRotationFrequency(String rotationFrequency) {
			this.rotationFrequency = rotationFrequency;
			return self();
		}
	}
}
