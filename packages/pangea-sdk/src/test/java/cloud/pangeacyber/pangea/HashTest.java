package cloud.pangeacyber.pangea;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import cloud.pangeacyber.pangea.audit.utils.Hash;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public final class HashTest {

	@ParameterizedTest
	@MethodSource("unhexlifyFixtures")
	public void testUnhexlify(String input, byte[] expected) {
		final var actual = Hash.unhexlify(input);
		assertArrayEquals(expected, actual);
	}

	private static Object[] unhexlifyFixtures() {
		return new Object[] {
			new Object[] { "", new byte[0] },
			new Object[] { "AB", new byte[] { (byte) 0xab } },
			new Object[] { "FF", new byte[] { (byte) 0xff } },
			new Object[] { "ABCDEF", new byte[] { (byte) 0xab, (byte) 0xcd, (byte) 0xef } },
		};
	}
}
