package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.TransferMethod;
import cloud.pangeacyber.pangea.share.models.ArchiveFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GetArchiveRequest extends BaseRequest {

	/** The bucket to use, if not the default. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("bucket_id")
	String bucketId;

	/** The IDs of the objects to include in the archive. Folders include all children. */
	@JsonProperty("ids")
	List<String> ids;

	/** The format to use to build the archive. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("format")
	ArchiveFormat format;

	protected GetArchiveRequest(Builder builder) {
		this.bucketId = builder.bucketId;
		this.ids = builder.ids;
		this.format = builder.format;
		this.setTransferMethod(builder.transferMethod);
	}

	public static class Builder {

		String bucketId;
		List<String> ids;
		ArchiveFormat format;
		TransferMethod transferMethod;

		public Builder(List<String> ids) {
			this.ids = ids;
		}

		public GetArchiveRequest build() {
			return new GetArchiveRequest(this);
		}

		/** The bucket to use, if not the default. */
		public Builder bucketId(String bucketId) {
			this.bucketId = bucketId;
			return this;
		}

		/** The format to use to build the archive. */
		public Builder format(ArchiveFormat format) {
			this.format = format;
			return this;
		}

		/** The requested transfer method for the file data. */
		public Builder transferMethod(TransferMethod transferMethod) {
			this.transferMethod = transferMethod;
			return this;
		}
	}
}
