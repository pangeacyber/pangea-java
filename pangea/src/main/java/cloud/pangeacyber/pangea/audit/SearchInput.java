package cloud.pangeacyber.pangea.audit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class SearchInput {
    @JsonProperty("query")
    String query;
    
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("order")
    String order = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("order_by")
    String orderBy = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("last")
    Integer last = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("start")
    String start = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("end")
    String end = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("limit")
    Integer limit = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("max_results")
    Integer maxResults = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("include_membership_proof")
    Boolean includeMembershipProof = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("include_hash")
    Boolean includeHash = true;     // Will be removed soon

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("include_root")
    Boolean includeRoot = true;     // Will be removed soon

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("search_restriction")
    SearchRestriction searchRestriction = null;

    public SearchInput(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getLast() {
        return last;
    }

    public void setLast(Integer last) {
        this.last = last;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    public Boolean getIncludeMembershipProof() {
        return includeMembershipProof;
    }

    public void setIncludeMembershipProof(Boolean includeMembershipProof) {
        this.includeMembershipProof = includeMembershipProof;
    }

    public SearchRestriction getSearchRestriction() {
        return searchRestriction;
    }

    public void setSearchRestriction(SearchRestriction searchRestriction) {
        this.searchRestriction = searchRestriction;
    }
}
