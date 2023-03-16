package cloud.pangeacyber.pangea.intel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.UnauthorizedException;
import cloud.pangeacyber.pangea.exceptions.ValidationException;
import cloud.pangeacyber.pangea.intel.models.IntelLookupData;
import cloud.pangeacyber.pangea.intel.models.IntelReputationData;
import cloud.pangeacyber.pangea.intel.models.URLReputationResponse;
import cloud.pangeacyber.pangea.intel.models.UrlLookupResponse;
import org.junit.Before;
import org.junit.Test;

public class ITURLIntelTest {

	UrlIntelClient client;
	TestEnvironment environment = TestEnvironment.LIVE;

	@Before
	public void setUp() throws ConfigException {
		client = new UrlIntelClient(Config.fromIntegrationEnvironment(environment));
		client.setCustomUserAgent("test");
	}

	@Test
	public void testUrlLookupMalicious_1() throws PangeaException, PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, not raw by default;
		UrlLookupResponse response = client.lookup("http://113.235.101.11:54384");
		assertTrue(response.isOk());

		IntelLookupData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testUrlLookupMalicious_2() throws PangeaException, PangeaAPIException {
		// With provider, not verbose by default, not raw by default;
		UrlLookupResponse response = client.lookup("http://113.235.101.11:54384", "crowdstrike");
		assertTrue(response.isOk());

		IntelLookupData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testUrlLookupMalicious_3() throws PangeaException, PangeaAPIException {
		// Default provider, no verbose, no raw;
		UrlLookupResponse response = client.lookup("http://113.235.101.11:54384", false, false);
		assertTrue(response.isOk());

		IntelLookupData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testUrlLookupMalicious_4() throws PangeaException, PangeaAPIException {
		// Default provider, verbose, no raw;
		UrlLookupResponse response = client.lookup("http://113.235.101.11:54384", true, false);
		assertTrue(response.isOk());

		IntelLookupData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNotNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testUrlLookupMalicious_5() throws PangeaException, PangeaAPIException {
		// Default provider, no verbose, raw;
		UrlLookupResponse response = client.lookup("http://113.235.101.11:54384", false, true);
		assertTrue(response.isOk());

		IntelLookupData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testUrlLookupMalicious_6() throws PangeaException, PangeaAPIException {
		// Default provider, verbose, raw;
		UrlLookupResponse response = client.lookup("http://113.235.101.11:54384", true, true);
		assertTrue(response.isOk());

		IntelLookupData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testUrlLookupMalicious_7() throws PangeaException, PangeaAPIException {
		// Provider, no verbose, no raw
		UrlLookupResponse response = client.lookup("http://113.235.101.11:54384", "crowdstrike", false, false);
		assertTrue(response.isOk());

		IntelLookupData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testUrlLookupMalicious_8() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		UrlLookupResponse response = client.lookup("http://113.235.101.11:54384", "crowdstrike", true, true);
		assertTrue(response.isOk());

		IntelLookupData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testUrlReputationMalicious_1() throws PangeaException, PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, not raw by default;
		URLReputationResponse response = client.reputation("http://113.235.101.11:54384");
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testUrlReputationMalicious_2() throws PangeaException, PangeaAPIException {
		// With provider, not verbose by default, not raw by default;
		URLReputationResponse response = client.reputation("http://113.235.101.11:54384", "crowdstrike");
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testUrlReputationMalicious_3() throws PangeaException, PangeaAPIException {
		// Default provider, no verbose, no raw;
		URLReputationResponse response = client.reputation("http://113.235.101.11:54384", false, false);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testUrlReputationMalicious_4() throws PangeaException, PangeaAPIException {
		// Default provider, verbose, no raw;
		URLReputationResponse response = client.reputation("http://113.235.101.11:54384", true, false);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNotNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testUrlReputationMalicious_5() throws PangeaException, PangeaAPIException {
		// Default provider, no verbose, raw;
		URLReputationResponse response = client.reputation("http://113.235.101.11:54384", false, true);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testUrlReputationMalicious_6() throws PangeaException, PangeaAPIException {
		// Default provider, verbose, raw;
		URLReputationResponse response = client.reputation("http://113.235.101.11:54384", true, true);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testUrlReputationMalicious_7() throws PangeaException, PangeaAPIException {
		// Provider, no verbose, no raw
		URLReputationResponse response = client.reputation("http://113.235.101.11:54384", "crowdstrike", false, false);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testUrlReputationMalicious_8() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		URLReputationResponse response = client.reputation("http://113.235.101.11:54384", "crowdstrike", true, true);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test(expected = ValidationException.class)
	public void testEmptyIP() throws PangeaException, PangeaAPIException {
		URLReputationResponse response = client.reputation("", "crowdstrike", true, true);
	}

	@Test(expected = ValidationException.class)
	public void testEmptyProvider() throws PangeaException, PangeaAPIException {
		URLReputationResponse response = client.reputation("http://113.235.101.11:54384", "", true, true);
	}

	@Test(expected = ValidationException.class)
	public void testEmptyNotValidProvider() throws PangeaException, PangeaAPIException {
		URLReputationResponse response = client.reputation(
			"http://113.235.101.11:54384",
			"notvalidprovider",
			true,
			true
		);
	}

	@Test(expected = UnauthorizedException.class)
	public void testUnauthorized() throws PangeaException, PangeaAPIException, ConfigException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg.setToken("notarealtoken");
		UrlIntelClient fakeClient = new UrlIntelClient(cfg);
		URLReputationResponse response = fakeClient.reputation("http://113.235.101.11:54384");
	}
}
