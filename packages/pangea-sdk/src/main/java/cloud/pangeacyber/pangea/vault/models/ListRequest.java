package cloud.pangeacyber.pangea.vault.models;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ListRequest {
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("filter")
    Map<String,String> filter = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("restrictions")
    Map<String,List<String>> restrictions = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("last")
    String last = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("size")
    Integer size = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("order")
    String order = null;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("order_by")
    String orderBy = null;

    public Map<String, String> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, String> filter) {
        this.filter = filter;
    }

    public Map<String, List<String>> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(Map<String, List<String>> restrictions) {
        this.restrictions = restrictions;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
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
}
