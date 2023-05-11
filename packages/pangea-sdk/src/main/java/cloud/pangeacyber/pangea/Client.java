package cloud.pangeacyber.pangea;

import cloud.pangeacyber.pangea.exceptions.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.net.http.HttpRequest.Builder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public abstract class Client {

	Config config;
	CloseableHttpClient httpClient;
	Builder httpRequestBuilder;
	String serviceName;
	Map<String, String> customHeaders = null;
	String userAgent = "pangea-java/default";

	protected Client(Config config, String serviceName) {
		this.serviceName = serviceName;
		this.config = config;
		this.httpClient = buildClient();
		this.setCustomUserAgent(config.getCustomUserAgent());
	}

	protected CloseableHttpClient buildClient() {
		int timeout = 5000;
		if (config.connectionTimeout != null) {
			timeout = (int) config.connectionTimeout.toMillis();
		}

		RequestConfig config = RequestConfig
			.custom()
			.setConnectTimeout(timeout)
			.setConnectionRequestTimeout(timeout)
			.setSocketTimeout(timeout)
			.setCookieSpec(CookieSpecs.STANDARD)
			.build();
		CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		return client;
	}

	protected HttpPost buildPostRequest(String path, String body) throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(config.getServiceUrl(serviceName, path));
		httpPost.setEntity(new StringEntity(body));
		fillPostHeaders(httpPost);
		return httpPost;
	}

	protected void fillPostHeaders(HttpPost request) {
		if (customHeaders != null) {
			for (Map.Entry<String, String> entry : customHeaders.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				request.setHeader(key, value);
			}
		}
		request.setHeader("Authorization", "Bearer " + config.getToken());
		request.setHeader("User-Agent", this.userAgent);
		return;
	}

	public <Req, ResponseType extends Response<?>> ResponseType doPost(
		String path,
		Req request,
		Class<ResponseType> responseClass
	) throws PangeaException, PangeaAPIException {
		ObjectMapper mapper = new ObjectMapper();
		String body;
		try {
			body = mapper.writeValueAsString(request);
		} catch (JsonProcessingException e) {
			throw new PangeaException("Failed to write request", e);
		}

		CloseableHttpResponse httpResponse;

		try {
			HttpPost httpRequest = buildPostRequest(path, body);
			httpResponse = httpClient.execute(httpRequest);
		} catch (Exception e) {
			throw new PangeaException("Failed to send request", e);
		}

		return checkResponse(httpResponse, responseClass);
	}

	private <ResponseType extends Response<?>> ResponseType checkResponse(
		CloseableHttpResponse httpResponse,
		Class<ResponseType> responseClass
	) throws PangeaException, PangeaAPIException {
		String body = null;

		try {
			body = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new PangeaException("Failed to read response body", e);
		}

		ObjectMapper mapper = new ObjectMapper();
		ResponseHeader header;

		try {
			header = mapper.readValue(body, ResponseHeader.class);
		} catch (Exception e) {
			throw new PangeaException("Failed to parse response header", e);
		}

		ResponseType resultResponse;

		if (header.isOk()) {
			try {
				resultResponse = mapper.readValue(body, responseClass);
			} catch (Exception e) {
				throw new ParseResultFailed("Failed to parse response result", e, header, body);
			}
			resultResponse.setHttpResponse(httpResponse);
			return resultResponse;
		}

		// Process error
		String summary = header.getSummary();
		String status = header.getStatus();
		ResponseError response;
		try {
			response = mapper.readValue(body, ResponseError.class);
		} catch (Exception e) {
			throw new ParseResultFailed("Failed to parse response errors", e, header, body);
		}

		response.setHttpResponse(httpResponse);

		if (status.equals(ResponseStatus.VALIDATION_ERR.toString())) {
			throw new ValidationException(summary, response);
		} else if (status.equals(ResponseStatus.TOO_MANY_REQUESTS.toString())) {
			throw new RateLimitException(summary, response);
		} else if (status.equals(ResponseStatus.NO_CREDIT.toString())) {
			throw new NoCreditException(summary, response);
		} else if (status.equals(ResponseStatus.UNAUTHORIZED.toString())) {
			throw new UnauthorizedException(this.serviceName, response);
		} else if (status.equals(ResponseStatus.SERVICE_NOT_ENABLED.toString())) {
			throw new ServiceNotEnabledException(this.serviceName, response);
		} else if (status.equals(ResponseStatus.PROVIDER_ERR.toString())) {
			throw new ProviderErrorException(summary, response);
		} else if (
			status.equals(ResponseStatus.MISSING_CONFIG_ID_SCOPE.toString()) ||
			status.equals(ResponseStatus.MISSING_CONFIG_ID.toString())
		) {
			throw new MissingConfigID(this.serviceName, response);
		} else if (status.equals(ResponseStatus.SERVICE_NOT_AVAILABLE.toString())) {
			throw new ServiceNotAvailableException(summary, response);
		} else if (status.equals(ResponseStatus.IP_NOT_FOUND.toString())) {
			throw new EmbargoIPNotFoundException(summary, response);
		} else if (status.equals(ResponseStatus.INTERNAL_ERROR.toString())) {
			throw new InternalServerError(
				String.format(
					"Summary: %s. request_id: %s. request_time: %s. response_time: %s",
					response.getSummary(),
					response.getRequestId(),
					response.getRequestTime(),
					response.getResponseTime()
				),
				response
			);
		} else {
			throw new PangeaAPIException(String.format("%s: %s", status, summary), response);
		}
	}

	public void setCustomHeaders(Map<String, String> customHeaders) {
		this.customHeaders = customHeaders;
	}

	public void setCustomUserAgent(String customUserAgent) {
		this.userAgent = "pangea-java/" + Version.VERSION;
		if (customUserAgent != null && !customUserAgent.isEmpty()) {
			this.userAgent += " " + customUserAgent;
		}
	}
}
