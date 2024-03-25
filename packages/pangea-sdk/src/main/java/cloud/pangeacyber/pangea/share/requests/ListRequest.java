package cloud.pangeacyber.pangea.share.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.share.models.FilterList;
import cloud.pangeacyber.pangea.share.models.ItemOrder;
import cloud.pangeacyber.pangea.share.models.ItemOrderBy;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ListRequest extends BaseRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("filter")
	FilterList filter;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("last")
	String last;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("order")
	ItemOrder order;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("order_by")
	ItemOrderBy orderBy;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("size")
	Integer size;

	protected ListRequest(Builder builder) {
		this.filter = builder.filter;
		this.last = builder.last;
		this.order = builder.order;
		this.orderBy = builder.orderBy;
		this.size = builder.size;
	}

	public static class Builder {

		FilterList filter;
		String last;
		ItemOrder order;
		ItemOrderBy orderBy;
		Integer size;

		public ListRequest build() {
			return new ListRequest(this);
		}

		public Builder filter(FilterList filter) {
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

		public Builder orderBy(ItemOrderBy orderBy) {
			this.orderBy = orderBy;
			return this;
		}

		public Builder size(Integer size) {
			this.size = size;
			return this;
		}
	}
}
