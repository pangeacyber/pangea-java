package cloud.pangeacyber.pangea;

import org.junit.Before;

class TestClient extends BaseClient {

	public TestClient() {
		super(new BaseClient.Builder<>(new Config.ConfigBuilder("token", "domain.com").build()), "test", false);
	}
}

public class ClientTest {

	BaseClient client;

	@Before
	public void setUp() {
		client = new TestClient();
	}
}
