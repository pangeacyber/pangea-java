package cloud.pangeacyber.pangea;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import cloud.pangeacyber.pangea.exceptions.ConfigException;
import org.junit.jupiter.api.Test;

public class ConfigTest {

	@Test
	public void testConfig() throws ConfigException {
		Config config = Config.fromIntegrationEnvironment(TestEnvironment.LIVE);
		assertNotNull(config.getDomain());
		assertNotNull(config.getToken());
		assertNotNull(config.getConnectionTimeout());
		assertEquals(50, config.getMaxTotalConnections());
		assertEquals(50, config.getMaxConnectionsPerRoute());
	}

	@Test
	public void testUrl() throws ConfigException {
		var config = Config.builder().token("foo").baseUrlTemplate("https://example.org/{SERVICE_NAME}").build();
		assertEquals("https://example.org/audit/api", config.getServiceUrl("audit", "api").toString());

		config = Config.builder().token("foo").baseUrlTemplate("https://example.org").build();
		assertEquals("https://example.org/api", config.getServiceUrl("audit", "api").toString());

		config = Config.builder().token("foo").domain("example.org").build();
		assertEquals("https://audit.example.org/api", config.getServiceUrl("audit", "api").toString());
	}
}
