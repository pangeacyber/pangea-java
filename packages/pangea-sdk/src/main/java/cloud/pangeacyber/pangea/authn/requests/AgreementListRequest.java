package cloud.pangeacyber.pangea.authn.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.authn.models.AgreementListOrderBy;
import cloud.pangeacyber.pangea.authn.models.Filter;
import cloud.pangeacyber.pangea.authn.models.FilterAgreementList;
import cloud.pangeacyber.pangea.authn.models.ListOrder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AgreementListRequest extends BaseRequest {

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
	AgreementListOrderBy orderBy;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("size")
	Integer size;

	private AgreementListRequest(Builder builder) {
		this.filter = builder.filter;
		this.last = builder.last;
		this.order = builder.order;
		this.orderBy = builder.orderBy;
		this.size = builder.size;
	}

	public static class Builder {

		Filter filter;
		String last;
		ListOrder order;
		AgreementListOrderBy orderBy;
		Integer size;

		public Builder() {}

		public AgreementListRequest build() {
			return new AgreementListRequest(this);
		}

		/**
		 * @deprecated Use setFilter(FilterAgreementList filter) instead
		 */
		public Builder setFilter(Map<String, String> filter) {
			this.filter = new Filter();
			this.filter.putAll(filter);
			return this;
		}

		public Builder setFilter(FilterAgreementList filter) {
			this.filter = new Filter();
			this.filter.putAll(filter);
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

		public Builder setOrderBy(AgreementListOrderBy orderBy) {
			this.orderBy = orderBy;
			return this;
		}

		public Builder setSize(Integer size) {
			this.size = size;
			return this;
		}
	}
}
