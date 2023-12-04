package cloud.pangeacyber.pangea;

import cloud.pangeacyber.pangea.exceptions.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

final class InternalHttpResponse {

	CloseableHttpResponse response;
	String body;

	public InternalHttpResponse(CloseableHttpResponse response) throws PangeaException {
		this.response = response;
		this.body = readBody(response);
	}

	private String readBody(CloseableHttpResponse response) throws PangeaException {
		String body = "";
		HttpEntity entity = response.getEntity();
		if (entity == null) {
			return body;
		}

		try {
			body = EntityUtils.toString(entity, StandardCharsets.UTF_8);
		} catch (Exception e) {
			System.out.println(e.toString());
			throw new PangeaException("Failed to read response body", e);
		}
		return body;
	}

	public CloseableHttpResponse getResponse() {
		return response;
	}

	public String getBody() {
		return body;
	}
}

public abstract class BaseClient {

	protected Config config;
	protected Logger logger;
	CloseableHttpClient httpClient;
	HttpRequest.Builder httpRequestBuilder;
	String serviceName;
	Map<String, String> customHeaders = null;
	String userAgent = "pangea-java/default";
	private String configID = null;

	protected BaseClient(Builder<?> builder, String serviceName) {
		this.config = builder.config;
		this.serviceName = serviceName;
		if (builder.logger != null) {
			this.logger = builder.logger;
		} else {
			this.logger = buildDefaultLogger();
		}
		this.httpClient = buildClient();
		this.setUserAgent(config.getCustomUserAgent());
	}

	private Logger buildDefaultLogger() {
		ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();

		// Set up the root logger to use the console appender and a file appender
		builder.add(builder.newRootLogger(Level.DEBUG).add(builder.newAppenderRef("File")));

		// Create a file appender
		LayoutComponentBuilder fileLayoutBuilder = builder
			.newLayout("PatternLayout")
			.addAttribute(
				"pattern",
				"{\"time\": \"%d{yyyy-MM-dd HH:mm:ss.SSS}\", \"name\": \"%logger{36}\", \"level\": \"%-5level\", \"message\": %msg },%n"
			);
		AppenderComponentBuilder fileAppenderBuilder = builder
			.newAppender("File", "File")
			.addAttribute("fileName", "pangea_sdk_logs.json")
			.add(fileLayoutBuilder);
		builder.add(fileAppenderBuilder);

		// Build the configuration and initialize Log4j
		Configurator.initialize(builder.build());

		return LogManager.getLogger("pangea");
	}

	protected void setConfigID(String configID) {
		this.configID = configID;
	}

	public String getConfigID() {
		return this.configID;
	}

	public static class Builder<B extends Builder<B>> {

		Config config;
		String serviceName;
		Logger logger;
		Map<String, String> customHeaders;
		String customUserAgent;

		public Builder(Config config) {
			this.config = config;
			this.logger = null;
			this.customHeaders = null;
		}

		protected Builder() {
			this.config = new Config.Builder("", "").build();
			this.logger = null;
			this.customHeaders = null;
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

	protected HttpPost buildPostRequest(URI url, String body) throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(body));
		fillHeaders(httpPost);
		return httpPost;
	}

