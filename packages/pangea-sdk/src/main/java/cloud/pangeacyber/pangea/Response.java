package cloud.pangeacyber.pangea;

import java.net.http.HttpResponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<Result> extends ResponseHeader{
    @JsonProperty("result")
    Result result;

    @JsonIgnore
    HttpResponse<?> httpResponse;

    public Response() {
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public HttpResponse<?> getHttpResponse() {
        return httpResponse;
    }

    public void setHttpResponse(HttpResponse<?> httpResponse) {
        this.httpResponse = httpResponse;
    }

}
