package cloud.pangeacyber.pangea.intel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
import cloud.pangeacyber.pangea.intel.models.FileLookupResponse;
import cloud.pangeacyber.pangea.intel.models.FileReputationResponse;
import cloud.pangeacyber.pangea.intel.models.IntelLookupData;
import cloud.pangeacyber.pangea.intel.models.IntelReputationData;

public class ITFileIntelTest {
    FileIntelClient client;
    TestEnvironment environment = TestEnvironment.LIVE;

    @Before
    public void setUp() throws ConfigException{
        client = new FileIntelClient(Config.fromIntegrationEnvironment(environment));
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

        // NOTE: because we're using the default provider in this test,
        // the resulting verdict can change based on the provider set as default
        // so only assert the response is successful
        assertTrue(response.isOk());

        assertNotNull(response.getResult().getData());
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

    @Test
    public void testFileReputationMalicious_1() throws PangeaException, PangeaAPIException {
        // Provider, verbose and raw data
        FileReputationResponse response = client.reputation("142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e", "sha256", "reversinglabs", false, false);
        assertTrue(response.isOk());

        IntelReputationData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertEquals("Trojan", data.getCategory()[0]);
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testFileReputationMalicious_2() throws PangeaException, PangeaAPIException {
        // Provider, no verbose, no raw data
        FileReputationResponse response = client.reputation("142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e", "sha256", "reversinglabs", true, true);
        assertTrue(response.isOk());

        IntelReputationData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertEquals("Trojan", data.getCategory()[0]);
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testFileReputationMalicious_3() throws PangeaException, PangeaAPIException {
        // Provider, no verbose by default, no raw data by default
        FileReputationResponse response = client.reputation("142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e", "sha256", "reversinglabs");
        assertTrue(response.isOk());

        IntelReputationData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertEquals("Trojan", data.getCategory()[0]);
        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testFileReputationMalicious_4() throws PangeaException, PangeaAPIException {
        // Default provider, no verbose by default, no raw data by default
        FileReputationResponse response = client.reputation("142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e", "sha256");

        // NOTE: because we're using the default provider in this test,
        // the resulting verdict can change based on the provider set as default
        // so only assert the response is successful
        assertTrue(response.isOk());

        assertNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testFileReputationMalicious_5() throws PangeaException, PangeaAPIException {
        // Provider, verbose, no raw data
        FileReputationResponse response = client.reputation("142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e", "sha256", "reversinglabs", true, false);
        assertTrue(response.isOk());

        IntelReputationData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertEquals("Trojan", data.getCategory()[0]);
        assertNotNull(response.getResult().getParameters());
        assertNull(response.getResult().getRawData());
    }

    @Test
    public void testFileReputationMalicious_6() throws PangeaException, PangeaAPIException {
        // Provider, no verbose, raw data
        FileReputationResponse response = client.reputation("142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e", "sha256", "reversinglabs", false, true);
        assertTrue(response.isOk());

        IntelReputationData data = response.getResult().getData();
        assertEquals("malicious", data.getVerdict());
        assertEquals("Trojan", data.getCategory()[0]);
        assertNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test
    public void testFileReputationNotProvided() throws PangeaException, PangeaAPIException{
        FileReputationResponse response = client.reputation("322ccbd42b7e4fd3a9d0167ca2fa9f6483d9691364c431625f1df542706", "sha256", "reversinglabs", true, true);
        assertTrue(response.isOk());

        IntelReputationData data = response.getResult().getData();
        assertEquals("", data.getVerdict());
        assertEquals("Not Provided", data.getCategory()[0]);
        assertNotNull(response.getResult().getParameters());
        assertNotNull(response.getResult().getRawData());
    }

    @Test(expected = ValidationException.class)
    public void testEmptyProvider() throws PangeaException, PangeaAPIException {
        FileReputationResponse response = client.reputation("322ccbd42b7e4fd3a9d0167ca2fa9f6483d9691364c431625f1df542706", "sha256", "", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testNotValidProvider() throws PangeaException, PangeaAPIException {
        FileReputationResponse response = client.reputation("322ccbd42b7e4fd3a9d0167ca2fa9f6483d9691364c431625f1df542706", "sha256", "notvalid", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testEmptyHash() throws PangeaException, PangeaAPIException {
        FileReputationResponse response = client.reputation("", "sha256", "reversinglabs", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testNotValidHash() throws PangeaException, PangeaAPIException {
        FileReputationResponse response = client.reputation("notarealhash", "sha256", "reversinglabs", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testEmptyHashType() throws PangeaException, PangeaAPIException {
        FileReputationResponse response = client.reputation("322ccbd42b7e4fd3a9d0167ca2fa9f6483d9691364c431625f1df542706", "", "reversinglabs", true, true);
    }

    @Test(expected = ValidationException.class)
    public void testNotValidHashType() throws PangeaException, PangeaAPIException {
        FileReputationResponse response = client.reputation("322ccbd42b7e4fd3a9d0167ca2fa9f6483d9691364c431625f1df542706", "notvalid", "reversinglabs", true, true);
    }

    @Test(expected = UnauthorizedException.class)
    public void testUnauthorized() throws PangeaException, PangeaAPIException, ConfigException{
        Config cfg = Config.fromIntegrationEnvironment(environment);
        cfg.setToken("notarealtoken");
        FileIntelClient fakeClient = new FileIntelClient(cfg);
        FileReputationResponse response = fakeClient.reputation("142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e", "sha256", "reversinglabs", false, false);
    }

    @Test
    public void testSHA256fromFilepath() throws PangeaException{
        String hash = FileIntelClient.calculateSHA256fromFile("./README.md");
        assertNotNull(hash);
        assertNotEquals(hash, "");
    }

    @Test(expected = PangeaException.class)
    public void testSHA256fromFilepathNoFile() throws PangeaException{
        String hash = FileIntelClient.calculateSHA256fromFile("./not/a/real/path/file.exe");
    }

}
