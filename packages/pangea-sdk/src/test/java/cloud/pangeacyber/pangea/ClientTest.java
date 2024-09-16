package cloud.pangeacyber.pangea;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class TestClient extends BaseClient {

	public TestClient(String domain) {
		super(
			new BaseClient.Builder<>(new Config.Builder("token", domain).enviroment("local").insecure(true).build()),
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
		public void handle(final HttpRequest request, final HttpResponse response, final HttpContext context)
			throws HttpException, IOException {
			// Fail twice, then succeed from then on.
			final var success = attempts++ < 2;

			response.setStatusCode(success ? HttpStatus.SC_OK : HttpStatus.SC_BAD_GATEWAY);

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
		serverBootstrap = ServerBootstrap.bootstrap().setServerInfo("TEST/1.1");
		serverBootstrap.registerHandler("*", new MockServerErrorHandler());
		server = serverBootstrap.create();
		server.start();

		client = new TestClient("localhost:" + server.getLocalPort());
	}

	@AfterEach
	public void shutDown() {
		if (server != null) {
			server.shutdown(10, TimeUnit.SECONDS);
		}
	}

	@Test
	public void retryServerErrors() {
		// Should not throw thanks to retries.
		assertDoesNotThrow(() -> client.ping());
	}
}
