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
import cloud.pangeacyber.pangea.intel.responses.*;
import org.junit.Before;
import org.junit.Test;

public class ITDomainIntelTest {

	DomainIntelClient client;
	TestEnvironment environment = TestEnvironment.LIVE;

	@Before
	public void setUp() throws ConfigException {
		client = new DomainIntelClient.Builder(Config.fromIntegrationEnvironment(environment)).build();
	}

	@Test
	public void testDomainReputationMalicious_1() throws PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, not raw by default;
		DomainReputationResponse response = client.reputation(
			new DomainReputationRequest.Builder("737updatesboeing.com").build()
		);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertNotNull(data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testDomainReputationMalicious_2() throws PangeaException, PangeaAPIException {
		// With provider, not verbose by default, not raw by default;
		DomainReputationResponse response = client.reputation(
			new DomainReputationRequest.Builder("737updatesboeing.com").provider("crowdstrike").build()
		);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertNotNull(data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testDomainReputationMalicious_3() throws PangeaException, PangeaAPIException {
		// Default provider, no verbose, no raw;
		DomainReputationResponse response = client.reputation(
			new DomainReputationRequest.Builder("737updatesboeing.com").verbose(false).raw(false).build()
		);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertNotNull(data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testDomainReputationMalicious_4() throws PangeaException, PangeaAPIException {
		// Default provider, verbose, no raw;
		DomainReputationResponse response = client.reputation(
			new DomainReputationRequest.Builder("737updatesboeing.com").verbose(true).raw(false).build()
		);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertNotNull(data.getVerdict());
		assertNotNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testDomainReputationMalicious_5() throws PangeaException, PangeaAPIException {
		// Default provider, no verbose, raw;
		DomainReputationResponse response = client.reputation(
			new DomainReputationRequest.Builder("737updatesboeing.com").verbose(false).raw(true).build()
		);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertNotNull(data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testDomainReputationMalicious_6() throws PangeaException, PangeaAPIException {
		// Default provider, verbose, raw;
		DomainReputationResponse response = client.reputation(
			new DomainReputationRequest.Builder("737updatesboeing.com").verbose(true).raw(true).build()
		);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertNotNull(data.getVerdict());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testDomainReputationMalicious_7() throws PangeaException, PangeaAPIException {
		// Provider, no verbose, no raw
		DomainReputationResponse response = client.reputation(
			new DomainReputationRequest.Builder("737updatesboeing.com")
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
	}

	@Test
	public void testDomainReputationMalicious_8() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		DomainReputationResponse response = client.reputation(
			new DomainReputationRequest.Builder("737updatesboeing.com")
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
	}

	@Test
	public void testDomainReputationNotFound() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		DomainReputationResponse response = client.reputation(
			new DomainReputationRequest.Builder("thisshouldbeafakedomain123asd.com")
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
	}

	@Test(expected = ValidationException.class)
	public void testEmptyIP() throws PangeaException, PangeaAPIException {
		DomainReputationResponse response = client.reputation(new DomainReputationRequest.Builder("").build());
	}

	@Test(expected = ValidationException.class)
	public void testEmptyProvider() throws PangeaException, PangeaAPIException {
		DomainReputationResponse response = client.reputation(
			new DomainReputationRequest.Builder("737updatesboeing.com").provider("").build()
		);
	}

	@Test(expected = ValidationException.class)
	public void testEmptyNotValidProvider() throws PangeaException, PangeaAPIException {
		DomainReputationResponse response = client.reputation(
			new DomainReputationRequest.Builder("737updatesboeing.com").provider("notavalidprovider").build()
		);
	}

	@Test(expected = UnauthorizedException.class)
	public void testUnauthorized() throws PangeaException, PangeaAPIException, ConfigException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg = new Config.Builder("notarealtoken", cfg.getDomain()).build();
		DomainIntelClient fakeClient = new DomainIntelClient.Builder(cfg).build();
		DomainReputationResponse response = fakeClient.reputation(
			new DomainReputationRequest.Builder("737updatesboeing.com").build()
		);
	}
}
