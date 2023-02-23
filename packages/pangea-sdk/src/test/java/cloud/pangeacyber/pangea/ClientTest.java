package cloud.pangeacyber.pangea;

import static org.mockito.Mockito.*;

import java.net.URI;
import java.net.http.HttpRequest;
import org.junit.Before;
import org.junit.Test;

class TestClient extends Client {

	public TestClient() {
		super(new Config("token", "domain.com"), "test");
	}

	@Override
	public void fillPostRequestBuilder(HttpRequest.Builder builder, String path, String body) {
		super.fillPostRequestBuilder(builder, path, body);
	}
}

public class ClientTest {

	Client client;

	@Before
	public void setUp() {
		client = new TestClient();
		client.setCustomUserAgent("test");
	}

	@Test
	public void testBuildPostRequestOk() {
		HttpRequest.Builder builder = mock(HttpRequest.Builder.class);
		when(builder.header(any(), any())).thenReturn(builder);
		when(builder.POST(any())).thenReturn(builder);
		when(builder.uri(any())).thenReturn(builder);

		client.fillPostRequestBuilder(builder, "/path", "body");

		verify(builder).header("Authorization", "Bearer token");
		verify(builder).uri(URI.create("https://test.domain.com/path"));
	}
}
