package cloud.pangeacyber.pangea.authn.requests;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cloud.pangeacyber.pangea.authn.models.ListOrder;
import cloud.pangeacyber.pangea.authn.models.SessionOrderBy;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonSessionListRequest {
    @JsonInclude(Include.NON_NULL)
    @JsonProperty("filter")
    HashMap<String,String> filter;

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

    protected CommonSessionListRequest(CommonSessionListRequestBuilder<?> builder) {
        this.filter = builder.filter;
        this.last = builder.last;
        this.order = builder.order;
        this.orderBy = builder.orderBy;
        this.size = builder.size;
	}

	public static class CommonSessionListRequestBuilder<B extends CommonSessionListRequestBuilder<B>>{
        HashMap<String,String> filter;
        String last;
        ListOrder order;
        SessionOrderBy orderBy;
        Integer size;

		public CommonSessionListRequestBuilder() {}

        public CommonSessionListRequest build(){
			return new CommonSessionListRequest(this);
		}

        @SuppressWarnings("unchecked")
        final B self() {
            return (B) this;
        }

        public B setFilter(HashMap<String, String> filter) {
            this.filter = filter;
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
