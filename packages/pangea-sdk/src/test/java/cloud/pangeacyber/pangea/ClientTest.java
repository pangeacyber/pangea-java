package cloud.pangeacyber.pangea;

import org.junit.Before;

class TestClient extends Client {

	public TestClient() {
		super(new Config.ConfigBuilder("token", "domain.com").build(), "test", false);
	}
}

public class ClientTest {

	Client client;

	@Before
	public void setUp() {
		client = new TestClient();
		client.setCustomUserAgent("test");
	}
}
