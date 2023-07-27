package cloud.pangeacyber.pangea.audit.requests;

import cloud.pangeacyber.pangea.BaseRequest;
import cloud.pangeacyber.pangea.audit.models.SearchRestriction;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchRequest extends BaseRequest {

	@JsonProperty("query")
	private final String query;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("order")
	private final String order;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("order_by")
	private final String orderBy;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("start")
	private final String start;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("end")
	private final String end;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("limit")
	private final Integer limit;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("max_results")
	private final Integer maxResults;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("search_restriction")
	private final SearchRestriction searchRestriction;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("verbose")
	private Boolean verbose;

	private SearchRequest(Builder builder) {
		this.query = builder.query;
		this.order = builder.order;
		this.orderBy = builder.orderBy;
		this.start = builder.start;
		this.end = builder.end;
		this.limit = builder.limit;
		this.maxResults = builder.maxResults;
		this.searchRestriction = builder.searchRestriction;
		this.verbose = builder.verbose;
	}

	public String getQuery() {
		return query;
	}

	public String getOrder() {
		return order;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}

	public Integer getLimit() {
		return limit;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public SearchRestriction getSearchRestriction() {
		return searchRestriction;
	}

	public Boolean getVerbose() {
		return verbose;
	}

	public void setVerbose(Boolean verbose) {
		this.verbose = verbose;
	}

	public static class Builder {

		private final String query;
		private String order = null;
		private String orderBy = null;
		private String start = null;
		private String end = null;
		private Integer limit = null;
		private Integer maxResults = null;
		private SearchRestriction searchRestriction = null;
		private Boolean verbose = null;

		public Builder(String query) {
			this.query = query;
		}

		public Builder order(String order) {
			this.order = order;
			return this;
		}

		public Builder orderBy(String orderBy) {
			this.orderBy = orderBy;
			return this;
		}

		public Builder start(String start) {
			this.start = start;
			return this;
		}

		public Builder end(String end) {
			this.end = end;
			return this;
		}

		public Builder limit(Integer limit) {
			this.limit = limit;
			return this;
		}

		public Builder maxResults(Integer maxResults) {
			this.maxResults = maxResults;
			return this;
		}

		public Builder searchRestriction(SearchRestriction searchRestriction) {
			this.searchRestriction = searchRestriction;
			return this;
		}

		public Builder verbose(Boolean verbose) {
			this.verbose = verbose;
			return this;
		}

		public SearchRequest build() {
			return new SearchRequest(this);
		}
	}
}
