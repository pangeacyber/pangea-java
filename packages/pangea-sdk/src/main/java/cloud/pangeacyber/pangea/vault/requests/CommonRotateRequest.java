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

	protected CommonRotateRequest(CommonBuilder<?> builder) {
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

	public static class CommonBuilder<B extends CommonBuilder<B>> {

		String id;
		ItemVersionState rotationState = null;
		String rotationFrequency = null;

		public CommonBuilder(String id) {
			this.id = id;
		}

		@SuppressWarnings("unchecked")
		final B self() {
			return (B) this;
		}

		public CommonRotateRequest build() {
			return new CommonRotateRequest(this);
		}

		public B rotationState(ItemVersionState rotationState) {
			this.rotationState = rotationState;
			return self();
		}

		public B rotationFrequency(String rotationFrequency) {
			this.rotationFrequency = rotationFrequency;
			return self();
		}
	}
}
