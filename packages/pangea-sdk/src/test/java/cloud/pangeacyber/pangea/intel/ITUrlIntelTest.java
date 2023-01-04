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

public class ITUrlIntelTest {
    UrlIntelClient client;
    TestEnvironment environment = TestEnvironment.DEVELOP;

    @Before
    public void setUp() throws ConfigException{
        client = new UrlIntelClient(Config.fromIntegrationEnvironment(environment));
    }

    @Test
    public void testUrlLookupMalicious_1() throws PangeaException, PangeaException, PangeaAPIException {
        // Default provider, not verbose by default, not raw by default;
        UrlLookupResponse response = client.lookup("http://113.235.101.11:54384");
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testUrlLookupMalicious_2() throws PangeaException, PangeaAPIException {
        // With provider, not verbose by default, not raw by default;
        UrlLookupResponse response = client.lookup("http://113.235.101.11:54384", "crowdstrike");
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testUrlLookupMalicious_3() throws PangeaException, PangeaAPIException {
        // Default provider, no verbose, no raw;
        UrlLookupResponse response = client.lookup("http://113.235.101.11:54384", false, false);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testUrlLookupMalicious_4() throws PangeaException, PangeaAPIException {
        // Default provider, verbose, no raw;
        UrlLookupResponse response = client.lookup("http://113.235.101.11:54384", true, false);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNotNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testUrlLookupMalicious_5() throws PangeaException, PangeaAPIException {
        // Default provider, no verbose, raw;
        UrlLookupResponse response = client.lookup("http://113.235.101.11:54384", false, true);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testUrlLookupMalicious_6() throws PangeaException, PangeaAPIException {
        // Default provider, verbose, raw;
        UrlLookupResponse response = client.lookup("http://113.235.101.11:54384", true, true);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testUrlLookupMalicious_7() throws PangeaException, PangeaAPIException {
        // Provider, no verbose, no raw
        UrlLookupResponse response = client.lookup("http://113.235.101.11:54384", "crowdstrike", false, false);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testUrlLookupMalicious_8() throws PangeaException, PangeaAPIException {
        // Provider, verbose, raw
        UrlLookupResponse response = client.lookup("http://113.235.101.11:54384", "crowdstrike", true, true);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test(expected = ValidationException.class)
    public void testEmptyIP() throws PangeaException, PangeaAPIException {
        UrlLookupResponse response = client.lookup("", "crowdstrike", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testEmptyProvider() throws PangeaException, PangeaAPIException {
        UrlLookupResponse response = client.lookup("http://113.235.101.11:54384", "", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testEmptyNotValidProvider() throws PangeaException, PangeaAPIException {
        UrlLookupResponse response = client.lookup("http://113.235.101.11:54384", "notvalidprovider", true, true);
    }

    @Test(expected = UnauthorizedException.class)
    public void testUnauthorized() throws PangeaException, PangeaAPIException, ConfigException{
        Config cfg = Config.fromIntegrationEnvironment(environment);
        cfg.setToken("notarealtoken");
        UrlIntelClient fakeClient = new UrlIntelClient(cfg);
        UrlLookupResponse response = fakeClient.lookup("http://113.235.101.11:54384");
    }

}
