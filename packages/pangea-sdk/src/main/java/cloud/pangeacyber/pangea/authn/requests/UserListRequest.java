package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.models.FilterUserList;
import cloud.pangeacyber.pangea.authn.models.UserListOrderBy;
import cloud.pangeacyber.pangea.filters.Filter;
import cloud.pangeacyber.pangea.vault.models.ItemOrder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserListRequest extends BaseRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("filter")
	private Filter filter;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("last")
	private String last;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("order")
	private ItemOrder order;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("order_by")
	private UserListOrderBy orderBy;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("size")
	private Integer size;

	private UserListRequest(Builder builder) {
		this.filter = builder.filter;
		this.last = builder.last;
		this.order = builder.order;
		this.orderBy = builder.orderBy;
		this.size = builder.size;
	}

	public static class Builder {

		private Filter filter;
		private String last;
		private ItemOrder order;
		private UserListOrderBy orderBy;
		private Integer size;

		public Builder() {}

		public Builder setFilter(FilterUserList filter) {
			this.filter = new Filter();
			this.filter.putAll(filter);
			return this;
		}

		public Builder setLast(String last) {
			this.last = last;
			return this;
		}

		public Builder setOrder(ItemOrder order) {
			this.order = order;
			return this;
		}

		public Builder setOrderBy(UserListOrderBy orderBy) {
			this.orderBy = orderBy;
			return this;
		}

		public Builder setSize(Integer size) {
			this.size = size;
			return this;
		}

		public UserListRequest build() {
			return new UserListRequest(this);
		}
	}

	public Filter getFilter() {
		return filter;
	}

	public String getLast() {
		return last;
	}

	public ItemOrder getOrder() {
		return order;
	}

	public UserListOrderBy getOrderBy() {
		return orderBy;
	}

	public Integer getSize() {
		return size;
	}
}
