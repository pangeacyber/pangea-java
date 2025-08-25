package cloud.pangeacyber.pangea;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.hc.client5.http.HttpRequestRetryStrategy;
import org.apache.hc.core5.concurrent.CancellableDependency;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.util.Args;
import org.apache.hc.core5.util.TimeValue;

/**
 * {@link HttpRequestRetryStrategy} implementation that retries HTTP/500,
 * HTTP/502, HTTP/503, and HTTP/504 responses.
 */
public final class ServerErrorRetryStrategy implements HttpRequestRetryStrategy {

	/** HTTP status codes that may be retried. */
	private static final Set<Integer> RETRYABLE_HTTP_CODES = Set.of(
		HttpStatus.SC_INTERNAL_SERVER_ERROR,
		HttpStatus.SC_BAD_GATEWAY,
		HttpStatus.SC_SERVICE_UNAVAILABLE,
		HttpStatus.SC_GATEWAY_TIMEOUT
	);

	/** Maximum number of allowed retries. */
	private final int maxRetries;

	public ServerErrorRetryStrategy() {
		this(3);
	}

	public ServerErrorRetryStrategy(final int maxRetries) {
		super();
		Args.positive(maxRetries, "maxRetries");
		this.maxRetries = maxRetries;
	}

	/**
	 * Determines if a method should be retried after an I/O exception occurred
	 * during execution.
	 * @param request Request that failed due to an I/O exception.
	 * @param exception Exception that occurred.
	 * @param executionCount Number of times this method has been unsuccessfully executed.
	 * @param context Context for the request execution.
	 * @return true if the request should be retried, false otherwise.
	 */
	@Override
	public final boolean retryRequest(
		HttpRequest request,
		IOException exception,
		int executionCount,
		HttpContext context
	) {
		if (executionCount > maxRetries) {
			return false;
		}

		if (request instanceof CancellableDependency && ((CancellableDependency) request).isCancelled()) {
			return false;
		}

		return true;
	}

	/**
	 * Determines if a method should be retried given the response from the target server.
	 * @param response Response from the target server.
	 * @param executionCount Number of times this method has been unsuccessfully executed.
	 * @param context Context for the request execution.
	 * @return true if the request should be retried, false otherwise.
	 */
	@Override
	public final boolean retryRequest(HttpResponse response, int executionCount, HttpContext context) {
		final var statusCode = response.getCode();
		final var shouldRetry = executionCount <= maxRetries && RETRYABLE_HTTP_CODES.contains(statusCode);
		if (shouldRetry) {
			final var requestIdHeader = response.getFirstHeader("x-request-id");
			if (requestIdHeader == null) {
				return shouldRetry;
			}
			final var requestId = requestIdHeader.getValue();

			@SuppressWarnings("unchecked")
			var retriedRequestIds = (Set<String>) context.getAttribute("pangea.retried.request.ids");
			if (retriedRequestIds == null) {
				retriedRequestIds = new HashSet<>();
				context.setAttribute("pangea.retried.request.ids", retriedRequestIds);
			}
			retriedRequestIds.add(requestId);
		}
		return shouldRetry;
	}

	/** @return The retry interval between subsequent retries. */
	@Override
	public final TimeValue getRetryInterval(HttpResponse response, int executionCount, HttpContext context) {
		final var delaySeconds = Math.min(0.5 * Math.pow(2, executionCount - 1), 8);
		final var jitter = 1 - ThreadLocalRandom.current().nextDouble() * 0.25;
		return TimeValue.ofMilliseconds((long) (delaySeconds * jitter * 1000));
	}
}
