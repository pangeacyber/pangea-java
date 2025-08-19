package cloud.pangeacyber.pangea;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.impl.bootstrap.HttpServer;
import org.apache.hc.core5.http.impl.bootstrap.ServerBootstrap;
import org.apache.hc.core5.http.io.HttpRequestHandler;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class TestClient extends BaseClient {

	public TestClient(final String baseUrlTemplate) {
		super(
			new BaseClient.Builder<>(Config.builder().token("token").baseUrlTemplate(baseUrlTemplate).build()),
			"test"
		);
	}

	public Response<Void> ping() throws PangeaException, PangeaAPIException {
		return post("/v1/ping", new BaseRequest(), new TypeReference<Response<Void>>() {});
	}
}

public class ClientTest {

	private class MockServerErrorHandler implements HttpRequestHandler {

		private int attempts = 0;

		@Override
		public void handle(
			final ClassicHttpRequest request,
			final ClassicHttpResponse response,
			final HttpContext context
		) throws HttpException, IOException {
			// Fail twice, then succeed from then on.
			final var success = attempts++ < 2;

			response.setCode(success ? HttpStatus.SC_OK : HttpStatus.SC_BAD_GATEWAY);

			// Response body.
			final var responseBody = new ResponseHeader();
			responseBody.setStatus(success ? ResponseStatus.SUCCESS.toString() : ResponseStatus.FAILED.toString());
			response.setEntity(new StringEntity(objectMapper.writeValueAsString(responseBody)));
		}
	}

	private static final ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

	private static ServerBootstrap serverBootstrap;
	private static HttpServer server;
	private static TestClient client;

	@BeforeEach
	public void setUp() throws IOException {
		serverBootstrap = ServerBootstrap.bootstrap();
		serverBootstrap.register("*", new MockServerErrorHandler());
		server = serverBootstrap.create();
		server.start();

		client = new TestClient("http://localhost:" + server.getLocalPort());
	}

	@AfterEach
	public void shutDown() {
		if (server != null) {
			server.close();
		}
	}

	@Test
	public void retryServerErrors() {
		// Should not throw thanks to retries.
		assertDoesNotThrow(() -> client.ping());
	}
}
