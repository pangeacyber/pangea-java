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

		config.setDomain(domain);
		config.setToken(token);
		config.setConnectionTimeout(Duration.ofSeconds(30));
		config.setEnviroment("prod");
		config.setInsecure(false);
	}

	@Test
	public void testInsecureTrueEnvironmentLocal() throws ConfigException {
		Config config = new Config.ConfigBuilder(token, domain).setInsecure(true).setEnviroment("local").build();
		String url = config.getServiceUrl(serviceName, path).toString();
		String urlExpected = new StringBuilder().append("http://").append(domain).append(path).toString();
		assertEquals(urlExpected, url);
	}

	@Test
	public void testInsecureFalseEnvironmentLocal() throws ConfigException {
		Config config = new Config.ConfigBuilder(token, domain).setInsecure(false).setEnviroment("local").build();
		String url = config.getServiceUrl(serviceName, path).toString();
		String urlExpected = new StringBuilder().append("https://").append(domain).append(path).toString();
		assertEquals(urlExpected, url);
	}

	@Test
	public void testInsecureTrueEnvironmentProduction() throws ConfigException {
		Config config = new Config.ConfigBuilder(token, domain).setInsecure(true).setEnviroment("production").build();
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
		Config config = new Config.ConfigBuilder(token, domain).setInsecure(false).setEnviroment("production").build();
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
		Config config = new Config.ConfigBuilder(token, domain).build();
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
	public void testFQDN() throws ConfigException {
		String fqdnDomain = "https://myfqdndomain.net";
		Config config = new Config.ConfigBuilder(token, fqdnDomain).build();
		String url = config.getServiceUrl(serviceName, path).toString();
		String urlExpected = new StringBuilder().append(fqdnDomain).append(path).toString();
		assertEquals(urlExpected, url);
	}
}
