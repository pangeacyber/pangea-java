package cloud.pangeacyber.pangea;

import cloud.pangeacyber.pangea.vault.models.ExportEncryptionAlgorithm;
import java.io.StringWriter;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

public final class CryptoUtils {

	/**
	 * Generates a new RSA key pair of the given key size.
	 *
	 * @param keysize Key size.
	 * @return RSA key pair.
	 */
	public static KeyPair generateRsaKeyPair(int keySize) {
		try {
			final var generator = KeyPairGenerator.getInstance("RSA", new BouncyCastleProvider());
			generator.initialize(keySize);
			return generator.generateKeyPair();
		} catch (Exception error) {
			throw new RuntimeException("Failed to generate RSA key pair.", error);
		}
	}

	/**
	 * Decrypt cipher text based on the given Vault export encryption algorithm.
	 *
	 * @param algorithm Vault encryption algorithm that created the cipher text.
	 * @param privateKey Asymmetric private key.
	 * @param cipherText Cipher text.
	 * @return Decrypted bytes.
	 */
	public static byte[] asymmetricDecrypt(
		ExportEncryptionAlgorithm algorithm,
		PrivateKey privateKey,
		byte[] cipherText
	)
		throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
		Cipher cipher = null;
		switch (algorithm) {
			case RSA4096_OAEP_SHA512:
				cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-512AndMGF1Padding", new BouncyCastleProvider());
				break;
			default:
				throw new IllegalArgumentException("Unrecognized export encryption algorithm: " + algorithm);
		}
		return asymmetricDecrypt(cipher, privateKey, cipherText);
	}

	/**
	 * Decrypt cipher text using the given asymmetric private key.
	 *
	 * @param cipher Cipher to use.
	 * @param privateKey Asymmetric private key.
	 * @param cipherText Cipher text.
	 * @return Decrypted bytes.
	 */
	public static byte[] asymmetricDecrypt(Cipher cipher, PrivateKey privateKey, byte[] cipherText)
		throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(cipherText);
	}

	/**
	 * Export the given asymmetric public key in PEM format.
	 * @param publicKey Asymmetric public key.
	 * @return Asymmetric public key in PEM format.
	 */
	public static String asymmetricPemExport(PublicKey publicKey) {
		try {
			final var pemObject = new PemObject("PUBLIC KEY", publicKey.getEncoded());
			final var buffer = new StringWriter();
			try (final var pemWriter = new PemWriter(buffer)) {
				pemWriter.writeObject(pemObject);
			}
			return buffer.toString();
		} catch (Exception error) {
			throw new RuntimeException("Failed to export public key.", error);
		}
	}
}
