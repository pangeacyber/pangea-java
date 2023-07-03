package cloud.pangeacyber.pangea;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import cloud.pangeacyber.pangea.exceptions.ConfigException;
import java.time.Duration;
import org.junit.Before;
import org.junit.Test;

public class ConfigTest {

	final String serviceName = "service";
	final String path = "/path";
	final String domain = "domain.test";
	final String token = "faketoken";

	@Before
	public void setUp() throws ConfigException {}

	@Test
	public void testConfig() throws ConfigException {
		Config config = Config.fromIntegrationEnvironment(TestEnvironment.LIVE);
		assertNotNull(config.getDomain());
		assertNotNull(config.getToken());
		assertNotNull(config.getConnectionTimeout());
		assertNotNull(config.getEnviroment());
		assertNotNull(config.isInsecure());
	}

	@Test
	public void testInsecureTrueEnvironmentLocal() throws ConfigException {
		Config config = new Config.Builder(token, domain).insecure(true).enviroment("local").build();
		String url = config.getServiceUrl(serviceName, path).toString();
		String urlExpected = new StringBuilder().append("http://").append(domain).append(path).toString();
		assertEquals(urlExpected, url);
	}

	@Test
	public void testInsecureFalseEnvironmentLocal() throws ConfigException {
		Config config = new Config.Builder(token, domain).insecure(false).enviroment("local").build();
		String url = config.getServiceUrl(serviceName, path).toString();
		String urlExpected = new StringBuilder().append("https://").append(domain).append(path).toString();
		assertEquals(urlExpected, url);
	}

	@Test
	public void testInsecureTrueEnvironmentProduction() throws ConfigException {
		Config config = new Config.Builder(token, domain).insecure(true).enviroment("production").build();
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
		Config config = new Config.Builder(token, domain).insecure(false).enviroment("production").build();
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
