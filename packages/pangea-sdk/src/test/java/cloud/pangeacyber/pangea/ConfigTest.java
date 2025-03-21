package cloud.pangeacyber.pangea;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import cloud.pangeacyber.pangea.exceptions.ConfigException;
import org.junit.jupiter.api.Test;

public class ConfigTest {

	final String serviceName = "service";
	final String path = "/path";
	final String domain = "domain.test";
	final String token = "faketoken";

	@Test
	public void testConfig() throws ConfigException {
		Config config = Config.fromIntegrationEnvironment(TestEnvironment.LIVE);
		assertNotNull(config.getBaseURLTemplate());
		assertNotNull(config.getToken());
		assertNotNull(config.getConnectionTimeout());
		assertNotNull(config.getEnvironment());
		assertNotNull(config.isInsecure());
		assertEquals(50, config.getMaxTotalConnections());
		assertEquals(50, config.getMaxConnectionsPerRoute());
	}
}
