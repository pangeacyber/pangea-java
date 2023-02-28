package cloud.pangeacyber.pangea.vault.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.vault.models.ItemVersionState;

public class CommonRotateRequest {
    @JsonProperty("id")
    String id;

    @JsonProperty("state")
    ItemVersionState state;

    protected CommonRotateRequest(CommonRotateRequestBuilder<?> builder) {
        this.id = builder.id;
        this.state = builder.state;
    }

    public String getId() {
        return id;
    }

    public static class CommonRotateRequestBuilder<B extends CommonRotateRequestBuilder<B>>{
        String id;
        ItemVersionState state;

        public CommonRotateRequestBuilder(String id, ItemVersionState state){
            this.id = id;
            this.state = state;
        }

        public CommonRotateRequest build(){
            return new CommonRotateRequest(this);
        }
    }
}
