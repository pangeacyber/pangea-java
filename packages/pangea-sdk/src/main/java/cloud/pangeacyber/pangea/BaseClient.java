package cloud.pangeacyber.pangea;

import cloud.pangeacyber.pangea.exceptions.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class BaseClient {

	Config config;
	protected Logger logger;
	CloseableHttpClient httpClient;
	HttpRequest.Builder httpRequestBuilder;
	public final String serviceName;
	Map<String, String> customHeaders = null;
	String userAgent = "pangea-java/default";

	protected BaseClient(Builder<?> builder) {
		this.serviceName = builder.serviceName;
		this.config = builder.config;
		this.logger = builder.logger;
		this.httpClient = buildClient();
		this.setUserAgent(config.getCustomUserAgent());
	}

	public static class Builder<B extends Builder<B>>{
		Config config;
		String serviceName;
		Logger logger;
		Map<String, String> customHeaders;
		String customUserAgent;

		public Builder(Config config, String serviceName) {
			this.config = config;
			this.serviceName = serviceName;
			this.logger = Logger.getLogger("pangea");
			this.customHeaders = null;
		}

		public BaseClient build() {
			return new BaseClient(this);
		}

		@SuppressWarnings("unchecked")
		final B self() {
			return (B) this;
		}

		public B logger(Logger logger) {
			this.logger = logger;
			return self();
		}

		public B customHeaders(Map<String, String> customHeaders) {
			this.customHeaders = customHeaders;
			return self();
		}
	}

	private void setUserAgent(String customUserAgent) {
		this.userAgent = "pangea-java/" + Version.VERSION;
		if (customUserAgent != null && !customUserAgent.isEmpty()) {
			this.userAgent += " " + customUserAgent;
		}
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
		fillHeaders(httpPost);
		return httpPost;
	}

	protected HttpPost buildPostRequest(String path, String body, File file) throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(config.getServiceUrl(serviceName, path));

		final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		final StringBody requestBody = new StringBody(body, ContentType.create("application/json"));
		builder.addPart("request", requestBody);
		builder.addBinaryBody("upload", file, ContentType.APPLICATION_OCTET_STREAM, "file.exe");

		final HttpEntity entity = builder.build();
		httpPost.setEntity(entity);
		fillHeaders(httpPost);

		return httpPost;
	}

	protected void fillHeaders(HttpRequestBase request) {
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

	protected <Req, ResponseType extends Response<?>> ResponseType post(
		String path,
		Req request,
		Class<ResponseType> responseClass
	) throws PangeaException, PangeaAPIException {
		return doPost(path, request, null, responseClass);
	}

	protected <Req, ResponseType extends Response<?>> ResponseType post(
		String path,
		Req request,
		File file,
		Class<ResponseType> responseClass
	) throws PangeaException, PangeaAPIException {
		return doPost(path, request, file, responseClass);
	}

	protected <ResponseType extends Response<?>> ResponseType get(
		String path,
		boolean checkResponse,
		Class<ResponseType> responseClass
	) throws PangeaException, PangeaAPIException {
		CloseableHttpResponse httpResponse = doGet(path);
		return checkResponse(httpResponse, responseClass);
	}

	private CloseableHttpResponse doGet(String path) throws PangeaException {
		try {
			HttpGet httpGet = new HttpGet(config.getServiceUrl(serviceName, path));
			fillHeaders(httpGet);
			return httpClient.execute(httpGet);
		} catch (Exception e) {
			System.out.println(e);
			throw new PangeaException("Failed to send get request", e);
		}
	}

	public <ResponseType extends Response<?>> ResponseType pollResult(
		String requestId,
		Class<ResponseType> responseClass
	) throws PangeaException, PangeaAPIException {
		String path = pollResultPath(requestId);
		return get(path, true, responseClass);
	}

	private <Req, ResponseType extends Response<?>> ResponseType doPost(
		String path,
		Req request,
		File file,
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
			HttpPost httpRequest;
			if (file != null) {
				httpRequest = buildPostRequest(path, body, file);
			} else {
				httpRequest = buildPostRequest(path, body);
			}

			httpResponse = httpClient.execute(httpRequest);
		} catch (Exception e) {
			throw new PangeaException("Failed to send post request", e);
		}

		if (httpResponse.getStatusLine().getStatusCode() == 202 && this.config.isQueuedRetryEnabled()) {
			httpResponse = this.handleQueued(httpResponse);
		}

		return checkResponse(httpResponse, responseClass);
	}

	private long getDelay(int retryCounter, Duration start) {
		long delay = retryCounter * retryCounter;
		long now = System.currentTimeMillis() / 1000;

		if (now + delay >= start.toSeconds() + this.config.getPollResultTimeout()) {
			delay = start.toSeconds() + this.config.getPollResultTimeout() - now;
		}
		return delay;
	}

	private boolean reachedTimeout(Duration start) {
		long now = System.currentTimeMillis() / 1000;
		return now >= start.toSeconds() + this.config.getPollResultTimeout();
	}

	private String pollResultPath(String requestId) {
		return String.format("/request/%s", requestId);
	}

	private CloseableHttpResponse handleQueued(CloseableHttpResponse response) throws PangeaException {
		if (
			response.getStatusLine().getStatusCode() != 202 ||
			!this.config.isQueuedRetryEnabled() ||
			this.config.getPollResultTimeout() <= 1
		) {
			return response;
		}

		int retryCounter = 1;
		Duration start = Duration.ofMillis(System.currentTimeMillis());
		long delay;
		ResponseHeader header = parseHeader(readBody(response));
		String requestId = header.getRequestId();
		String path = pollResultPath(requestId);

		while (response.getStatusLine().getStatusCode() == 202 && !reachedTimeout(start)) {
			delay = getDelay(retryCounter, start);

			try {
				Thread.sleep(Duration.ofSeconds(delay));
				EntityUtils.consumeQuietly(response.getEntity()); //response need to be consumed
				response = doGet(path);
				retryCounter++;
			} catch (InterruptedException e) {}
		}
		return response;
	}

	private String readBody(CloseableHttpResponse response) throws PangeaException {
		String body;
		try {
			body = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new PangeaException("Failed to read response body", e);
		}
		return body;
	}

	private ResponseHeader parseHeader(String body) throws PangeaException {
		ObjectMapper mapper = new ObjectMapper();
		ResponseHeader header;
		try {
			header = mapper.readValue(body, ResponseHeader.class);
		} catch (Exception e) {
			throw new PangeaException("Failed to parse response header", e);
		}
		return header;
	}

	private <ResponseType extends Response<?>> ResponseType checkResponse(
		CloseableHttpResponse httpResponse,
		Class<ResponseType> responseClass
	) throws PangeaException, PangeaAPIException {
		String body = readBody(httpResponse);
		ResponseHeader header = parseHeader(body);

		ObjectMapper mapper = new ObjectMapper();
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
		} else if (status.equals(ResponseStatus.ACCEPTED.toString())) {
			throw new AcceptedRequestException(
				String.format("Summary: %s. request_id: %s.", response.getSummary(), response.getRequestId()),
				response
			);
		} else {
			throw new PangeaAPIException(String.format("%s: %s", status, summary), response);
		}
	}

}