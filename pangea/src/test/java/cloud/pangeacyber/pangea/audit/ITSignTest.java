package cloud.pangeacyber.pangea.audit;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ITSignTest {
    LogSigner signer;

    @Before
    public void setUp() {
        signer = new LogSigner("./src/test/java/cloud/pangeacyber/pangea/testdata/privkey");
    }

    @Test
    public void testSigner() throws InterruptedException, Exception {
        String msg = "Hello signed world";
        String signature = signer.sign(msg);
        String pubKey = signer.getPublicKey();

        assertEquals("lvOyDMpK2DQ16NI8G41yINl01wMHzINBahtDPoh4+mE=", pubKey);
        // FIXME: check signature generation
        // assertEquals("dg7Wg+E8QzZzhECzQoH3v3pbjWObR8ve7SHREAyA9JlFOusKPHVb16t5D3rbscnv80ry/aWzfMTscRNSYJFzDA==", signature);
    }
}
