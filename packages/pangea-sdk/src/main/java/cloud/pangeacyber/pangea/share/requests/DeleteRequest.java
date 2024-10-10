package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteRequest extends BaseRequest {

	/** The ID of the object to delete. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id")
	String id;

	/** If true, delete a folder even if it's not empty. Deletes the contents of folder as well. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("force")
	Boolean force;

	/** The bucket to use, if not the default. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("bucket_id")
	String bucketId;

	protected DeleteRequest(Builder builder) {
		this.id = builder.id;
		this.force = builder.force;
		this.bucketId = builder.bucketId;
	}

	public static class Builder {

		String id;
		Boolean force;
		String bucketId;

		public Builder() {
			// Empty constructor
		}

		public DeleteRequest build() {
			return new DeleteRequest(this);
		}

		/** The ID of the object to delete. */
		public Builder id(String id) {
			this.id = id;
			return this;
		}

		/** If true, delete a folder even if it's not empty. Deletes the contents of folder as well. */
		public Builder force(Boolean force) {
			this.force = force;
			return this;
		}

		/** The bucket to use, if not the default. */
		public Builder bucketId(String bucketId) {
			this.bucketId = bucketId;
			return this;
		}
	}
}
