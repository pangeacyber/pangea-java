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
		assertNotNull(config.getDomain());
		assertNotNull(config.getToken());
		assertNotNull(config.getConnectionTimeout());
		assertNotNull(config.getEnvironment());
		assertNotNull(config.isInsecure());
		assertEquals(50, config.getMaxTotalConnections());
		assertEquals(50, config.getMaxConnectionsPerRoute());
	}

	@Test
	public void testInsecureTrueEnvironmentLocal() throws ConfigException {
		Config config = new Config.Builder(token, domain).insecure(true).environment("local").build();
		String url = config.getServiceUrl(serviceName, path).toString();
		String urlExpected = new StringBuilder().append("http://").append(domain).append(path).toString();
		assertEquals(urlExpected, url);
	}

	@Test
	public void testInsecureFalseEnvironmentLocal() throws ConfigException {
		Config config = new Config.Builder(token, domain).insecure(false).environment("local").build();
		String url = config.getServiceUrl(serviceName, path).toString();
		String urlExpected = new StringBuilder().append("https://").append(domain).append(path).toString();
		assertEquals(urlExpected, url);
	}

	@Test
	public void testInsecureTrueEnvironmentProduction() throws ConfigException {
		Config config = new Config.Builder(token, domain).insecure(true).environment("production").build();
		String url = config.getServiceUrl(serviceName, path).toString();
		String urlExpected = new StringBuilder()
			.append("http://")
			.append(serviceName)
			.append(".")
			.append(domain)
			.append(path)
			.toString();
		assertEquals(urlExpected, url);
	}

	@Test
	public void testInsecureFalseEnvironmentProduction() throws ConfigException {
		Config config = new Config.Builder(token, domain).insecure(false).environment("production").build();
		String url = config.getServiceUrl(serviceName, path).toString();
		String urlExpected = new StringBuilder()
			.append("https://")
			.append(serviceName)
			.append(".")
			.append(domain)
			.append(path)
			.toString();
		assertEquals(urlExpected, url);
	}

	@Test
	public void testInsecureDefaultEnvironmentDefault() throws ConfigException {
		Config config = new Config.Builder(token, domain).build();
		String url = config.getServiceUrl(serviceName, path).toString();
		String urlExpected = new StringBuilder()
			.append("https://")
			.append(serviceName)
			.append(".")
			.append(domain)
			.append(path)
			.toString();
		assertEquals(urlExpected, url);
	}

	@Test
	public void testURL() throws ConfigException {
		String urlDomain = "https://myurldomain.net";
		Config config = new Config.Builder(token, urlDomain).build();
		String url = config.getServiceUrl(serviceName, path).toString();
		String urlExpected = new StringBuilder().append(urlDomain).append(path).toString();
		assertEquals(urlExpected, url);
	}
}
