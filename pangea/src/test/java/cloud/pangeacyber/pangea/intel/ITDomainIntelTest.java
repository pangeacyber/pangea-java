package cloud.pangeacyber.pangea.intel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.ValidationException;

public class ITDomainIntelTest {
    DomainIntelClient client;

    @Before
    public void setUp() throws ConfigException{
        client = new DomainIntelClient(Config.fromEnvironment("domain_intel"));
    }

    @Test
    public void testDomainLookupMalicious_1() throws IOException, InterruptedException, PangeaAPIException {
        // Default provider, not verbose by default, not raw by default;
        DomainLookupResponse response = client.lookup("teoghehofuuxo.su");
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testDomainLookupMalicious_2() throws IOException, InterruptedException, PangeaAPIException {
        // With provider, not verbose by default, not raw by default;
        DomainLookupResponse response = client.lookup("teoghehofuuxo.su", "crowdstrike");
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testDomainLookupMalicious_3() throws IOException, InterruptedException, PangeaAPIException {
        // Default provider, no verbose, no raw;
        DomainLookupResponse response = client.lookup("teoghehofuuxo.su", false, false);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testDomainLookupMalicious_4() throws IOException, InterruptedException, PangeaAPIException {
        // Default provider, verbose, no raw;
        DomainLookupResponse response = client.lookup("teoghehofuuxo.su", true, false);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNotNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testDomainLookupMalicious_5() throws IOException, InterruptedException, PangeaAPIException {
        // Default provider, no verbose, raw;
        DomainLookupResponse response = client.lookup("teoghehofuuxo.su", false, true);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testDomainLookupMalicious_6() throws IOException, InterruptedException, PangeaAPIException {
        // Default provider, verbose, raw;
        DomainLookupResponse response = client.lookup("teoghehofuuxo.su", true, true);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testDomainLookupMalicious_7() throws IOException, InterruptedException, PangeaAPIException {
        // Provider, no verbose, no raw
        DomainLookupResponse response = client.lookup("teoghehofuuxo.su", "crowdstrike", false, false);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testDomainLookupMalicious_8() throws IOException, InterruptedException, PangeaAPIException {
        // Provider, verbose, raw
        DomainLookupResponse response = client.lookup("teoghehofuuxo.su", "crowdstrike", true, true);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test(expected = ValidationException.class)
    public void testEmptyIP() throws IOException, InterruptedException, PangeaAPIException {
        DomainLookupResponse response = client.lookup("", "crowdstrike", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testEmptyProvider() throws IOException, InterruptedException, PangeaAPIException {
        DomainLookupResponse response = client.lookup("teoghehofuuxo.su", "", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testEmptyNotValidProvider() throws IOException, InterruptedException, PangeaAPIException {
        DomainLookupResponse response = client.lookup("teoghehofuuxo.su", "notvalidprovider", true, true);
    }

}
