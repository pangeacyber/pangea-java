package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.TransferMethod;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetRequest extends BaseRequest {

	/** The ID of the object to retrieve. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("id")
	String id;

	/** The path of the object to retrieve. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("path")
	String path;

	/** If the file was protected with a password, the password to decrypt with. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("password")
	String password;

	/** The bucket to use, if not the default. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("bucket_id")
	String bucketId;

	protected GetRequest(Builder builder) {
		this.id = builder.id;
		this.path = builder.path;
		this.bucketId = builder.bucketId;
		this.setTransferMethod(builder.transferMethod);
	}

	public static class Builder {

		String id;
		String path;
		String password;
		TransferMethod transferMethod;
		String bucketId;

		public Builder() {
			// Empty constructor
		}

		public GetRequest build() {
			return new GetRequest(this);
		}

		/** The ID of the object to retrieve. */
		public Builder id(String id) {
			this.id = id;
			return this;
		}

		/** The path of the object to retrieve. */
		public Builder path(String path) {
			this.path = path;
			return this;
		}

		/** If the file was protected with a password, the password to decrypt with. */
		public Builder password(String password) {
			this.password = password;
			return this;
		}

		/** The requested transfer method for the file data. */
		public Builder transferMethod(TransferMethod transferMethod) {
			this.transferMethod = transferMethod;
			return this;
		}

		/** The bucket to use, if not the default. */
		public Builder bucketId(String bucketId) {
			this.bucketId = bucketId;
			return this;
		}
	}
}
