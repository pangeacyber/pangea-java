package cloud.pangeacyber.pangea.intel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.UnauthorizedException;
import cloud.pangeacyber.pangea.exceptions.ValidationException;

public class ITIPIntelTest {
    IpIntelClient client;
    // FIXME: Update enviroment to LIVE once in prod
    TestEnvironment environment = TestEnvironment.DEVELOP;

    @Before
    public void setUp() throws ConfigException{
        client = new IpIntelClient(Config.fromIntegrationEnvironment(environment));
    }

    @Test
    public void testIpLookupMalicious_1() throws PangeaException, PangeaAPIException {
        // Default provider, not verbose by default, not raw by default;
        IpLookupResponse response = client.lookup("93.231.182.110");
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpLookupMalicious_2() throws PangeaException, PangeaAPIException {
        // With provider, not verbose by default, not raw by default;
        IpLookupResponse response = client.lookup("93.231.182.110", "crowdstrike");
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpLookupMalicious_3() throws PangeaException, PangeaAPIException {
        // Default provider, no verbose, no raw;
        IpLookupResponse response = client.lookup("93.231.182.110", false, false);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpLookupMalicious_4() throws PangeaException, PangeaAPIException {
        // Default provider, verbose, no raw;
        IpLookupResponse response = client.lookup("93.231.182.110", true, false);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNotNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpLookupMalicious_5() throws PangeaException, PangeaAPIException {
        // Default provider, no verbose, raw;
        IpLookupResponse response = client.lookup("93.231.182.110", false, true);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testIpLookupMalicious_6() throws PangeaException, PangeaAPIException {
        // Default provider, verbose, raw;
        IpLookupResponse response = client.lookup("93.231.182.110", true, true);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testIpLookupMalicious_7() throws PangeaException, PangeaAPIException {
        // Provider, no verbose, no raw
        IpLookupResponse response = client.lookup("93.231.182.110", "crowdstrike", false, false);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpLookupMalicious_8() throws PangeaException, PangeaAPIException {
        // Provider, verbose, raw
        IpLookupResponse response = client.lookup("93.231.182.110", "crowdstrike", true, true);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testIpGeolocate_1() throws PangeaException, PangeaAPIException {
        // Default provider, not verbose by default, not raw by default;
        IpGeolocateResponse response = client.geolocate("93.231.182.110");
        assertTrue(response.isOk());

        IpGeolocateData data = response.getResult().getData();
        assertEquals("deu", data.getCountry());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpGeolocate_2() throws PangeaException, PangeaAPIException {
        // With provider, not verbose by default, not raw by default;
        IpGeolocateResponse response = client.geolocate("93.231.182.110", "digitalenvoy");
        assertTrue(response.isOk());

        IpGeolocateData data = response.getResult().getData();
        assertEquals("deu", data.getCountry());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpGeolocate_3() throws PangeaException, PangeaAPIException {
        // Default provider, no verbose, no raw;
        IpGeolocateResponse response = client.geolocate("93.231.182.110", false, false);
        assertTrue(response.isOk());

        IpGeolocateData data = response.getResult().getData();
        assertEquals("deu", data.getCountry());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpGeolocate_4() throws PangeaException, PangeaAPIException {
        // Default provider, verbose, no raw;
        IpGeolocateResponse response = client.geolocate("93.231.182.110", true, false);
        assertTrue(response.isOk());

        IpGeolocateData data = response.getResult().getData();
        assertEquals("deu", data.getCountry());
        assertNotNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpGeolocate_5() throws PangeaException, PangeaAPIException {
        // Default provider, no verbose, raw;
        IpGeolocateResponse response = client.geolocate("93.231.182.110", false, true);
        assertTrue(response.isOk());

        IpGeolocateData data = response.getResult().getData();
        assertEquals("deu", data.getCountry());
        assertNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testIpGeolocate_6() throws PangeaException, PangeaAPIException {
        // Default provider, verbose, raw;
        IpGeolocateResponse response = client.geolocate("93.231.182.110", true, true);
        assertTrue(response.isOk());

        IpGeolocateData data = response.getResult().getData();
        assertEquals("deu", data.getCountry());
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testIpGeolocate_7() throws PangeaException, PangeaAPIException {
        // Provider, no verbose, no raw
        IpGeolocateResponse response = client.geolocate("93.231.182.110", "digitalenvoy", false, false);
        assertTrue(response.isOk());

        IpGeolocateData data = response.getResult().getData();
        assertEquals("deu", data.getCountry());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpGeolocate_8() throws PangeaException, PangeaAPIException {
        // Provider, verbose, raw
        IpGeolocateResponse response = client.geolocate("93.231.182.110", "digitalenvoy", true, true);
        assertTrue(response.isOk());

        IpGeolocateData data = response.getResult().getData();
        assertEquals("deu", data.getCountry());
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testIpDomain_1() throws PangeaException, PangeaAPIException {
        // Default provider, not verbose by default, not raw by default;
        IpDomainResponse response = client.getDomain("24.235.114.61");
        assertTrue(response.isOk());

        IpDomainData data = response.getResult().getData();
        assertTrue(data.isDomainFound());
        assertEquals("rogers.com", data.getDomain());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpDomain_2() throws PangeaException, PangeaAPIException {
        // With provider, not verbose by default, not raw by default;
        IpDomainResponse response = client.getDomain("24.235.114.61", "digitalenvoy");
        assertTrue(response.isOk());

        IpDomainData data = response.getResult().getData();
        assertTrue(data.isDomainFound());
        assertEquals("rogers.com", data.getDomain());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpDomain_3() throws PangeaException, PangeaAPIException {
        // Default provider, no verbose, no raw;
        IpDomainResponse response = client.getDomain("24.235.114.61", false, false);
        assertTrue(response.isOk());

        IpDomainData data = response.getResult().getData();
        assertTrue(data.isDomainFound());
        assertEquals("rogers.com", data.getDomain());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpDomain_4() throws PangeaException, PangeaAPIException {
        // Default provider, verbose, no raw;
        IpDomainResponse response = client.getDomain("24.235.114.61", true, false);
        assertTrue(response.isOk());

        IpDomainData data = response.getResult().getData();
        assertTrue(data.isDomainFound());
        assertEquals("rogers.com", data.getDomain());
        assertNotNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpDomain_5() throws PangeaException, PangeaAPIException {
        // Default provider, no verbose, raw;
        IpDomainResponse response = client.getDomain("24.235.114.61", false, true);
        assertTrue(response.isOk());

        IpDomainData data = response.getResult().getData();
        assertTrue(data.isDomainFound());
        assertEquals("rogers.com", data.getDomain());
        assertNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testIpDomain_6() throws PangeaException, PangeaAPIException {
        // Default provider, verbose, raw;
        IpDomainResponse response = client.getDomain("24.235.114.61", true, true);
        assertTrue(response.isOk());

        IpDomainData data = response.getResult().getData();
            assertTrue(data.isDomainFound());
        assertEquals("rogers.com", data.getDomain());
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testIpDomain_7() throws PangeaException, PangeaAPIException {
        // Provider, no verbose, no raw
        IpDomainResponse response = client.getDomain("24.235.114.61", "digitalenvoy", false, false);
        assertTrue(response.isOk());

        IpDomainData data = response.getResult().getData();
            assertTrue(data.isDomainFound());
        assertEquals("rogers.com", data.getDomain());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpDomain_8() throws PangeaException, PangeaAPIException {
        // Provider, verbose, raw
        IpDomainResponse response = client.getDomain("24.235.114.61", "digitalenvoy", true, true);
        assertTrue(response.isOk());

        IpDomainData data = response.getResult().getData();
        assertTrue(data.isDomainFound());
        assertEquals("rogers.com", data.getDomain());
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testIpVPN_1() throws PangeaException, PangeaAPIException {
        // Default provider, not verbose by default, not raw by default;
        IpVPNResponse response = client.isVPN("1.46.128.165");
        assertTrue(response.isOk());

        IpVPNData data = response.getResult().getData();
        assertTrue(data.isVPN());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpVPN_2() throws PangeaException, PangeaAPIException {
        // With provider, not verbose by default, not raw by default;
        IpVPNResponse response = client.isVPN("1.46.128.165", "digitalenvoy");
        assertTrue(response.isOk());

        IpVPNData data = response.getResult().getData();
        assertTrue(data.isVPN());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpVPN_3() throws PangeaException, PangeaAPIException {
        // Default provider, no verbose, no raw;
        IpVPNResponse response = client.isVPN("1.46.128.165", false, false);
        assertTrue(response.isOk());

        IpVPNData data = response.getResult().getData();
        assertTrue(data.isVPN());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpVPN_4() throws PangeaException, PangeaAPIException {
        // Default provider, verbose, no raw;
        IpVPNResponse response = client.isVPN("1.46.128.165", true, false);
        assertTrue(response.isOk());

        IpVPNData data = response.getResult().getData();
        assertTrue(data.isVPN());
        assertNotNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpVPN_5() throws PangeaException, PangeaAPIException {
        // Default provider, no verbose, raw;
        IpVPNResponse response = client.isVPN("1.46.128.165", false, true);
        assertTrue(response.isOk());

        IpVPNData data = response.getResult().getData();
        assertTrue(data.isVPN());
        assertNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testIpVPN_6() throws PangeaException, PangeaAPIException {
        // Default provider, verbose, raw;
        IpVPNResponse response = client.isVPN("1.46.128.165", true, true);
        assertTrue(response.isOk());

        IpVPNData data = response.getResult().getData();
        assertTrue(data.isVPN());
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testIpVPN_7() throws PangeaException, PangeaAPIException {
        // Provider, no verbose, no raw
        IpVPNResponse response = client.isVPN("1.46.128.165", "digitalenvoy", false, false);
        assertTrue(response.isOk());

        IpVPNData data = response.getResult().getData();
        assertTrue(data.isVPN());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpVPN_8() throws PangeaException, PangeaAPIException {
        // Provider, verbose, raw
        IpVPNResponse response = client.isVPN("1.46.128.165", "digitalenvoy", true, true);
        assertTrue(response.isOk());

        IpVPNData data = response.getResult().getData();
        assertTrue(data.isVPN());
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testIpProxy_1() throws PangeaException, PangeaAPIException {
        // Default provider, not verbose by default, not raw by default;
        IpProxyResponse response = client.isProxy("34.201.32.172");
        assertTrue(response.isOk());

        IpProxyData data = response.getResult().getData();
        assertTrue(data.isProxy());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpProxy_2() throws PangeaException, PangeaAPIException {
        // With provider, not verbose by default, not raw by default;
        IpProxyResponse response = client.isProxy("34.201.32.172", "digitalenvoy");
        assertTrue(response.isOk());

        IpProxyData data = response.getResult().getData();
        assertTrue(data.isProxy());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpProxy_3() throws PangeaException, PangeaAPIException {
        // Default provider, no verbose, no raw;
        IpProxyResponse response = client.isProxy("34.201.32.172", false, false);
        assertTrue(response.isOk());

        IpProxyData data = response.getResult().getData();
        assertTrue(data.isProxy());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpProxy_4() throws PangeaException, PangeaAPIException {
        // Default provider, verbose, no raw;
        IpProxyResponse response = client.isProxy("34.201.32.172", true, false);
        assertTrue(response.isOk());

        IpProxyData data = response.getResult().getData();
        assertTrue(data.isProxy());
        assertNotNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpProxy_5() throws PangeaException, PangeaAPIException {
        // Default provider, no verbose, raw;
        IpProxyResponse response = client.isProxy("34.201.32.172", false, true);
        assertTrue(response.isOk());

        IpProxyData data = response.getResult().getData();
        assertTrue(data.isProxy());
        assertNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testIpProxy_6() throws PangeaException, PangeaAPIException {
        // Default provider, verbose, raw;
        IpProxyResponse response = client.isProxy("34.201.32.172", true, true);
        assertTrue(response.isOk());

        IpProxyData data = response.getResult().getData();
        assertTrue(data.isProxy());
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testIpProxy_7() throws PangeaException, PangeaAPIException {
        // Provider, no verbose, no raw
        IpProxyResponse response = client.isProxy("34.201.32.172", "digitalenvoy", false, false);
        assertTrue(response.isOk());

        IpProxyData data = response.getResult().getData();
        assertTrue(data.isProxy());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpProxy_8() throws PangeaException, PangeaAPIException {
        // Provider, verbose, raw
        IpProxyResponse response = client.isProxy("34.201.32.172", "digitalenvoy", true, true);
        assertTrue(response.isOk());

        IpProxyData data = response.getResult().getData();
        assertTrue(data.isProxy());
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test(expected = ValidationException.class)
    public void testEmptyIP() throws PangeaException, PangeaAPIException {
        IpLookupResponse response = client.lookup("", "crowdstrike", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testEmptyProvider() throws PangeaException, PangeaAPIException {
        IpLookupResponse response = client.lookup("93.231.182.110", "", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testEmptyNotValidProvider() throws PangeaException, PangeaAPIException {
        IpLookupResponse response = client.lookup("93.231.182.110", "notvalidprovider", true, true);
    }

    @Test(expected = UnauthorizedException.class)
    public void testUnauthorized() throws PangeaException, PangeaAPIException, ConfigException{
        Config cfg = Config.fromIntegrationEnvironment(environment);
        cfg.setToken("notarealtoken");
        IpIntelClient fakeClient = new IpIntelClient(cfg);
        IpLookupResponse response = fakeClient.lookup("93.231.182.110");
    }

}
