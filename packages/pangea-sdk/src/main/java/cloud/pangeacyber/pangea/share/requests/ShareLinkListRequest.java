package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.share.models.FilterShareLinkList;
import cloud.pangeacyber.pangea.share.models.ItemOrder;
import cloud.pangeacyber.pangea.share.models.ShareLinkOrderBy;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class ShareLinkListRequest extends BaseRequest {

	/** The bucket to use, if not the default. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("bucket_id")
	String bucketId;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("filter")
	FilterShareLinkList filter;

	/** Reflected value from a previous response to obtain the next page of results. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("last")
	String last;

	/** Order results asc(ending) or desc(ending). */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("order")
	ItemOrder order;

	/** Which field to order results by. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("order_by")
	ShareLinkOrderBy orderBy;

	/** Maximum results to include in the response. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("size")
	Integer size;

	protected ShareLinkListRequest(Builder builder) {
		this.bucketId = builder.bucketId;
		this.filter = builder.filter;
		this.last = builder.last;
		this.order = builder.order;
		this.orderBy = builder.orderBy;
		this.size = builder.size;
	}

	public static class Builder {

		String bucketId;
		FilterShareLinkList filter;
		String last;
		ItemOrder order;
		ShareLinkOrderBy orderBy;
		Integer size;

		public Builder() {}

		public ShareLinkListRequest build() {
			return new ShareLinkListRequest(this);
		}

		/** The bucket to use, if not the default. */
		public Builder bucketId(String bucketId) {
			this.bucketId = bucketId;
			return this;
		}

		public Builder filter(FilterShareLinkList filter) {
			this.filter = filter;
			return this;
		}

		/** Reflected value from a previous response to obtain the next page of results. */
		public Builder last(String last) {
			this.last = last;
			return this;
		}

		/** Order results asc(ending) or desc(ending). */
		public Builder order(ItemOrder order) {
			this.order = order;
			return this;
		}

		/** Which field to order results by. */
		public Builder orderBy(ShareLinkOrderBy orderBy) {
			this.orderBy = orderBy;
			return this;
		}

		/** Maximum results to include in the response. */
		public Builder size(Integer size) {
			this.size = size;
			return this;
		}
	}
}
