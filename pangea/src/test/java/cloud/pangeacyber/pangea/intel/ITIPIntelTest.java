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

public class ITIPIntelTest {
    IpIntelClient client;

    @Before
    public void setUp() throws ConfigException{
        client = new IpIntelClient(Config.fromEnvironment("ip_intel"));
    }

    @Test
    public void testIpLookupMalicious_1() throws IOException, InterruptedException, PangeaAPIException {
        // Default provider, not verbose by default, not raw by default;
        IpLookupResponse response = client.lookup("93.231.182.110");
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpLookupMalicious_2() throws IOException, InterruptedException, PangeaAPIException {
        // With provider, not verbose by default, not raw by default;
        IpLookupResponse response = client.lookup("93.231.182.110", "crowdstrike");
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpLookupMalicious_3() throws IOException, InterruptedException, PangeaAPIException {
        // Default provider, no verbose, no raw;
        IpLookupResponse response = client.lookup("93.231.182.110", false, false);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpLookupMalicious_4() throws IOException, InterruptedException, PangeaAPIException {
        // Default provider, verbose, no raw;
        IpLookupResponse response = client.lookup("93.231.182.110", true, false);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNotNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpLookupMalicious_5() throws IOException, InterruptedException, PangeaAPIException {
        // Default provider, no verbose, raw;
        IpLookupResponse response = client.lookup("93.231.182.110", false, true);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testIpLookupMalicious_6() throws IOException, InterruptedException, PangeaAPIException {
        // Default provider, verbose, raw;
        IpLookupResponse response = client.lookup("93.231.182.110", true, true);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testIpLookupMalicious_7() throws IOException, InterruptedException, PangeaAPIException {
        // Provider, no verbose, no raw
        IpLookupResponse response = client.lookup("93.231.182.110", "crowdstrike", false, false);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testIpLookupMalicious_8() throws IOException, InterruptedException, PangeaAPIException {
        // Provider, verbose, raw
        IpLookupResponse response = client.lookup("93.231.182.110", "crowdstrike", true, true);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test(expected = ValidationException.class)
    public void testEmptyIP() throws IOException, InterruptedException, PangeaAPIException {
        IpLookupResponse response = client.lookup("", "crowdstrike", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testEmptyProvider() throws IOException, InterruptedException, PangeaAPIException {
        IpLookupResponse response = client.lookup("93.231.182.110", "", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testEmptyNotValidProvider() throws IOException, InterruptedException, PangeaAPIException {
        IpLookupResponse response = client.lookup("93.231.182.110", "notvalidprovider", true, true);
    }

}
