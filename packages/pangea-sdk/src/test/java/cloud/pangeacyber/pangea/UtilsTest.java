package cloud.pangeacyber.pangea;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import org.junit.Before;
import org.junit.Test;

public class UtilsTest {

	@Before
	public void setUp() {}

	@Test
	public void testHashNTLM() throws UnsupportedEncodingException {
		String hash = Utils.hashNTLM("password");
		assertEquals(hash, "8846F7EAEE8FB117AD06BDD830B7586C");
	}
}
