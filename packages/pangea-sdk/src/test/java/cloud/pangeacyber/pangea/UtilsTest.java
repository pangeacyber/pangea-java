package cloud.pangeacyber.pangea;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cloud.pangeacyber.pangea.exceptions.PangeaException;
import org.junit.jupiter.api.Test;

public class UtilsTest {

	@Test
	public void testHashNTLM() {
		assertEquals("31D6CFE0D16AE931B73C59D7E0C089C0", Utils.hashNTLM(""));
		assertEquals("8846F7EAEE8FB117AD06BDD830B7586C", Utils.hashNTLM("password"));
	}

	@Test
	public void testFileUploadParams() throws PangeaException {
		final var params = Utils.getFileUploadParams("./src/test/java/cloud/pangeacyber/pangea/testdata/testfile.pdf");
		assertEquals("754995fb", params.getCRC32C());
	}
}
