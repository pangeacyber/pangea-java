package cloud.pangeacyber.pangea.intel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.ErrorField;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.ValidationException;

public class ITDomainIntelTest {
    DomainIntelClient client;

    @Before
    public void setUp() throws ConfigException{
         client = new DomainIntelClient(Config.fromEnvironment("domain_intel"));
    }

    @Test
    public void testDomainLookupMalicious_1() throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        // Default provider, not verbose by default, not raw by default;
        DomainLookupResponse response = client.lookup("737updatesboeing.com");
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testDomainLookupMalicious_2() throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        // With provider, not verbose by default, not raw by default;
        DomainLookupResponse response = client.lookup("737updatesboeing.com", "domaintools");
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testDomainLookupMalicious_3() throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        // Default provider, no verbose, no raw;
        DomainLookupResponse response = client.lookup("737updatesboeing.com", false, false);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testDomainLookupMalicious_4() throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        // Default provider, verbose, no raw;
        DomainLookupResponse response = client.lookup("737updatesboeing.com", true, false);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNotNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testDomainLookupMalicious_5() throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        // Default provider, no verbose, raw;
        DomainLookupResponse response = client.lookup("737updatesboeing.com", false, true);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testDomainLookupMalicious_6() throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        // Default provider, verbose, raw;
        DomainLookupResponse response = client.lookup("737updatesboeing.com", true, true);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testDomainLookupMalicious_7() throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        // Provider, no verbose, no raw
        DomainLookupResponse response = client.lookup("737updatesboeing.com", "domaintools", false, false);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testDomainLookupMalicious_8() throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        // Provider, verbose, raw
        DomainLookupResponse response = client.lookup("737updatesboeing.com", "domaintools", true, true);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test(expected = ValidationException.class)
    public void testEmptyIP() throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        DomainLookupResponse response = client.lookup("", "domaintools", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testEmptyProvider() throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        DomainLookupResponse response = client.lookup("737updatesboeing.com", "", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testEmptyNotValidProvider() throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        DomainLookupResponse response = client.lookup("737updatesboeing.com", "notvalidprovider", true, true);
    }

}
