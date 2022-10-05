package cloud.pangeacyber.pangea.intel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.ConfigException;

public class ITFileIntelTest {
    FileIntelClient client;

    @Before
    public void setUp() throws ConfigException{
        client = new FileIntelClient(Config.fromEnvironment("file_intel"));
    }

    @Test
    public void testFileLookupMalicious() throws IOException, InterruptedException {
        FileLookupResponse response;
        response = client.lookup("142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e", "sha256", "reversinglabs", false, false);

        assertTrue(response.isOk());

        IntelLookupData data = response.getResult().getData();

        assertEquals("malicious", data.getVerdict());
        assertEquals("Trojan", data.getCategory()[0]);
    }

    @Test
    public void testFileLookupNotProvided() throws IOException, InterruptedException {
        FileLookupResponse response;
        response = client.lookup("322ccbd42b7e4fd3a9d0167ca2fa9f6483d9691364c431625f1df542706", "sha256", "reversinglabs", true, true);

        assertTrue(response.isOk());
        IntelLookupData data = response.getResult().getData();

        assertEquals("", data.getVerdict());
        assertEquals("Not Provided", data.getCategory()[0]);
    }
}
