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
import cloud.pangeacyber.pangea.intel.models.*;
import cloud.pangeacyber.pangea.intel.requests.IPDomainRequest;
import cloud.pangeacyber.pangea.intel.requests.IPGeolocateRequest;
import cloud.pangeacyber.pangea.intel.requests.IPProxyRequest;
import cloud.pangeacyber.pangea.intel.requests.IPReputationRequest;
import cloud.pangeacyber.pangea.intel.requests.IPVPNRequest;
import cloud.pangeacyber.pangea.intel.responses.IPDomainResponse;
import cloud.pangeacyber.pangea.intel.responses.IPGeolocateResponse;
import cloud.pangeacyber.pangea.intel.responses.IPProxyResponse;
import cloud.pangeacyber.pangea.intel.responses.IPReputationResponse;
import cloud.pangeacyber.pangea.intel.responses.IPVPNResponse;
import org.junit.Before;
import org.junit.Test;

public class ITIPIntelTest {

	IPIntelClient client;
	TestEnvironment environment = TestEnvironment.LIVE;

	@Before
	public void setUp() throws ConfigException {
		client = new IPIntelClient.Builder(Config.fromIntegrationEnvironment(environment)).build();
	}

	@Test
	public void testIpReputationMalicious_1() throws PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, not raw by default;
		IPReputationResponse response = client.reputation(new IPReputationRequest.Builder("93.231.182.110").build());
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpReputationMalicious_2() throws PangeaException, PangeaAPIException {
		// With provider, not verbose by default, not raw by default;
		IPReputationResponse response = client.reputation(
			new IPReputationRequest.Builder("93.231.182.110").provider("crowdstrike").build()
		);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpReputationMalicious_3() throws PangeaException, PangeaAPIException {
		// Default provider, no verbose, no raw;
		IPReputationResponse response = client.reputation(
			new IPReputationRequest.Builder("93.231.182.110").verbose(false).raw(false).build()
		);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpReputationMalicious_4() throws PangeaException, PangeaAPIException {
		// Default provider, verbose, no raw;
		IPReputationResponse response = client.reputation(
			new IPReputationRequest.Builder("93.231.182.110").verbose(true).raw(false).build()
		);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNotNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpReputationMalicious_5() throws PangeaException, PangeaAPIException {
		// Default provider, no verbose, raw;
		IPReputationResponse response = client.reputation(
			new IPReputationRequest.Builder("93.231.182.110").verbose(false).raw(true).build()
		);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testIpReputationMalicious_6() throws PangeaException, PangeaAPIException {
		// Default provider, verbose, raw;
		IPReputationResponse response = client.reputation(
			new IPReputationRequest.Builder("93.231.182.110").verbose(true).raw(true).build()
		);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testIpReputationMalicious_7() throws PangeaException, PangeaAPIException {
		// Provider, no verbose, no raw
		IPReputationResponse response = client.reputation(
			new IPReputationRequest.Builder("93.231.182.110").provider("crowdstrike").verbose(false).raw(false).build()
		);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpReputationMalicious_8() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		IPReputationResponse response = client.reputation(
			new IPReputationRequest.Builder("93.231.182.110").provider("crowdstrike").verbose(true).raw(true).build()
		);
		assertTrue(response.isOk());
		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testIpReputation_CymruProvider() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		IPReputationResponse response = client.reputation(
			new IPReputationRequest.Builder("93.231.182.110").provider("cymru").raw(true).verbose(true).build()
		);
		assertTrue(response.isOk());
		assertNotNull(response.getResult().getData());
		assertNotNull(response.getResult().getParameters());
	}

	@Test
	public void testIpGeolocate_1() throws PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, not raw by default;
		IPGeolocateResponse response = client.geolocate(new IPGeolocateRequest.Builder("93.231.182.110").build());
		assertTrue(response.isOk());

		IPGeolocateData data = response.getResult().getData();
		assertEquals("Federal Republic Of Germany", data.getCountry());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpGeolocate_2() throws PangeaException, PangeaAPIException {
		// With provider, not verbose by default, not raw by default;
		IPGeolocateResponse response = client.geolocate(
			new IPGeolocateRequest.Builder("93.231.182.110").provider("digitalelement").build()
		);
		assertTrue(response.isOk());

		IPGeolocateData data = response.getResult().getData();
		assertEquals("Federal Republic Of Germany", data.getCountry());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpGeolocate_3() throws PangeaException, PangeaAPIException {
		// Default provider, no verbose, no raw;
		IPGeolocateResponse response = client.geolocate(
			new IPGeolocateRequest.Builder("93.231.182.110").verbose(false).raw(false).build()
		);
		assertTrue(response.isOk());

		IPGeolocateData data = response.getResult().getData();
		assertEquals("Federal Republic Of Germany", data.getCountry());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpGeolocate_4() throws PangeaException, PangeaAPIException {
		// Default provider, verbose, no raw;
		IPGeolocateResponse response = client.geolocate(
			new IPGeolocateRequest.Builder("93.231.182.110").verbose(true).raw(false).build()
		);
		assertTrue(response.isOk());

		IPGeolocateData data = response.getResult().getData();
		assertEquals("Federal Republic Of Germany", data.getCountry());
		assertNotNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpGeolocate_5() throws PangeaException, PangeaAPIException {
		// Default provider, no verbose, raw;
		IPGeolocateResponse response = client.geolocate(
			new IPGeolocateRequest.Builder("93.231.182.110").verbose(false).raw(true).build()
		);
		assertTrue(response.isOk());

		IPGeolocateData data = response.getResult().getData();
		assertEquals("Federal Republic Of Germany", data.getCountry());
		assertNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testIpGeolocate_6() throws PangeaException, PangeaAPIException {
		// Default provider, verbose, raw;
		IPGeolocateResponse response = client.geolocate(
			new IPGeolocateRequest.Builder("93.231.182.110").verbose(true).raw(true).build()
		);
		assertTrue(response.isOk());

		IPGeolocateData data = response.getResult().getData();
		assertEquals("Federal Republic Of Germany", data.getCountry());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testIpGeolocate_7() throws PangeaException, PangeaAPIException {
		// Provider, no verbose, no raw
		IPGeolocateResponse response = client.geolocate(
			new IPGeolocateRequest.Builder("93.231.182.110")
				.provider("digitalelement")
				.verbose(false)
				.raw(false)
				.build()
		);
		assertTrue(response.isOk());

		IPGeolocateData data = response.getResult().getData();
		assertEquals("Federal Republic Of Germany", data.getCountry());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpGeolocate_8() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		IPGeolocateResponse response = client.geolocate(
			new IPGeolocateRequest.Builder("93.231.182.110").provider("digitalelement").verbose(true).raw(true).build()
		);
		assertTrue(response.isOk());

		IPGeolocateData data = response.getResult().getData();
		assertEquals("Federal Republic Of Germany", data.getCountry());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testIpDomain_1() throws PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, not raw by default;
		IPDomainResponse response = client.getDomain(new IPDomainRequest.Builder("24.235.114.61").build());
		assertTrue(response.isOk());

		IPDomainData data = response.getResult().getData();
		assertTrue(data.isDomainFound());
		assertEquals("rogers.com", data.getDomain());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpDomain_2() throws PangeaException, PangeaAPIException {
		// With provider, not verbose by default, not raw by default;
		IPDomainResponse response = client.getDomain(
			new IPDomainRequest.Builder("24.235.114.61").provider("digitalelement").build()
		);
		assertTrue(response.isOk());

		IPDomainData data = response.getResult().getData();
		assertTrue(data.isDomainFound());
		assertEquals("rogers.com", data.getDomain());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpDomain_3() throws PangeaException, PangeaAPIException {
		// Default provider, no verbose, no raw;
		IPDomainResponse response = client.getDomain(
			new IPDomainRequest.Builder("24.235.114.61").verbose(false).raw(false).build()
		);
		assertTrue(response.isOk());

		IPDomainData data = response.getResult().getData();
		assertTrue(data.isDomainFound());
		assertEquals("rogers.com", data.getDomain());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpDomain_4() throws PangeaException, PangeaAPIException {
		// Default provider, verbose, no raw;
		IPDomainResponse response = client.getDomain(
			new IPDomainRequest.Builder("24.235.114.61").verbose(true).raw(false).build()
		);
		assertTrue(response.isOk());

		IPDomainData data = response.getResult().getData();
		assertTrue(data.isDomainFound());
		assertEquals("rogers.com", data.getDomain());
		assertNotNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpDomain_5() throws PangeaException, PangeaAPIException {
		// Default provider, no verbose, raw;
		IPDomainResponse response = client.getDomain(
			new IPDomainRequest.Builder("24.235.114.61").provider("digitalelement").verbose(false).raw(true).build()
		);
		assertTrue(response.isOk());

		IPDomainData data = response.getResult().getData();
		assertTrue(data.isDomainFound());
		assertEquals("rogers.com", data.getDomain());
		assertNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testIpDomain_6() throws PangeaException, PangeaAPIException {
		// Default provider, verbose, raw;
		IPDomainResponse response = client.getDomain(
			new IPDomainRequest.Builder("24.235.114.61").verbose(true).raw(true).build()
		);
		assertTrue(response.isOk());

		IPDomainData data = response.getResult().getData();
		assertTrue(data.isDomainFound());
		assertEquals("rogers.com", data.getDomain());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testIpDomain_7() throws PangeaException, PangeaAPIException {
		// Provider, no verbose, no raw
		IPDomainResponse response = client.getDomain(
			new IPDomainRequest.Builder("24.235.114.61").provider("digitalelement").verbose(false).raw(false).build()
		);
		assertTrue(response.isOk());

		IPDomainData data = response.getResult().getData();
		assertTrue(data.isDomainFound());
		assertEquals("rogers.com", data.getDomain());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpDomain_8() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		IPDomainResponse response = client.getDomain(
			new IPDomainRequest.Builder("24.235.114.61").provider("digitalelement").verbose(true).raw(true).build()
		);
		assertTrue(response.isOk());

		IPDomainData data = response.getResult().getData();
		assertTrue(data.isDomainFound());
		assertEquals("rogers.com", data.getDomain());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testIpVPN_1() throws PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, not raw by default;
		IPVPNResponse response = client.isVPN(new IPVPNRequest.Builder("2.56.189.74").build());
		assertTrue(response.isOk());

		IPVPNData data = response.getResult().getData();
		assertTrue(data.isVPN());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpVPN_2() throws PangeaException, PangeaAPIException {
		// With provider, not verbose by default, not raw by default;
		IPVPNResponse response = client.isVPN(
			new IPVPNRequest.Builder("2.56.189.74").provider("digitalelement").build()
		);
		assertTrue(response.isOk());

		IPVPNData data = response.getResult().getData();
		assertTrue(data.isVPN());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpVPN_3() throws PangeaException, PangeaAPIException {
		// Default provider, no verbose, no raw;
		IPVPNResponse response = client.isVPN(
			new IPVPNRequest.Builder("2.56.189.74").verbose(false).raw(false).build()
		);
		assertTrue(response.isOk());

		IPVPNData data = response.getResult().getData();
		assertTrue(data.isVPN());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpVPN_4() throws PangeaException, PangeaAPIException {
		// Default provider, verbose, no raw;
		IPVPNResponse response = client.isVPN(new IPVPNRequest.Builder("2.56.189.74").verbose(true).raw(false).build());
		assertTrue(response.isOk());

		IPVPNData data = response.getResult().getData();
		assertTrue(data.isVPN());
		assertNotNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpVPN_5() throws PangeaException, PangeaAPIException {
		// Default provider, no verbose, raw;
		IPVPNResponse response = client.isVPN(new IPVPNRequest.Builder("2.56.189.74").verbose(false).raw(true).build());
		assertTrue(response.isOk());

		IPVPNData data = response.getResult().getData();
		assertTrue(data.isVPN());
		assertNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testIpVPN_6() throws PangeaException, PangeaAPIException {
		// Default provider, verbose, raw;
		IPVPNResponse response = client.isVPN(new IPVPNRequest.Builder("2.56.189.74").verbose(true).raw(true).build());
		assertTrue(response.isOk());

		IPVPNData data = response.getResult().getData();
		assertTrue(data.isVPN());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testIpVPN_7() throws PangeaException, PangeaAPIException {
		// Provider, no verbose, no raw
		IPVPNResponse response = client.isVPN(
			new IPVPNRequest.Builder("2.56.189.74").provider("digitalelement").verbose(false).raw(false).build()
		);
		assertTrue(response.isOk());

		IPVPNData data = response.getResult().getData();
		assertTrue(data.isVPN());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpVPN_8() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		IPVPNResponse response = client.isVPN(
			new IPVPNRequest.Builder("2.56.189.74").provider("digitalelement").verbose(true).raw(true).build()
		);

		assertTrue(response.isOk());

		IPVPNData data = response.getResult().getData();
		assertTrue(data.isVPN());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testIpProxy_1() throws PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, not raw by default;
		IPProxyResponse response = client.isProxy(new IPProxyRequest.Builder("34.201.32.172").build());

		assertTrue(response.isOk());

		IPProxyData data = response.getResult().getData();
		assertTrue(data.isProxy());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpProxy_2() throws PangeaException, PangeaAPIException {
		// With provider, not verbose by default, not raw by default;
		IPProxyResponse response = client.isProxy(
			new IPProxyRequest.Builder("34.201.32.172").provider("digitalelement").build()
		);

		assertTrue(response.isOk());

		IPProxyData data = response.getResult().getData();
		assertTrue(data.isProxy());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpProxy_3() throws PangeaException, PangeaAPIException {
		// Default provider, no verbose, no raw;
		IPProxyResponse response = client.isProxy(
			new IPProxyRequest.Builder("34.201.32.172").verbose(false).raw(false).build()
		);

		assertTrue(response.isOk());

		IPProxyData data = response.getResult().getData();
		assertTrue(data.isProxy());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpProxy_4() throws PangeaException, PangeaAPIException {
		// Default provider, verbose, no raw;
		IPProxyResponse response = client.isProxy(
			new IPProxyRequest.Builder("34.201.32.172").verbose(true).raw(false).build()
		);

		assertTrue(response.isOk());

		IPProxyData data = response.getResult().getData();
		assertTrue(data.isProxy());
		assertNotNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpProxy_5() throws PangeaException, PangeaAPIException {
		// Default provider, no verbose, raw;
		IPProxyResponse response = client.isProxy(
			new IPProxyRequest.Builder("34.201.32.172").verbose(false).raw(true).build()
		);

		assertTrue(response.isOk());

		IPProxyData data = response.getResult().getData();
		assertTrue(data.isProxy());
		assertNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testIpProxy_6() throws PangeaException, PangeaAPIException {
		// Default provider, verbose, raw;
		IPProxyResponse response = client.isProxy(
			new IPProxyRequest.Builder("34.201.32.172").verbose(true).raw(true).build()
		);

		assertTrue(response.isOk());

		IPProxyData data = response.getResult().getData();
		assertTrue(data.isProxy());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testIpProxy_7() throws PangeaException, PangeaAPIException {
		// Provider, no verbose, no raw
		IPProxyResponse response = client.isProxy(
			new IPProxyRequest.Builder("34.201.32.172").provider("digitalelement").verbose(false).raw(false).build()
		);

		assertTrue(response.isOk());

		IPProxyData data = response.getResult().getData();
		assertTrue(data.isProxy());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIpProxy_8() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		IPProxyResponse response = client.isProxy(
			new IPProxyRequest.Builder("34.201.32.172").provider("digitalelement").verbose(true).raw(true).build()
		);
		assertTrue(response.isOk());

		IPProxyData data = response.getResult().getData();
		assertTrue(data.isProxy());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test(expected = ValidationException.class)
	public void testEmptyIP() throws PangeaException, PangeaAPIException {
		IPReputationResponse response = client.reputation(
			new IPReputationRequest.Builder("").provider("crowdstrike").verbose(false).raw(false).build()
		);
	}

	@Test(expected = ValidationException.class)
	public void testEmptyProvider() throws PangeaException, PangeaAPIException {
		IPReputationResponse response = client.reputation(
			new IPReputationRequest.Builder("93.231.182.110").provider("").verbose(false).raw(false).build()
		);
	}

	@Test(expected = ValidationException.class)
	public void testEmptyNotValidProvider() throws PangeaException, PangeaAPIException {
		IPReputationResponse response = client.reputation(
			new IPReputationRequest.Builder("93.231.182.110")
				.provider("notavalidprovider")
				.verbose(false)
				.raw(false)
				.build()
		);
	}

	@Test(expected = UnauthorizedException.class)
	public void testUnauthorized() throws PangeaException, PangeaAPIException, ConfigException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg.setToken("notarealtoken");
		IPIntelClient fakeClient = new IPIntelClient.Builder(cfg).build();
		IPReputationResponse response = fakeClient.reputation(
			new IPReputationRequest.Builder("93.231.182.110").provider("crowdstrike").verbose(false).raw(false).build()
		);
	}
}
