package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.vault.models.Metadata;
import cloud.pangeacyber.pangea.vault.models.Tags;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CommonGenerateRequest {

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

	protected CommonGenerateRequest(CommonGenerateRequestBuilder<?> builder) {
		this.name = builder.name;
		this.folder = builder.folder;
		this.metadata = builder.metadata;
		this.tags = builder.tags;
		this.rotationFrequency = builder.rotationFrequency;
		this.expiration = builder.expiration;
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

	public static class CommonGenerateRequestBuilder<B extends CommonGenerateRequestBuilder<B>> {

		String name = null;
		String folder = null;
		Metadata metadata = null;
		Tags tags = null;
		Boolean autoRotate = null;
		String rotationFrequency = null;
		String rotationState = null;
		String expiration = null;

		public CommonGenerateRequestBuilder() {}

		public CommonGenerateRequest build() {
			return new CommonGenerateRequest(this);
		}

		@SuppressWarnings("unchecked")
		final B self() {
			return (B) this;
		}

		public B setName(String name) {
			this.name = name;
			return self();
		}

		public B setFolder(String folder) {
			this.folder = folder;
			return self();
		}

		public B setMetadata(Metadata metadata) {
			this.metadata = metadata;
			return self();
		}

		public B setTags(Tags tags) {
			this.tags = tags;
			return self();
		}

		public B setAutoRotate(Boolean autoRotate) {
			this.autoRotate = autoRotate;
			return self();
		}

		public B setRotationFrequency(String rotationFrequency) {
			this.rotationFrequency = rotationFrequency;
			return self();
		}

		public B setRotationState(String rotationState) {
			this.rotationState = rotationState;
			return self();
		}

		public B setExpiration(String expiration) {
			this.expiration = expiration;
			return self();
		}
	}
}
