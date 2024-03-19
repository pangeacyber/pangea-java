package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteRequest extends BaseRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id")
	String id;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("force")
	Boolean force;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("path")
	String path;

	protected DeleteRequest(Builder builder) {
		this.id = builder.id;
		this.force = builder.force;
		this.path = builder.path;
	}

	public static class Builder {

		String id;
		Boolean force;
		String path;

		public Builder() {
			// Empty constructor
		}

		public DeleteRequest build() {
			return new DeleteRequest(this);
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder force(Boolean force) {
			this.force = force;
			return this;
		}

		public Builder path(String path) {
			this.path = path;
			return this;
		}
	}
}
