package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.share.models.FilterList;
import cloud.pangeacyber.pangea.share.models.ItemOrder;
import cloud.pangeacyber.pangea.share.models.ItemOrderBy;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ListRequest extends BaseRequest {

	/** The bucket to use, if not the default. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("bucket_id")
	String bucketId;

	/** If true, include the `external_bucket_key` in results. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("include_external_bucket_key")
	Boolean includeExternalBucketKey;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("filter")
	FilterList filter;

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
	ItemOrderBy orderBy;

	/** Maximum results to include in the response. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("size")
	Integer size;

	protected ListRequest(Builder builder) {
		this.bucketId = builder.bucketId;
		this.includeExternalBucketKey = builder.includeExternalBucketKey;
		this.filter = builder.filter;
		this.last = builder.last;
		this.order = builder.order;
		this.orderBy = builder.orderBy;
		this.size = builder.size;
	}

	public static class Builder {

		String bucketId;
		Boolean includeExternalBucketKey;
		FilterList filter;
		String last;
		ItemOrder order;
		ItemOrderBy orderBy;
		Integer size;

		public ListRequest build() {
			return new ListRequest(this);
		}

		/** The bucket to use, if not the default. */
		public Builder bucketId(String bucketId) {
			this.bucketId = bucketId;
			return this;
		}

		/** If true, include the `external_bucket_key` in results. */
		public Builder includeExternalBucketKey(boolean includeExternalBucketKey) {
			this.includeExternalBucketKey = includeExternalBucketKey;
			return this;
		}

		public Builder filter(FilterList filter) {
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
		public Builder orderBy(ItemOrderBy orderBy) {
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
