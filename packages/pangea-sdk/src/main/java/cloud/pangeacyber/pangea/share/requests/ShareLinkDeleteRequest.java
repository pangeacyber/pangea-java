package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ShareLinkDeleteRequest extends BaseRequest {

	@JsonProperty("ids")
	List<String> ids;

	/** The bucket to use, if not the default. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("bucket_id")
	String bucketId;

	public List<String> getIds() {
		return ids;
	}

	/** The bucket to use, if not the default. */
	public String getBucketId() {
		return bucketId;
	}

	protected ShareLinkDeleteRequest(Builder builder) {
		this.ids = builder.ids;
	}

	public static class Builder {

		List<String> ids;
		String bucketId;

		public Builder(List<String> ids) {
			this.ids = ids;
		}

		/** The bucket to use, if not the default. */
		public Builder bucketId(String bucketId) {
			this.bucketId = bucketId;
			return this;
		}

		public ShareLinkDeleteRequest build() {
			return new ShareLinkDeleteRequest(this);
		}
	}
}
