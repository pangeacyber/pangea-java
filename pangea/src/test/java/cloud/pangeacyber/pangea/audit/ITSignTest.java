package cloud.pangeacyber.pangea.audit;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cloud.pangeacyber.pangea.exceptions.PangeaException;

public class ITSignTest {
    LogSigner signer;

    @Before
    public void setUp() {
        signer = new LogSigner("./src/test/java/cloud/pangeacyber/pangea/testdata/privkey");
    }

    @Test
    public void testSigner() throws PangeaException {
        String msg = "Hello signed world";
        String signature = signer.sign(msg);
        String pubKey = signer.getPublicKey();

        assertEquals("lvOyDMpK2DQ16NI8G41yINl01wMHzINBahtDPoh4+mE=", pubKey);
        assertEquals("IYmIUBKWu5yLHM1u3bAw7dvVg1MPc7FLDWSz6d9oqn4FoCu9Bk6ta/lXvvXZUpa7hCm6RhU0VdBzh53x3mKiDQ==", signature);
    }
}
