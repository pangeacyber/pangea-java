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

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("filter")
	FilterShareLinkList filter;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("last")
	String last;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("order")
	ItemOrder order;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("order_by")
	ShareLinkOrderBy orderBy;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("size")
	Integer size;

	protected ShareLinkListRequest(Builder builder) {
		this.filter = builder.filter;
		this.last = builder.last;
		this.order = builder.order;
		this.orderBy = builder.orderBy;
		this.size = builder.size;
	}

	public static class Builder {

		FilterShareLinkList filter;
		String last;
		ItemOrder order;
		ShareLinkOrderBy orderBy;
		Integer size;

		public Builder() {}

		public ShareLinkListRequest build() {
			return new ShareLinkListRequest(this);
		}

		public Builder filter(FilterShareLinkList filter) {
			this.filter = filter;
			return this;
		}

		public Builder last(String last) {
			this.last = last;
			return this;
		}

		public Builder order(ItemOrder order) {
			this.order = order;
			return this;
		}

		public Builder orderBy(ShareLinkOrderBy orderBy) {
			this.orderBy = orderBy;
			return this;
		}

		public Builder size(Integer size) {
			this.size = size;
			return this;
		}
	}
}
