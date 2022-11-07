package cloud.pangeacyber.pangea.intel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.ValidationException;

public class ITFileIntelTest {
    FileIntelClient client;

    @Before
    public void setUp() throws ConfigException{
        client = new FileIntelClient(Config.fromIntegrationEnvironment(FileIntelClient.serviceName));
    }

    @Test
    public void testFileLookupMalicious_1() throws PangeaException, PangeaAPIException {
        // Provider, verbose and raw data
        FileLookupResponse response = client.lookup("142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e", "sha256", "reversinglabs", false, false);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertEquals("Trojan", data.getCategory()[0]);
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testFileLookupMalicious_2() throws PangeaException, PangeaAPIException {
        // Provider, no verbose, no raw data
        FileLookupResponse response = client.lookup("142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e", "sha256", "reversinglabs", true, true);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertEquals("Trojan", data.getCategory()[0]);
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testFileLookupMalicious_3() throws PangeaException, PangeaAPIException {
        // Provider, no verbose by default, no raw data by default
        FileLookupResponse response = client.lookup("142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e", "sha256", "reversinglabs");
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertEquals("Trojan", data.getCategory()[0]);
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testFileLookupMalicious_4() throws PangeaException, PangeaAPIException {
        // Default provider, no verbose by default, no raw data by default
        FileLookupResponse response = client.lookup("142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e", "sha256");
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertEquals("Trojan", data.getCategory()[0]);
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testFileLookupMalicious_5() throws PangeaException, PangeaAPIException {
        // Provider, verbose, no raw data
        FileLookupResponse response = client.lookup("142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e", "sha256", "reversinglabs", true, false);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertEquals("Trojan", data.getCategory()[0]);
        assertNotNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testFileLookupMalicious_6() throws PangeaException, PangeaAPIException {
        // Provider, no verbose, raw data
        FileLookupResponse response = client.lookup("142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e", "sha256", "reversinglabs", false, true);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertEquals("Trojan", data.getCategory()[0]);
        assertNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testFileLookupNotProvided() throws PangeaException, PangeaAPIException{
        FileLookupResponse response = client.lookup("322ccbd42b7e4fd3a9d0167ca2fa9f6483d9691364c431625f1df542706", "sha256", "reversinglabs", true, true);
        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();
        assertEquals("", data.getVerdict());
        assertEquals("Not Provided", data.getCategory()[0]);
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test(expected = ValidationException.class)
    public void testEmptyProvider() throws PangeaException, PangeaAPIException {
        FileLookupResponse response = client.lookup("322ccbd42b7e4fd3a9d0167ca2fa9f6483d9691364c431625f1df542706", "sha256", "", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testNotValidProvider() throws PangeaException, PangeaAPIException {
        FileLookupResponse response = client.lookup("322ccbd42b7e4fd3a9d0167ca2fa9f6483d9691364c431625f1df542706", "sha256", "notvalid", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testEmptyHash() throws PangeaException, PangeaAPIException {
        FileLookupResponse response = client.lookup("", "sha256", "reversinglabs", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testNotValidHash() throws PangeaException, PangeaAPIException {
        FileLookupResponse response = client.lookup("notarealhash", "sha256", "reversinglabs", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testEmptyHashType() throws PangeaException, PangeaAPIException {
        FileLookupResponse response = client.lookup("322ccbd42b7e4fd3a9d0167ca2fa9f6483d9691364c431625f1df542706", "", "reversinglabs", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testNotValidHashType() throws PangeaException, PangeaAPIException {
        FileLookupResponse response = client.lookup("322ccbd42b7e4fd3a9d0167ca2fa9f6483d9691364c431625f1df542706", "notvalid", "reversinglabs", true, true);
    }
}
