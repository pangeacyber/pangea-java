package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.vault.models.Metadata;
import cloud.pangeacyber.pangea.vault.models.Tags;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CommonGenerateRequest extends BaseRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("name")
	String name = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("folder")
	String folder = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("metadata")
	Metadata metadata = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("tags")
	Tags tags = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_frequency")
	String rotationFrequency = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_state")
	String rotationState = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("expiration")
	String expiration = null;

	/** Whether the key is exportable or not. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("exportable")
	Boolean exportable = null;

	protected CommonGenerateRequest(CommonBuilder<?> builder) {
		this.name = builder.name;
		this.folder = builder.folder;
		this.metadata = builder.metadata;
		this.tags = builder.tags;
		this.rotationFrequency = builder.rotationFrequency;
		this.expiration = builder.expiration;
		this.exportable = builder.exportable;
	}

	public String getName() {
		return name;
	}

	public String getFolder() {
		return folder;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public Tags getTags() {
		return tags;
	}

	public String getRotationFrequency() {
		return rotationFrequency;
	}

	public String getExpiration() {
		return expiration;
	}

	public static class CommonBuilder<B extends CommonBuilder<B>> {

		String name = null;
		String folder = null;
		Metadata metadata = null;
		Tags tags = null;
		Boolean autoRotate = null;
		String rotationFrequency = null;
		String rotationState = null;
		String expiration = null;
		Boolean exportable = null;

		public CommonBuilder(String name) {
			this.name = name;
		}

		public CommonGenerateRequest build() {
			return new CommonGenerateRequest(this);
		}

		@SuppressWarnings("unchecked")
		final B self() {
			return (B) this;
		}

		public B name(String name) {
			this.name = name;
			return self();
		}

		public B folder(String folder) {
			this.folder = folder;
			return self();
		}

		public B metadata(Metadata metadata) {
			this.metadata = metadata;
			return self();
		}

		public B tags(Tags tags) {
			this.tags = tags;
			return self();
		}

		public B autoRotate(Boolean autoRotate) {
			this.autoRotate = autoRotate;
			return self();
		}

		public B rotationFrequency(String rotationFrequency) {
			this.rotationFrequency = rotationFrequency;
			return self();
		}

		public B rotationState(String rotationState) {
			this.rotationState = rotationState;
			return self();
		}

		public B expiration(String expiration) {
			this.expiration = expiration;
			return self();
		}

		public B exportable(boolean exportable) {
			this.exportable = exportable;
			return self();
		}
	}
}
