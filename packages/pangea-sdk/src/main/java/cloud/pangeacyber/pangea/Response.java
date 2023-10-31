package cloud.pangeacyber.pangea;

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
	String body = "";

	public Response() {}

	public Result getResult() {
		return result;
	}

	public CloseableHttpResponse getHttpResponse() {
		return httpResponse;
	}

	protected void setHttpResponse(CloseableHttpResponse httpResponse) {
		this.httpResponse = httpResponse;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
