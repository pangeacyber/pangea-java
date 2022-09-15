package cloud.pangeacyber.pangea;

import java.net.http.HttpResponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Response<Result> {
    @JsonProperty("request_id") 
    String requestId;
    
    @JsonProperty("request_time") 
    String requestTime; // TODO: datetime

    @JsonProperty("response_time") 
    String responseTime; // TODO: datetime

    @JsonProperty("status") 
    String status;
    
    @JsonProperty("summary")
    String summary;

    @JsonProperty("result")
    Result result;

    @JsonIgnore
    HttpResponse<?> httpResponse;

    public Response() {
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public boolean isOk() {
        return status.equals("Success");
    }
}
