package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.vault.models.ItemVersionState;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetRequest {

	@JsonProperty("id")
	String id;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("version")
	String version = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("verbose")
	Boolean verbose = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("version_state")
	ItemVersionState versionState = null;

	protected GetRequest(GetRequestBuilder builder) {
		this.id = builder.id;
		this.version = builder.version;
		this.verbose = builder.verbose;
		this.versionState = builder.versionState;
	}

	public static class GetRequestBuilder {

		String id;
		String version = null;
		Boolean verbose = null;
		ItemVersionState versionState = null;

		public GetRequestBuilder(String id) {
			this.id = id;
		}

		public GetRequest build() {
			return new GetRequest(this);
		}

		public GetRequestBuilder setVersion(String version) {
			this.version = version;
			return this;
		}

		public GetRequestBuilder setVerbose(Boolean verbose) {
			this.verbose = verbose;
			return this;
		}

		public GetRequestBuilder setVersionState(ItemVersionState versionState) {
			this.versionState = versionState;
			return this;
		}
	}

	public String getId() {
		return id;
	}

	public String getVersion() {
		return version;
	}

	public Boolean getVerbose() {
		return verbose;
	}

	public ItemVersionState getVersionState() {
		return versionState;
	}
}
