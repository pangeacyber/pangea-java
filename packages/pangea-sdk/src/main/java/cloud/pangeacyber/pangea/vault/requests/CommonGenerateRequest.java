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
	@JsonProperty("auto_rotate")
	Boolean autoRotate = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("rotation_policy")
	String rotationPolicy = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("store")
	Boolean store = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("expiration")
	String expiration = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("managed")
	Boolean managed = null;

	protected CommonGenerateRequest(CommonGenerateRequestBuilder<?> builder) {
		this.name = builder.name;
		this.folder = builder.folder;
		this.metadata = builder.metadata;
		this.tags = builder.tags;
		this.autoRotate = builder.autoRotate;
		this.rotationPolicy = builder.rotationPolicy;
		this.store = builder.store;
		this.expiration = builder.expiration;
		this.managed = builder.managed;
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

	public Boolean getAutoRotate() {
		return autoRotate;
	}

	public String getRotationPolicy() {
		return rotationPolicy;
	}

	public Boolean getStore() {
		return store;
	}

	public String getExpiration() {
		return expiration;
	}

	public Boolean getManaged() {
		return managed;
	}

	public static class CommonGenerateRequestBuilder<B extends CommonGenerateRequestBuilder<B>> {

		String name = null;
		String folder = null;
		Metadata metadata = null;
		Tags tags = null;
		Boolean autoRotate = null;
		String rotationPolicy = null;
		Boolean retainPreviousVersion = null;
		Boolean store = null;
		String expiration = null;
		Boolean managed = null;

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

		public B setRotationPolicy(String rotationPolicy) {
			this.rotationPolicy = rotationPolicy;
			return self();
		}

		public B setRetainPreviousVersion(Boolean retainPreviousVersion) {
			this.retainPreviousVersion = retainPreviousVersion;
			return self();
		}

		public B setStore(Boolean store) {
			this.store = store;
			return self();
		}

		public B setExpiration(String expiration) {
			this.expiration = expiration;
			return self();
		}

		public B setManaged(Boolean managed) {
			this.managed = managed;
			return self();
		}
	}
}
