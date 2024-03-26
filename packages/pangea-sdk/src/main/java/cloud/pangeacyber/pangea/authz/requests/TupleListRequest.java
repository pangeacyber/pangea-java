package cloud.pangeacyber.pangea.authz.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authz.models.FilterTupleList;
import cloud.pangeacyber.pangea.authz.models.ListOrder;
import cloud.pangeacyber.pangea.authz.models.TupleOrderBy;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TupleListRequest extends BaseRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("filter")
	private FilterTupleList filter;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("size")
	private Integer size;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("last")
	private String last;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("order")
	private ListOrder order;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("order_by")
	private TupleOrderBy orderBy;

	private TupleListRequest(Builder builder) {
		this.filter = builder.filter;
		this.size = builder.size;
		this.last = builder.last;
		this.order = builder.order;
		this.orderBy = builder.orderBy;
	}

	public FilterTupleList getFilter() {
		return filter;
	}

	public Integer getSize() {
		return size;
	}

	public String getLast() {
		return last;
	}

	public static class Builder {

		private FilterTupleList filter;
		private Integer size;
		private String last;
		private ListOrder order;
		private TupleOrderBy orderBy;

		public Builder() {}

		public Builder setFilter(FilterTupleList filter) {
			this.filter = filter;
			return this;
		}

		public Builder setSize(Integer size) {
			this.size = size;
			return this;
		}

		public Builder setLast(String last) {
			this.last = last;
			return this;
		}

		public Builder setOrder(ListOrder order) {
			this.order = order;
			return this;
		}

		public Builder setOrderBy(TupleOrderBy orderBy) {
			this.orderBy = orderBy;
			return this;
		}

		public TupleListRequest build() {
			return new TupleListRequest(this);
		}
	}
}
