package cloud.pangeacyber.pangea.audit.utils;

import cloud.pangeacyber.pangea.audit.models.EventVerification;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;

public class Verifier {

	public EventVerification verify(String pubKeyInput, String signatureBase64, String message) {
		byte[] pubKeyBytes;

		if (pubKeyInput.startsWith("-----")) {
			if (pubKeyInput.startsWith("-----BEGIN PUBLIC KEY-----")) {
				// Ed25519 header format
				String publicKeyPEM = pubKeyInput
					.replace("-----BEGIN PUBLIC KEY-----", "")
					.replaceAll(System.lineSeparator(), "")
					.replace("-----END PUBLIC KEY-----", "");
				byte[] encoded = Base64.getMimeDecoder().decode(publicKeyPEM);
				pubKeyBytes = Arrays.copyOfRange(encoded, Math.max(encoded.length - 32, 0), encoded.length);
			} else {
				// Not supported formats
				return EventVerification.NOT_VERIFIED;
			}
		} else {
			pubKeyBytes = Base64.getDecoder().decode(pubKeyInput);
		}

		Signer verifier = new Ed25519Signer();
		Ed25519PublicKeyParameters pubKey = null;
		try {
			pubKey = new Ed25519PublicKeyParameters(pubKeyBytes);
		} catch (IllegalArgumentException e) {
			return EventVerification.FAILED;
		}

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