	protected HttpPost buildPostRequest(URI url, String body, FileData fileData) throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);

		final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		final StringBody requestBody = new StringBody(body, ContentType.create("application/json"));
		builder.addPart("request", requestBody);
		builder.addBinaryBody(fileData.getName(), fileData.getFile(), ContentType.APPLICATION_OCTET_STREAM, "file.exe");

		final HttpEntity entity = builder.build();
		httpPost.setEntity(entity);
		fillHeaders(httpPost);

		return httpPost;
	}

	protected HttpUriRequest buildRequestPresignedURL(URI url, TransferMethod transferMethod, FileData fileData) {
		HttpEntityEnclosingRequestBase httpRequest;
		if (transferMethod == TransferMethod.PUT_URL) {
			httpRequest = new HttpPut(url);
		} else {
			httpRequest = new HttpPost(url);
		}

		final MultipartEntityBuilder builder = MultipartEntityBuilder.create();

		if (
			fileData.getDetails() != null &&
			(transferMethod == TransferMethod.POST_URL || transferMethod == TransferMethod.DIRECT)
		) {
			for (Map.Entry<String, Object> entry : fileData.getDetails().entrySet()) {
				if (entry.getValue() instanceof String) {
					final StringBody requestBody = new StringBody(
						(String) entry.getValue(),
						ContentType.create("application/json")
					);
					String key = entry.getKey();
					builder.addPart(key, requestBody);
				}
			}
		}

		builder.addBinaryBody(fileData.getName(), fileData.getFile(), ContentType.APPLICATION_OCTET_STREAM, "file.exe");
		final HttpEntity entity = builder.build();
		httpRequest.setEntity(entity);
		return httpRequest;
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

	protected <Req extends BaseRequest, ResponseType extends Response<?>> ResponseType post(
		String path,
		Req request,
		Class<ResponseType> responseClass,
		PostConfig postConfig
	) throws PangeaException, PangeaAPIException {
		return doPost(path, request, null, responseClass, postConfig);
	}

	protected <Req extends BaseRequest, ResponseType extends Response<?>> ResponseType post(
		String path,
		Req request,
		Class<ResponseType> responseClass
	) throws PangeaException, PangeaAPIException {
		return doPost(path, request, null, responseClass, new PostConfig.Builder().build());
	}

	protected <Req extends BaseRequest, ResponseType extends Response<?>> ResponseType post(
		String path,
		Req request,
		FileData fileData,
		Class<ResponseType> responseClass
	) throws PangeaException, PangeaAPIException {
		return doPost(path, request, fileData, responseClass, new PostConfig.Builder().build());
	}

	protected <ResponseType extends Response<?>> ResponseType get(
		String path,
		boolean checkResponse,
		Class<ResponseType> responseClass
	) throws PangeaException, PangeaAPIException {
		InternalHttpResponse response = doGet(path);
		return checkResponse(response, responseClass, path);
	}

	private InternalHttpResponse doGet(String path) throws PangeaException {
		URI uri = config.getServiceUrl(serviceName, path);
		try {
			this.logger.debug(
					String.format(
						"{\"service\": \"%s\", \"action\": \"get\", \"url\": \"%s\"}",
						serviceName,
						uri.toString()
					)
				);
			HttpGet httpGet = new HttpGet(uri);
			fillHeaders(httpGet);
			return executeRequest(httpGet);
		} catch (Exception e) {
			this.logger.error(
					String.format(
						"{\"service\": \"%s\", \"action\": \"get\", \"url\": \"%s\", \"message\": \"failed to send request\", \"exception\": \"%s\"}",
						serviceName,
						uri.toString(),
						e.toString()
					)
				);
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

	private <Req extends BaseRequest, ResponseType extends Response<?>> ResponseType doPost(
		String path,
		Req request,
		FileData fileData,
		Class<ResponseType> responseClass,
		PostConfig postConfig
	) throws PangeaException, PangeaAPIException {
		if (configID != null && !configID.isEmpty() && request.getConfigID() == null) {
			request.setConfigID(this.configID);
		}

		URI url = config.getServiceUrl(serviceName, path);
		InternalHttpResponse response;

		if (request.getTransferMethod() == TransferMethod.DIRECT) {
			response = fullPostPresignedURL(url, request, fileData, responseClass);
		} else {
			response = postSingle(url, request, fileData);
		}

		if (postConfig.getPollResult() == true) {
			response = this.handleQueued(response);
		}
		return checkResponse(response, responseClass, url.toString());
	}

	private AcceptedResponse pollPresignedURL(AcceptedResponse response) throws PangeaAPIException, PangeaException {
		AcceptedResult acceptedResult = response.getResult();
		if (
			acceptedResult != null &&
			acceptedResult.getAcceptedStatus() != null &&
			acceptedResult.getAcceptedStatus().getUploadURL() != null
		) {
			return response;
		}

		String requestId = response.getRequestId();
		String path = pollResultPath(requestId);
		int retryCounter = 1;
		Duration start = Duration.ofMillis(System.currentTimeMillis());
		long delay;
		AcceptedRequestException loopException = null;
		AcceptedResponse loopResp = response;

		while (
			(
				acceptedResult == null ||
				acceptedResult.getAcceptedStatus() == null ||
				acceptedResult.getAcceptedStatus().getUploadURL() == null
			) &&
			!reachedTimeout(start)
		) {
			this.logger.debug(
					String.format(
						"{\"service\": \"%s\", \"action\": \"poll presigned URL\", \"step\": \"%d\"}",
						serviceName,
						retryCounter
					)
				);

			delay = getDelay(retryCounter, start);
			try {
				Thread.sleep(delay * 1000); //sleep(Duration) is supported on v19. We use v18.
			} catch (InterruptedException e) {
				throw new PangeaException("Interrupted timer", e);
			}

			retryCounter++;
			try {
				InternalHttpResponse httpResponse = doGet(path);
				loopResp = checkResponse(httpResponse, AcceptedResponse.class, path);
				return loopResp;
			} catch (AcceptedRequestException e) {
				acceptedResult = e.getAcceptedResult();
				loopException = e;
			} catch (PangeaException e) {
				throw new PresignedURLException("Failed polling presigned URL", e, null);
			}
		}

		if (reachedTimeout(start)) {
			if (loopException != null) {
				throw loopException;
			} else {
				throw new PangeaException("Unable to poll presigned URL", null);
			}
		}

		return loopResp;
	}

	protected <Req extends BaseRequest> AcceptedResponse requestPresignedURL(String path, Req request)
		throws PangeaException, PangeaAPIException {
		URI url = config.getServiceUrl(serviceName, path);
		InternalHttpResponse response = postSingle(url, request, null);
		AcceptedResponse responseAccepted = checkResponse(response, AcceptedResponse.class, url.toString());
		return this.pollPresignedURL(responseAccepted);
	}

	protected void uploadPresignedURL(String url, TransferMethod transferMethod, FileData fileData)
		throws PresignedURLException {
		URI uri;
		try {
			uri = URI.create(url);
		} catch (Exception e) {
			throw new PresignedURLException(String.format("Failed to read presigned URL: %s", url), e, null);
		}

		HttpUriRequest request = buildRequestPresignedURL(uri, transferMethod, fileData);
		this.logger.debug(
				String.format(
					"{\"service\": \"%s\", \"action\": \"upload presigned url\", \"url\": \"%s\", \"transfer method\": \"%s\"}",
					serviceName,
					url,
					transferMethod
				)
			);

		InternalHttpResponse psURLresponse;
		try {
			psURLresponse = executeRequest(request);
		} catch (Exception e) {
			System.out.println(e.toString());
			throw new PresignedURLException("Failed to upload to presigned URL", e, null);
		}

		int statusCode = psURLresponse.getResponse().getStatusLine().getStatusCode();
		if (statusCode < 200 || statusCode >= 300) {
			throw new PresignedURLException(
				String.format("Error when uploading to presigned URL. StatusCode: %d", statusCode),
				null,
				psURLresponse.getBody()
			);
		}
		this.logger.debug(
				String.format(
					"{\"service\": \"%s\", \"action\": \"upload presigned url\", \"url\": \"%s\", \"response\": \"%s\"}",
					serviceName,
					url,
					psURLresponse.getBody()
				)
			);
	}

	private <Req extends BaseRequest, ResponseType extends Response<?>> InternalHttpResponse fullPostPresignedURL(
		URI url,
		Req request,
		FileData fileData,
		Class<ResponseType> responseClass
	) throws PangeaException, PangeaAPIException {
		InternalHttpResponse response = postSingle(url, request, null);
		AcceptedResponse responseAccepted = checkResponse(response, AcceptedResponse.class, url.toString());
		responseAccepted = this.pollPresignedURL(responseAccepted);

		String presignedURL = responseAccepted.getResult().getAcceptedStatus().getUploadURL();
		fileData.setDetails(responseAccepted.getResult().getAcceptedStatus().getUploadDetails());

		uploadPresignedURL(presignedURL, TransferMethod.POST_URL, fileData);
		return response;
	}

	private InternalHttpResponse executeRequest(HttpUriRequest request)
		throws PangeaException, IOException, ClientProtocolException {
		return new InternalHttpResponse(httpClient.execute(request));
	}

	private <Req extends BaseRequest> InternalHttpResponse postSingle(URI url, Req request, FileData fileData)
		throws PangeaException, PangeaAPIException {
		ObjectMapper mapper = new ObjectMapper();
		String body;

		try {
			body = mapper.writeValueAsString(request);
		} catch (JsonProcessingException e) {
			throw new PangeaException("Failed to write request", e);
		}

		this.logger.debug(
				String.format(
					"{\"service\": \"%s\", \"action\": \"post\", \"url\": \"%s\", \"data\": %s}",
					serviceName,
					url.toString(),
					body
				)
			);

		try {
			HttpPost httpRequest;
			if (fileData != null) {
				httpRequest = buildPostRequest(url, body, fileData);
			} else {
				httpRequest = buildPostRequest(url, body);
			}

			return executeRequest(httpRequest);
		} catch (Exception e) {
			this.logger.error(
					String.format(
						"{\"service\": \"%s\", \"action\": \"post\", \"url\": \"%s\", \"message\": \"failed to send request\", \"exception\": \"%s\"}",
						serviceName,
						url.toString(),
						e.toString()
					)
				);
			throw new PangeaException("Failed to send post request", e);
		}
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

	private InternalHttpResponse handleQueued(InternalHttpResponse response) throws PangeaException {
		if (
			response.getResponse().getStatusLine().getStatusCode() != 202 ||
			!this.config.isQueuedRetryEnabled() ||
			this.config.getPollResultTimeout() <= 1
		) {
			return response;
		}

		String body = response.getBody();

		int retryCounter = 1;
		Duration start = Duration.ofMillis(System.currentTimeMillis());
		long delay;

		this.logger.info(
				String.format(
					"{\"service\": \"%s\", \"action\": \"handle queued\", \"step\": \"start\", \"response\": %s}",
					serviceName,
					body
				)
			);
		ResponseHeader header = parseHeader(body);
		String requestId = header.getRequestId();
		String path = pollResultPath(requestId);

		while (response.getResponse().getStatusLine().getStatusCode() == 202 && !reachedTimeout(start)) {
			delay = getDelay(retryCounter, start);
			this.logger.debug(
					String.format(
						"{\"service\": \"%s\", \"action\": \"handle queued\", \"step\": \"%d\"}",
						serviceName,
						retryCounter
					)
				);

			try {
				Thread.sleep(delay * 1000); // sleep(Duration) is supported on v19. We use v18.
				EntityUtils.consumeQuietly(response.getResponse().getEntity()); // response need to be consumed
				response = doGet(path);
				retryCounter++;
			} catch (InterruptedException e) {}
		}

		this.logger.debug(
				String.format("{\"service\": \"%s\", \"action\": \"handle queued\", \"step\": \"exit\"}", serviceName)
			);

		return response;
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

	private <ResponseType extends Response<?>> ResponseType parseResponse(
		InternalHttpResponse httpResponse,
		Class<ResponseType> responseClass
	) throws PangeaException {
		ResponseType resultResponse;
		ObjectMapper mapper = new ObjectMapper();
		try {
			resultResponse = mapper.readValue(httpResponse.getBody(), responseClass);
		} catch (Exception e) {
			this.logger.error(
					String.format(
						"{\"service\": \"%s\", \"action\": \"check response\", \"message\": \"failed to parse result\", \"response\": %s, \"exception\": \"%s\"}",
						serviceName,
						httpResponse.getBody(),
						e.toString()
					)
				);
			ResponseHeader header = parseHeader(httpResponse.getBody());
			throw new ParseResultFailed("Failed to parse response result", e, header, httpResponse.getBody());
		}
		resultResponse.setHttpResponse(httpResponse);
		return resultResponse;
	}

	private <ResponseType extends Response<?>> ResponseType checkResponse(
		InternalHttpResponse httpResponse,
		Class<ResponseType> responseClass,
		String url
	) throws PangeaException, PangeaAPIException {
		this.logger.debug(
				String.format(
					"{\"service\": \"%s\", \"action\": \"check response\", \"response\": %s}",
					serviceName,
					httpResponse.getBody()
				)
			);

		if (httpResponse.getResponse().getStatusLine().getStatusCode() == 503) {
			throw new ServiceTemporarilyUnavailable(httpResponse.getBody());
		}

		ResponseHeader header = parseHeader(httpResponse.getBody());

		if (header.isOk()) {
			return parseResponse(httpResponse, responseClass);
		}

		// Process error
		ResponseError response;
		String summary = header.getSummary();
		String status = header.getStatus();
		response = parseResponse(httpResponse, ResponseError.class);

		if (ResponseStatus.ACCEPTED.equals(status)) {
			AcceptedResponse responseAccepted = null;
			responseAccepted = parseResponse(httpResponse, AcceptedResponse.class);
			if (responseClass == AcceptedResponse.class) {
				return (ResponseType) responseAccepted;
			}

			throw new AcceptedRequestException(
				String.format("Summary: \"%s\". request_id: \"%s\".", response.getSummary(), response.getRequestId()),
				response,
				responseAccepted != null ? responseAccepted.getResult() : null
			);
		}

		if (ResponseStatus.VALIDATION_ERR.equals(status)) {
			throw new ValidationException(summary, response);
		} else if (ResponseStatus.TOO_MANY_REQUESTS.equals(status)) {
			throw new RateLimitException(summary, response);
		} else if (ResponseStatus.NO_CREDIT.equals(status)) {
			throw new NoCreditException(summary, response);
		} else if (ResponseStatus.UNAUTHORIZED.equals(status)) {
			throw new UnauthorizedException(serviceName, response);
		} else if (ResponseStatus.SERVICE_NOT_ENABLED.equals(status)) {
			throw new ServiceNotEnabledException(serviceName, response);
		} else if (ResponseStatus.PROVIDER_ERR.equals(status)) {
			throw new ProviderErrorException(summary, response);
		} else if (
			ResponseStatus.MISSING_CONFIG_ID_SCOPE.equals(status) || ResponseStatus.MISSING_CONFIG_ID.equals(status)
		) {
			throw new MissingConfigID(serviceName, response);
		} else if (ResponseStatus.NOT_FOUND.equals(status)) {
			throw new NotFound(url, response);
		} else if (ResponseStatus.SERVICE_NOT_AVAILABLE.equals(status)) {
			throw new ServiceNotAvailableException(summary, response);
		} else if (ResponseStatus.IP_NOT_FOUND.equals(status)) {
			throw new EmbargoIPNotFoundException(summary, response);
		} else if (ResponseStatus.INTERNAL_ERROR.equals(status)) {
			throw new InternalServerError(
				String.format(
					"Summary: \"%s\". request_id: \"%s\". request_time: \"%s\". response_time: \"%s\"",
					response.getSummary(),
					response.getRequestId(),
					response.getRequestTime(),
					response.getResponseTime()
				),
				response
			);
		} else {
			throw new PangeaAPIException(String.format("\"%s\": \"%s\"", status, summary), response);
		}
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
