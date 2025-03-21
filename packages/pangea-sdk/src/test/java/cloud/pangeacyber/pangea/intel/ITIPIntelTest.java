package cloud.pangeacyber.pangea.intel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import cloud.pangeacyber.pangea.intel.models.*;
import cloud.pangeacyber.pangea.intel.requests.IPDomainBulkRequest;
import cloud.pangeacyber.pangea.intel.requests.IPDomainRequest;
import cloud.pangeacyber.pangea.intel.requests.IPGeolocateBulkRequest;
import cloud.pangeacyber.pangea.intel.requests.IPGeolocateRequest;
import cloud.pangeacyber.pangea.intel.requests.IPProxyBulkRequest;
import cloud.pangeacyber.pangea.intel.requests.IPProxyRequest;
import cloud.pangeacyber.pangea.intel.requests.IPReputationBulkRequest;
import cloud.pangeacyber.pangea.intel.requests.IPReputationRequest;
import cloud.pangeacyber.pangea.intel.requests.IPVPNBulkRequest;
import cloud.pangeacyber.pangea.intel.requests.IPVPNRequest;
import cloud.pangeacyber.pangea.intel.responses.IPDomainBulkResponse;
import cloud.pangeacyber.pangea.intel.responses.IPDomainResponse;
import cloud.pangeacyber.pangea.intel.responses.IPGeolocateBulkResponse;
import cloud.pangeacyber.pangea.intel.responses.IPGeolocateResponse;
import cloud.pangeacyber.pangea.intel.responses.IPProxyBulkResponse;
import cloud.pangeacyber.pangea.intel.responses.IPProxyResponse;
import cloud.pangeacyber.pangea.intel.responses.IPReputationBulkResponse;
import cloud.pangeacyber.pangea.intel.responses.IPReputationResponse;
import cloud.pangeacyber.pangea.intel.responses.IPVPNBulkResponse;
import cloud.pangeacyber.pangea.intel.responses.IPVPNResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ITIPIntelTest {

	IPIntelClient client;
	static TestEnvironment environment;

	@BeforeAll
	public static void setUpClass() throws Exception {
		environment = Helper.loadTestEnvironment("ip-intel", TestEnvironment.LIVE);
	}

	@BeforeEach
	public void setUp() throws Exception {
		client = new IPIntelClient.Builder(Config.fromIntegrationEnvironment(environment)).build();
	}

	@Test
	public void testIPReputationMalicious_1() throws PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, not raw by default;
		IPReputationResponse response = client.reputation(new IPReputationRequest.Builder("93.231.182.110").build());
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIPReputationMalicious_2() throws PangeaException, PangeaAPIException {
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
	public void testIPReputationMalicious_3() throws PangeaException, PangeaAPIException {
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
	public void testIPReputationMalicious_4() throws PangeaException, PangeaAPIException {
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
	public void testIPReputationMalicious_5() throws PangeaException, PangeaAPIException {
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
	public void testIPReputationMalicious_6() throws PangeaException, PangeaAPIException {
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
	public void testIPReputationMalicious_7() throws PangeaException, PangeaAPIException {
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
	public void testIPReputationMalicious_8() throws PangeaException, PangeaAPIException {
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
	public void testIPReputationBulk() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		String[] ips = { "93.231.182.110", "190.28.74.251" };
		IPReputationBulkResponse response = client.reputationBulk(
			new IPReputationBulkRequest.Builder(ips).provider("crowdstrike").verbose(true).raw(true).build()
		);
		assertTrue(response.isOk());

		IPReputationBulkData data = response.getResult().getData();
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
		assertEquals(2, data.size());
	}

	@Test
	public void testIPReputationNotFound() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		IPReputationResponse response = client.reputation(
			new IPReputationRequest.Builder("127.0.0.1").provider("crowdstrike").verbose(true).raw(true).build()
		);
		assertTrue(response.isOk());
		IntelReputationData data = response.getResult().getData();
		assertNotNull(data);
		assertNotNull(data.getCategory());
		assertNotNull(data.getScore());
		assertNotNull(data.getVerdict());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testIPReputation_CymruProvider() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		IPReputationResponse response = client.reputation(
			new IPReputationRequest.Builder("93.231.182.110").provider("cymru").raw(true).verbose(true).build()
		);
		assertTrue(response.isOk());
		assertNotNull(response.getResult().getData());
		assertNotNull(response.getResult().getParameters());
	}

	@Test
	public void testIPGeolocate_1() throws PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, not raw by default;
		IPGeolocateResponse response = client.geolocate(new IPGeolocateRequest.Builder("93.231.182.110").build());
		assertTrue(response.isOk());

		IPGeolocateData data = response.getResult().getData();
		assertEquals("Federal Republic Of Germany", data.getCountry());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIPGeolocate_2() throws PangeaException, PangeaAPIException {
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
	public void testIPGeolocate_3() throws PangeaException, PangeaAPIException {
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
	public void testIPGeolocate_4() throws PangeaException, PangeaAPIException {
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
	public void testIPGeolocate_5() throws PangeaException, PangeaAPIException {
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
	public void testIPGeolocate_6() throws PangeaException, PangeaAPIException {
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
	public void testIPGeolocate_7() throws PangeaException, PangeaAPIException {
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
	public void testIPGeolocate_8() throws PangeaException, PangeaAPIException {
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
	public void testIPGeolocateBulk() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		String[] ips = { "93.231.182.110", "190.28.74.251" };
		IPGeolocateBulkResponse response = client.geolocateBulk(
			new IPGeolocateBulkRequest.Builder(ips).verbose(true).raw(true).build()
		);
		assertTrue(response.isOk());

		IPGeolocateBulkData data = response.getResult().getData();
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
		assertEquals(2, data.size());
	}

	@Test
	public void testIPDomain_1() throws PangeaException, PangeaAPIException {
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
	public void testIPDomain_2() throws PangeaException, PangeaAPIException {
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
	public void testIPDomain_3() throws PangeaException, PangeaAPIException {
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
	public void testIPDomain_4() throws PangeaException, PangeaAPIException {
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
	public void testIPDomain_5() throws PangeaException, PangeaAPIException {
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
	public void testIPDomain_6() throws PangeaException, PangeaAPIException {
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
	public void testIPDomain_7() throws PangeaException, PangeaAPIException {
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
	public void testIPDomain_8() throws PangeaException, PangeaAPIException {
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
	public void testIPDomainBulk() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		String[] ips = { "93.231.182.110", "190.28.74.251" };
		IPDomainBulkResponse response = client.getDomainBulk(
			new IPDomainBulkRequest.Builder(ips).verbose(true).raw(true).build()
		);
		assertTrue(response.isOk());

		IPDomainBulkData data = response.getResult().getData();
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
		assertEquals(2, data.size());
	}

	@Test
	public void testIPDomainNotFound() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		IPDomainResponse response = client.getDomain(
			new IPDomainRequest.Builder("127.0.0.1").provider("digitalelement").verbose(true).raw(true).build()
		);
		assertTrue(response.isOk());

		IPDomainData data = response.getResult().getData();
		assertNotNull(data);
		assertFalse(data.isDomainFound());
		assertFalse(data.isDomainFound());
		assertNull(data.getDomain());
		assertNotNull(response.getResult().getParameters());
	}

	@Test
	public void testIPVPN_1() throws PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, not raw by default;
		IPVPNResponse response = client.isVPN(new IPVPNRequest.Builder("2.56.189.74").build());
		assertTrue(response.isOk());

		IPVPNData data = response.getResult().getData();
		assertTrue(data.isVPN());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIPVPN_2() throws PangeaException, PangeaAPIException {
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
	public void testIPVPN_3() throws PangeaException, PangeaAPIException {
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
	public void testIPVPN_4() throws PangeaException, PangeaAPIException {
		// Default provider, verbose, no raw;
		IPVPNResponse response = client.isVPN(new IPVPNRequest.Builder("2.56.189.74").verbose(true).raw(false).build());
		assertTrue(response.isOk());

		IPVPNData data = response.getResult().getData();
		assertTrue(data.isVPN());
		assertNotNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIPVPN_5() throws PangeaException, PangeaAPIException {
		// Default provider, no verbose, raw;
		IPVPNResponse response = client.isVPN(new IPVPNRequest.Builder("2.56.189.74").verbose(false).raw(true).build());
		assertTrue(response.isOk());

		IPVPNData data = response.getResult().getData();
		assertTrue(data.isVPN());
		assertNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testIPVPN_6() throws PangeaException, PangeaAPIException {
		// Default provider, verbose, raw;
		IPVPNResponse response = client.isVPN(new IPVPNRequest.Builder("2.56.189.74").verbose(true).raw(true).build());
		assertTrue(response.isOk());

		IPVPNData data = response.getResult().getData();
		assertTrue(data.isVPN());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testIPVPN_7() throws PangeaException, PangeaAPIException {
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
	public void testIPVPN_8() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		IPVPNResponse response = client.isVPN(
			new IPVPNRequest.Builder("2.56.189.74").provider("digitalelement").verbose(true).raw(true).build()
		);

		assertTrue(response.isOk());

		IPVPNData data = response.getResult().getData();
		assertNotNull(data);
		assertTrue(data.isVPN());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testIPVPNBulk() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		String[] ips = { "93.231.182.110", "190.28.74.251" };
		IPVPNBulkResponse response = client.isVPNBulk(
			new IPVPNBulkRequest.Builder(ips).verbose(true).raw(true).build()
		);
		assertTrue(response.isOk());

		IPVPNBulkData data = response.getResult().getData();
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
		assertEquals(2, data.size());
	}

	@Test
	public void testIPVPNnotFound() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		IPVPNResponse response = client.isVPN(
			new IPVPNRequest.Builder("127.0.0.1").provider("digitalelement").verbose(true).raw(true).build()
		);

		assertTrue(response.isOk());

		IPVPNData data = response.getResult().getData();
		assertFalse(data.isVPN());
		assertNotNull(response.getResult().getParameters());
	}

	@Test
	public void testIPProxy_1() throws PangeaException, PangeaAPIException {
		// Default provider, not verbose by default, not raw by default;
		IPProxyResponse response = client.isProxy(new IPProxyRequest.Builder("34.201.32.172").build());

		assertTrue(response.isOk());

		IPProxyData data = response.getResult().getData();
		assertTrue(data.isProxy());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testIPProxy_2() throws PangeaException, PangeaAPIException {
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
	public void testIPProxy_3() throws PangeaException, PangeaAPIException {
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
	public void testIPProxy_4() throws PangeaException, PangeaAPIException {
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
	public void testIPProxy_5() throws PangeaException, PangeaAPIException {
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
	public void testIPProxy_6() throws PangeaException, PangeaAPIException {
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
	public void testIPProxy_7() throws PangeaException, PangeaAPIException {
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
	public void testIPProxy_8() throws PangeaException, PangeaAPIException {
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

	@Test
	public void testIPProxyBulk() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		String[] ips = { "93.231.182.110", "190.28.74.251" };
		IPProxyBulkResponse response = client.isProxyBulk(
			new IPProxyBulkRequest.Builder(ips).verbose(true).raw(true).build()
		);
		assertTrue(response.isOk());

		IPProxyBulkData data = response.getResult().getData();
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
		assertEquals(2, data.size());
	}

	@Test
	public void testIPProxyNotFound() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw
		IPProxyResponse response = client.isProxy(
			new IPProxyRequest.Builder("127.0.0.1").provider("digitalelement").verbose(true).raw(true).build()
		);
		assertTrue(response.isOk());

		IPProxyData data = response.getResult().getData();
		assertFalse(data.isProxy());
		assertNotNull(response.getResult().getParameters());
	}

	@Test
	public void testEmptyIP() throws PangeaException, PangeaAPIException {
		assertThrows(
			ValidationException.class,
			() ->
				client.reputation(
					new IPReputationRequest.Builder("").provider("crowdstrike").verbose(false).raw(false).build()
				)
		);
	}

	@Test
	public void testEmptyProvider() throws PangeaException, PangeaAPIException {
		assertThrows(
			ValidationException.class,
			() ->
				client.reputation(
					new IPReputationRequest.Builder("93.231.182.110").provider("").verbose(false).raw(false).build()
				)
		);
	}

	@Test
	public void testEmptyNotValidProvider() throws PangeaException, PangeaAPIException {
		assertThrows(
			ValidationException.class,
			() ->
				client.reputation(
					new IPReputationRequest.Builder("93.231.182.110")
						.provider("notavalidprovider")
						.verbose(false)
						.raw(false)
						.build()
				)
		);
	}

	@Test
	public void testUnauthorized() throws PangeaException, PangeaAPIException, ConfigException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg = Config.builder().token("notarealtoken").baseURLTemplate(cfg.getBaseURLTemplate()).build();
		IPIntelClient fakeClient = new IPIntelClient.Builder(cfg).build();
		assertThrows(
			UnauthorizedException.class,
			() ->
				fakeClient.reputation(
					new IPReputationRequest.Builder("93.231.182.110")
						.provider("crowdstrike")
						.verbose(false)
						.raw(false)
						.build()
				)
		);
	}
}
