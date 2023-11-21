package cloud.pangeacyber.pangea;

import cloud.pangeacyber.pangea.exceptions.AcceptedResult;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.http.client.methods.CloseableHttpResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<Result> extends ResponseHeader {

	@JsonProperty("result")
	Result result;

	@JsonIgnore
	CloseableHttpResponse httpResponse;

	@JsonIgnore
	AcceptedResult acceptedResult;

	@JsonIgnore
	String body = "";

	public Response() {}

	public Result getResult() {
		return result;
	}

	public CloseableHttpResponse getHttpResponse() {
		return httpResponse;
	}

	protected void setHttpResponse(InternalHttpResponse httpResponse) {
		this.httpResponse = httpResponse.getResponse();
		this.body = httpResponse.getBody();
	}

	public String getBody() {
		return body;
	}

	public AcceptedResult getAcceptedResult() {
		return acceptedResult;
	}

	public Response(Response<?> response, AcceptedResult acceptedResult) {
		super(response);
		this.acceptedResult = acceptedResult;
		this.result = null;
		this.body = response.getBody();
		this.httpResponse = response.getHttpResponse();
	}
}
