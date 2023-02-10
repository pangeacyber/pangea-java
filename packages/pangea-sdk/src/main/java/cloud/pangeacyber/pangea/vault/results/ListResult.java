package cloud.pangeacyber.pangea.vault.results;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cloud.pangeacyber.pangea.vault.models.ListItemData;

public class ListResult {
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("items")
    List<ListItemData> items = null;

    @JsonProperty("count")
    int count;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("last")
    String last = null;

    public ListResult() {
    }

    public ListResult(List<ListItemData> items, int count, String last) {
        this.items = items;
        this.count = count;
        this.last = last;
    }

    public ListResult(int count) {
        this.count = count;
    }

    public List<ListItemData> getItems() {
        return items;
    }

    public int getCount() {
        return count;
    }

    public String getLast() {
        return last;
    }

}
