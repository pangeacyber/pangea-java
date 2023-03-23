package cloud.pangeacyber.pangea;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseHeader {

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

	public boolean isOk() {
		return status.equals(ResponseStatus.SUCCESS.toString());
	}
}
