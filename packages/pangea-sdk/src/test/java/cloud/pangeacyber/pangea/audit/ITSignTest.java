package cloud.pangeacyber.pangea.audit;

import static org.junit.Assert.assertEquals;

import cloud.pangeacyber.pangea.audit.models.EventVerification;
import cloud.pangeacyber.pangea.audit.utils.MembershipProof;
import cloud.pangeacyber.pangea.audit.utils.MembershipProofItem;
import cloud.pangeacyber.pangea.audit.utils.Verification;
import cloud.pangeacyber.pangea.audit.utils.Verifier;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.SignerException;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

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
		String oldPubKeyFormat = "lvOyDMpK2DQ16NI8G41yINl01wMHzINBahtDPoh4+mE=";
		String pubKey = signer.getPublicKey();
		assertEquals(
			"-----BEGIN PUBLIC KEY-----\nMCowBQYDK2VwAyEAlvOyDMpK2DQ16NI8G41yINl01wMHzINBahtDPoh4+mE=\n-----END PUBLIC KEY-----\n",
			pubKey
		);
		assertEquals(
			"IYmIUBKWu5yLHM1u3bAw7dvVg1MPc7FLDWSz6d9oqn4FoCu9Bk6ta/lXvvXZUpa7hCm6RhU0VdBzh53x3mKiDQ==",
			signature
		);

		Verifier v = new Verifier();
		EventVerification result = v.verify(pubKey, signature, msg);
		assertEquals(EventVerification.SUCCESS, result);

		result = v.verify(oldPubKeyFormat, signature, msg);
		assertEquals(EventVerification.SUCCESS, result);
	}

	@Test(expected = SignerException.class)
	public void testSignerFileNotFound() throws PangeaException {
		LogSigner fakeSigner = new LogSigner("./not/arealfile");
		String msg = "Hello signed world";
		String signature = fakeSigner.sign(msg);
	}

	@Test
	public void testVerification() throws PangeaException {
		String membershipProof =
			"l:fac8e0a9f3f1997fb7f7c608ec993ac6d634efbd796f3ac444e3573320a8365c,l:daae1317182c71a039601a4b7f89dcd32a67e7deb816f9a2ab14b6a4321b4ddb,l:dcb38da01e75402b690f35cb830ea87b21882693d9a7c6ef1f8605515e41d9ea,l:0aa67b370ada7d95104ca73850c39436e5b8a865a4cd71d31d6ccaf0a845fcc3";
		System.out.println(membershipProof);
		MembershipProof proof = Verification.decodeMembershipProof(membershipProof);
		for (MembershipProofItem membershipProofItem : proof) {
			System.out.println(membershipProofItem.getSide());
			System.out.println(Arrays.toString(membershipProofItem.getHash()));
		}
	}
}
