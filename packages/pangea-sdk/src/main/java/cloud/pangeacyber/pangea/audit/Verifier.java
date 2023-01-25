package cloud.pangeacyber.pangea.audit;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;

public class Verifier {

	public EventVerification verify(String pubKeyBase64, String signatureBase64, String message) {
		// verify the signature
		Signer verifier = new Ed25519Signer();
		Ed25519PublicKeyParameters pubKey = new Ed25519PublicKeyParameters(Base64.getDecoder().decode(pubKeyBase64));
		verifier.init(false, pubKey);
		byte[] byteMessage;
		try {
			byteMessage = message.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			return EventVerification.FAILED;
		}

		verifier.update(byteMessage, 0, byteMessage.length);
		boolean verified = verifier.verifySignature(Base64.getDecoder().decode(signatureBase64));
		return verified ? EventVerification.SUCCESS : EventVerification.FAILED;
	}
}
