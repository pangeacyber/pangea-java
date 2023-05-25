package cloud.pangeacyber.pangea;

import cloud.pangeacyber.pangea.exceptions.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.http.HttpRequest.Builder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
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

	public <Req, ResponseType extends Response<?>> ResponseType doPost(
		String path,
		Req request,
		Class<ResponseType> responseClass
	) throws PangeaException, PangeaAPIException {
		return internalDoPost(path, request, null, responseClass);
	}

	public <Req, ResponseType extends Response<?>> ResponseType doPost(
		String path,
		Req request,
		File file,
		Class<ResponseType> responseClass
	) throws PangeaException, PangeaAPIException {
		return internalDoPost(path, request, file, responseClass);
	}

	public <ResponseType extends Response<?>> ResponseType doGet(
		String path,
		boolean checkResponse,
		Class<ResponseType> responseClass
	) throws PangeaException, PangeaAPIException {
		CloseableHttpResponse httpResponse = internalGet(path);
		return checkResponse(httpResponse, responseClass);
	}

	private CloseableHttpResponse internalGet(String path) throws PangeaException {
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
		return doGet(path, true, responseClass);
	}

	private <Req, ResponseType extends Response<?>> ResponseType internalDoPost(
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
				Thread.sleep(delay * 1000); // Duration.ofSeconds() on Java 19
				EntityUtils.consumeQuietly(response.getEntity()); //response need to be consumed
				response = internalGet(path);
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
