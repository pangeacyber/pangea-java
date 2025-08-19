package cloud.pangeacyber.pangea;

import java.util.Set;
import org.apache.hc.core5.http.EntityDetails;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpRequestInterceptor;
import org.apache.hc.core5.http.protocol.HttpContext;

public class RetryHeaderInterceptor implements HttpRequestInterceptor {

	@Override
	public void process(HttpRequest request, EntityDetails entity, HttpContext context) {
		@SuppressWarnings("unchecked")
		final var retriedRequestIds = (Set<String>) context.getAttribute("pangea.retried.request.ids");
		if (retriedRequestIds != null && !retriedRequestIds.isEmpty()) {
			request.setHeader("X-Pangea-Retried-Request-Ids", String.join(",", retriedRequestIds));
		}
	}
}
