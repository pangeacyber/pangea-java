package cloud.pangeacyber.pangea.vault.requests;

import cloud.pangeacyber.pangea.vault.models.ItemOrder;
import cloud.pangeacyber.pangea.vault.models.ItemOrderBy;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class ListRequest {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("filter")
	Map<String, String> filter = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("last")
	String last = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("size")
	Integer size = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("order")
	ItemOrder order = null;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("order_by")
	ItemOrderBy orderBy = null;

	protected ListRequest(Builder builder) {
		this.filter = builder.filter;
		this.last = builder.last;
		this.size = builder.size;
		this.order = builder.order;
		this.orderBy = builder.orderBy;
	}

	public Map<String, String> getFilter() {
		return filter;
	}

	public String getLast() {
		return last;
	}

	public Integer getSize() {
		return size;
	}

	public ItemOrder getOrder() {
		return order;
	}

	public ItemOrderBy getOrderBy() {
		return orderBy;
	}

	public static class Builder {

		Map<String, String> filter = null;
		String last = null;
		Integer size = null;
		ItemOrder order = null;
		ItemOrderBy orderBy = null;

		public Builder() {}

		public ListRequest build() {
			return new ListRequest(this);
		}

		public Builder filter(Map<String, String> filter) {
			this.filter = filter;
			return this;
		}

		public Builder last(String last) {
			this.last = last;
			return this;
		}

		public Builder size(Integer size) {
			this.size = size;
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
	}
}
