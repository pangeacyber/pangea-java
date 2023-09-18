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
import cloud.pangeacyber.pangea.intel.models.IntelReputationData;
import cloud.pangeacyber.pangea.intel.requests.*;
import cloud.pangeacyber.pangea.intel.responses.URLReputationResponse;
import org.junit.Before;
import org.junit.Test;

public class ITURLIntelTest {

	URLIntelClient client;
	TestEnvironment environment = TestEnvironment.LIVE;

	@Before
	public void setUp() throws ConfigException {
		client = new URLIntelClient.Builder(Config.fromIntegrationEnvironment(environment)).build();
	}

	@Test
	public void testUrlReputationMalicious_1() throws PangeaException, PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, not raw by default;
		URLReputationResponse response = client.reputation(
			new URLReputationRequest.Builder("http://113.235.101.11:54384").build()
		);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
		assertNull(response.getResult().getDataDetails());
	}

	@Test
	public void testUrlReputationMalicious_2() throws PangeaException, PangeaAPIException {
		// With provider, not verbose by default, not raw by default;
		URLReputationResponse response = client.reputation(
			new URLReputationRequest.Builder("http://113.235.101.11:54384").provider("crowdstrike").build()
		);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
		assertNull(response.getResult().getDataDetails());
	}

	@Test
	public void testUrlReputationMalicious_3() throws PangeaException, PangeaAPIException {
		// Default provider, no verbose, no raw;
		URLReputationResponse response = client.reputation(
			new URLReputationRequest.Builder("http://113.235.101.11:54384").verbose(false).raw(false).build()
		);

		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
		assertNull(response.getResult().getDataDetails());
	}

	@Test
	public void testUrlReputationMalicious_4() throws PangeaException, PangeaAPIException {
		// Default provider, verbose, no raw;
		URLReputationResponse response = client.reputation(
			new URLReputationRequest.Builder("http://113.235.101.11:54384").verbose(true).raw(false).build()
		);

		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNotNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
		assertNull(response.getResult().getDataDetails());
	}

	@Test
	public void testUrlReputationMalicious_5() throws PangeaException, PangeaAPIException {
		// Default provider, no verbose, raw;
		URLReputationResponse response = client.reputation(
			new URLReputationRequest.Builder("http://113.235.101.11:54384").verbose(false).raw(true).build()
		);

		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
		assertNull(response.getResult().getDataDetails());
	}

	@Test
	public void testUrlReputationMalicious_6() throws PangeaException, PangeaAPIException {
		// Default provider, verbose, raw;
		URLReputationResponse response = client.reputation(
			new URLReputationRequest.Builder("http://113.235.101.11:54384").verbose(true).raw(true).build()
		);

		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
		assertNull(response.getResult().getDataDetails());
	}

	@Test
	public void testUrlReputationMalicious_7() throws PangeaException, PangeaAPIException {
		// Provider, no verbose, no raw
		URLReputationResponse response = client.reputation(
			new URLReputationRequest.Builder("http://113.235.101.11:54384")
				.provider("crowdstrike")
				.verbose(false)
				.raw(false)
				.build()
		);

		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
		assertNull(response.getResult().getDataDetails());
	}

	@Test
	public void testUrlReputationMalicious_8() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		URLReputationResponse response = client.reputation(
			new URLReputationRequest.Builder("http://113.235.101.11:54384")
				.provider("crowdstrike")
				.verbose(true)
				.raw(true)
				.build()
		);

		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
		assertNull(response.getResult().getDataDetails());
	}

	@Test
	public void testUrlReputationMaliciousBulk() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		String[] urlList = {"http://113.235.101.11:54384",
			"http://45.14.49.109:54819",
			"https://chcial.ru/uplcv?utm_term%3Dcost%2Bto%2Brezone%2Bland"};
		URLReputationResponse response = client.reputation(
			new URLReputationRequest.Builder(urlList)
				.provider("crowdstrike")
				.verbose(true)
				.raw(true)
				.build()
		);

		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
		assertEquals(3, response.getResult().getDataDetails().size());
	}

	@Test
	public void testUrlReputationNotFound() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		URLReputationResponse response = client.reputation(
			new URLReputationRequest.Builder("http://thisshouldbeafakeurl123asd:54384")
				.provider("crowdstrike")
				.verbose(true)
				.raw(true)
				.build()
		);

		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertNotNull(data);
		assertNotNull(data.getVerdict());
		assertNotNull(data.getCategory());
		assertNotNull(data.getScore());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test(expected = ValidationException.class)
	public void testEmptyURL() throws PangeaException, PangeaAPIException {
		URLReputationResponse response = client.reputation(
			new URLReputationRequest.Builder("").provider("crowdstrike").verbose(true).raw(true).build()
		);
	}

	@Test(expected = ValidationException.class)
	public void testEmptyProvider() throws PangeaException, PangeaAPIException {
		URLReputationResponse response = client.reputation(
			new URLReputationRequest.Builder("http://113.235.101.11:54384").provider("").verbose(true).raw(true).build()
		);
	}

	@Test(expected = ValidationException.class)
	public void testEmptyNotValidProvider() throws PangeaException, PangeaAPIException {
		URLReputationResponse response = client.reputation(
			new URLReputationRequest.Builder("http://113.235.101.11:54384")
				.provider("notavalidprovider")
				.verbose(true)
				.raw(true)
				.build()
		);
	}

	@Test(expected = UnauthorizedException.class)
	public void testUnauthorized() throws PangeaException, PangeaAPIException, ConfigException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg = new Config.Builder("notarealtoken", cfg.getDomain()).build();
		URLIntelClient fakeClient = new URLIntelClient.Builder(cfg).build();
		URLReputationResponse response = fakeClient.reputation(
			new URLReputationRequest.Builder("http://113.235.101.11:54384")
				.provider("crowdstrike")
				.verbose(true)
				.raw(true)
				.build()
		);
	}
}
