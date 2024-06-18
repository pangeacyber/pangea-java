package cloud.pangeacyber.pangea;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

/**
 * {@link ServiceUnavailableRetryStrategy} implementation that retries HTTP/503
 * and HTTP/504 responses.
 */
public final class ServerErrorRetryStrategy implements ServiceUnavailableRetryStrategy {

	/** Maximum number of allowed retries. */
	private final int maxRetries;

	/** Retry interval between subsequent requests, in milliseconds. */
	private final long retryInterval;

	public ServerErrorRetryStrategy() {
		this(3, 5000);
	}

	public ServerErrorRetryStrategy(final int maxRetries, final long retryInterval) {
		super();
		Args.positive(maxRetries, "maxRetries");
		Args.positive(retryInterval, "retryInterval");
		this.maxRetries = maxRetries;
		this.retryInterval = retryInterval;
	}

	/**
	 * Determines if a method should be retried given the response from the server.
	 * @param response Response from the server.
	 * @param executionCount Number of times this method has been unsuccessfully executed.
	 * @param context Context for the request execution.
	 * @return true if the method should be retried, false otherwise.
	 */
	@Override
	public final boolean retryRequest(HttpResponse response, int executionCount, HttpContext context) {
		final var statusCode = response.getStatusLine().getStatusCode();
		return (
			executionCount <= maxRetries &&
			(statusCode == HttpStatus.SC_SERVICE_UNAVAILABLE || statusCode == HttpStatus.SC_GATEWAY_TIMEOUT)
		);
	}

	/** @return The interval between the subsequent auto-retries. */
	@Override
	public final long getRetryInterval() {
		return this.retryInterval;
	}
}
