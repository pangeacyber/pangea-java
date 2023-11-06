package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.models.FilterSessionList;
import cloud.pangeacyber.pangea.authn.models.ListOrder;
import cloud.pangeacyber.pangea.authn.models.SessionOrderBy;
import cloud.pangeacyber.pangea.filters.Filter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonSessionListRequest extends BaseRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("filter")
	Filter filter;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("last")
	String last;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("order")
	ListOrder order;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("order_by")
	SessionOrderBy orderBy;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("size")
	Integer size;

	protected CommonSessionListRequest(CommonBuilder<?> builder) {
		this.filter = builder.filter;
		this.last = builder.last;
		this.order = builder.order;
		this.orderBy = builder.orderBy;
		this.size = builder.size;
	}

	public static class CommonBuilder<B extends CommonBuilder<B>> {

		Filter filter;
		String last;
		ListOrder order;
		SessionOrderBy orderBy;
		Integer size;

		public CommonBuilder() {}

		public CommonSessionListRequest build() {
			return new CommonSessionListRequest(this);
		}

		@SuppressWarnings("unchecked")
		final B self() {
			return (B) this;
		}

		/**
		 * @deprecated Use setFilter(FilterSessionList filter) instead
		 */
		public B setFilter(Filter filter) {
			this.filter = new Filter();
			this.filter.putAll(filter);
			return self();
		}

		public B setFilter(FilterSessionList filter) {
			this.filter = new Filter();
			this.filter.putAll(filter);
			return self();
		}

		public B setLast(String last) {
			this.last = last;
			return self();
		}

		public B setOrder(ListOrder order) {
			this.order = order;
			return self();
		}

		public B setOrderBy(SessionOrderBy orderBy) {
			this.orderBy = orderBy;
			return self();
		}

		public B setSize(Integer size) {
			this.size = size;
			return self();
		}
	}
}
