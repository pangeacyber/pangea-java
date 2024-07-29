package cloud.pangeacyber.pangea.intel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Helper;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.UnauthorizedException;
import cloud.pangeacyber.pangea.exceptions.ValidationException;
import cloud.pangeacyber.pangea.intel.models.DomainReputationBulkData;
import cloud.pangeacyber.pangea.intel.models.DomainWhoIsData;
import cloud.pangeacyber.pangea.intel.models.IntelReputationData;
import cloud.pangeacyber.pangea.intel.requests.*;
import cloud.pangeacyber.pangea.intel.responses.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ITDomainIntelTest {

	DomainIntelClient client;
	static TestEnvironment environment;

	@BeforeAll
	public static void setUpClass() throws Exception {
		environment = Helper.loadTestEnvironment("domain-intel", TestEnvironment.LIVE);
	}

	@BeforeEach
	public void setUp() throws Exception {
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
	public void testDomainReputationBulk() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		String[] domainList = { "pemewizubidob.cafij.co.za", "redbomb.com.tr", "kmbk8.hicp.net" };
		DomainReputationBulkResponse response = client.reputationBulk(
			new DomainReputationBulkRequest.Builder(domainList).provider("crowdstrike").verbose(true).raw(true).build()
		);
		assertTrue(response.isOk());

		DomainReputationBulkData data = response.getResult().getData();
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
		assertEquals(3, data.size());
	}

	@Test
	public void testDomainWhoIs() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		DomainWhoIsResponse response = client.whoIs(
			new DomainWhoIsRequest.Builder("737updatesboeing.com").provider("whoisxml").verbose(true).raw(true).build()
		);
		assertTrue(response.isOk());

		DomainWhoIsData data = response.getResult().getData();
		assertNotNull(data.getDomainName());
		assertNotNull(data.getDomainAvailability());
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

	@Test
	public void testEmptyIP() throws PangeaException, PangeaAPIException {
		assertThrows(
			ValidationException.class,
			() -> client.reputation(new DomainReputationRequest.Builder("").build())
		);
	}

	@Test
	public void testEmptyProvider() throws PangeaException, PangeaAPIException {
		assertThrows(
			ValidationException.class,
			() -> client.reputation(new DomainReputationRequest.Builder("737updatesboeing.com").provider("").build())
		);
	}

	@Test
	public void testEmptyNotValidProvider() throws PangeaException, PangeaAPIException {
		assertThrows(
			ValidationException.class,
			() ->
				client.reputation(
					new DomainReputationRequest.Builder("737updatesboeing.com").provider("notavalidprovider").build()
				)
		);
	}

	@Test
	public void testUnauthorized() throws PangeaException, PangeaAPIException, ConfigException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg = new Config.Builder("notarealtoken", cfg.getDomain()).build();
		DomainIntelClient fakeClient = new DomainIntelClient.Builder(cfg).build();
		assertThrows(
			UnauthorizedException.class,
			() -> fakeClient.reputation(new DomainReputationRequest.Builder("737updatesboeing.com").build())
		);
	}
}
