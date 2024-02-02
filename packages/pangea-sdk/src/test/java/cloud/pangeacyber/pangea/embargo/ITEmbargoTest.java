package cloud.pangeacyber.pangea.embargo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Helper;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.embargo.models.EmbargoSanction;
import cloud.pangeacyber.pangea.embargo.models.EmbargoSanctions;
import cloud.pangeacyber.pangea.embargo.responses.IPCheckResponse;
import cloud.pangeacyber.pangea.embargo.responses.ISOCheckResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.UnauthorizedException;
import cloud.pangeacyber.pangea.exceptions.ValidationException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ITEmbargoTest {

	EmbargoClient client;
	static TestEnvironment environment;

	@BeforeClass
	public static void setUpClass() throws Exception {
		environment = Helper.loadTestEnvironment("embargo", TestEnvironment.LIVE);
	}

	@Before
	public void setUp() throws Exception {
		client = new EmbargoClient.Builder(Config.fromIntegrationEnvironment(environment)).build();
	}

	@Test
	public void testISOCheckSanctionedCountry() throws PangeaException, PangeaException, PangeaAPIException {
		ISOCheckResponse response = client.isoCheck("CU");
		assertTrue(response.isOk());

		EmbargoSanctions result = response.getResult();
		assertTrue(result.getCount() > 0);

		EmbargoSanction sanction = result.getSanctions().elementAt(0);
		assertEquals("Cuba", sanction.getEmbargoedCountryName());
	}

	@Test
	public void testISOCheckNoSanctionedCountry() throws PangeaException, PangeaAPIException {
		ISOCheckResponse response = client.isoCheck("AR");
		assertTrue(response.isOk());

		EmbargoSanctions result = response.getResult();
		assertTrue(result.getCount() == 0);
	}

	@Test
	public void testIPCheckSanctionedCountry() throws PangeaException, PangeaAPIException {
		IPCheckResponse response = client.ipCheck("213.24.238.26");
		assertTrue(response.isOk());

		EmbargoSanctions result = response.getResult();
		assertTrue(result.getCount() > 0);

		EmbargoSanction sanction = result.getSanctions().elementAt(0);
		assertEquals("Russia", sanction.getEmbargoedCountryName());
		assertEquals("RU", sanction.getEmbargoedCountryISOCode());
		assertEquals("US", sanction.getIssuingCountry());
		assertEquals("US - ITAR", sanction.getListName());
	}

	@Test(expected = ValidationException.class)
	public void testEmptyIP() throws PangeaException, PangeaAPIException {
		IPCheckResponse response = client.ipCheck("");
	}

	public void testPrintError() throws PangeaException, PangeaAPIException {
		try {
			IPCheckResponse response = client.ipCheck("");
			assertTrue(false);
		} catch (PangeaAPIException e) {
			assertNotNull(e.toString());
			System.out.println(e.toString());
		}
	}

	@Test(expected = UnauthorizedException.class)
	public void testUnauthorized() throws PangeaException, PangeaAPIException, ConfigException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg = new Config.Builder("notarealtoken", cfg.getDomain()).build();
		EmbargoClient fakeClient = new EmbargoClient.Builder(cfg).build();
		IPCheckResponse response = fakeClient.ipCheck("1.1.1.1");
	}
}
