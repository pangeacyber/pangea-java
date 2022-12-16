package cloud.pangeacyber.pangea.intel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.audit.TestEnvironment;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.UnauthorizedException;
import cloud.pangeacyber.pangea.exceptions.ValidationException;

public class ITDomainIntelTest {
    DomainIntelClient client;
    TestEnvironment environment = TestEnvironment.PRODUCTION;

    @Before
    public void setUp() throws ConfigException{
         client = new DomainIntelClient(Config.fromIntegrationEnvironment(environment));
    }

    @Test
    public void testDomainLookupMalicious_1() throws PangeaException, PangeaAPIException {
        // Default provider, not verbose by default, not raw by default;
        DomainLookupResponse response = client.lookup("737updatesboeing.com");
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testDomainLookupMalicious_2() throws PangeaException, PangeaAPIException {
        // With provider, not verbose by default, not raw by default;
        DomainLookupResponse response = client.lookup("737updatesboeing.com", "domaintools");
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testDomainLookupMalicious_3() throws PangeaException, PangeaAPIException {
        // Default provider, no verbose, no raw;
        DomainLookupResponse response = client.lookup("737updatesboeing.com", false, false);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testDomainLookupMalicious_4() throws PangeaException, PangeaAPIException {
        // Default provider, verbose, no raw;
        DomainLookupResponse response = client.lookup("737updatesboeing.com", true, false);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNotNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testDomainLookupMalicious_5() throws PangeaException, PangeaAPIException {
        // Default provider, no verbose, raw;
        DomainLookupResponse response = client.lookup("737updatesboeing.com", false, true);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testDomainLookupMalicious_6() throws PangeaException, PangeaAPIException {
        // Default provider, verbose, raw;
        DomainLookupResponse response = client.lookup("737updatesboeing.com", true, true);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testDomainLookupMalicious_7() throws PangeaException, PangeaAPIException {
        // Provider, no verbose, no raw
        DomainLookupResponse response = client.lookup("737updatesboeing.com", "domaintools", false, false);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testDomainLookupMalicious_8() throws PangeaException, PangeaAPIException {
        // Provider, verbose, raw
        DomainLookupResponse response = client.lookup("737updatesboeing.com", "domaintools", true, true);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test(expected = ValidationException.class)
    public void testEmptyIP() throws PangeaException, PangeaAPIException {
        DomainLookupResponse response = client.lookup("", "domaintools", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testEmptyProvider() throws PangeaException, PangeaAPIException {
        DomainLookupResponse response = client.lookup("737updatesboeing.com", "", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testEmptyNotValidProvider() throws PangeaException, PangeaAPIException {
        DomainLookupResponse response = client.lookup("737updatesboeing.com", "notvalidprovider", true, true);
    }

    @Test(expected = UnauthorizedException.class)
    public void testUnauthorized() throws PangeaException, PangeaAPIException, ConfigException{
        Config cfg = Config.fromIntegrationEnvironment(environment);
        cfg.setToken("notarealtoken");
        DomainIntelClient fakeClient = new DomainIntelClient(cfg);
        DomainLookupResponse response = fakeClient.lookup("737updatesboeing.com");
    }

}
