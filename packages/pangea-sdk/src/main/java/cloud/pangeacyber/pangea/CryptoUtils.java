package cloud.pangeacyber.pangea;

import cloud.pangeacyber.pangea.vault.models.ExportEncryptionAlgorithm;
import cloud.pangeacyber.pangea.vault.results.ExportResult;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import lombok.experimental.UtilityClass;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

@UtilityClass
public final class CryptoUtils {

	private static final int AES_GCM_IV_SIZE = 12;
	private static final int AES_GCM_TAG_SIZE = 16;

	/**
	 * Generates a new RSA key pair of the given key size.
	 *
	 * @param keySize Key size.
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
			case RSA_NO_PADDING_4096_KEM:
				cipher = Cipher.getInstance("RSA/ECB/NoPadding", new BouncyCastleProvider());
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
	 *
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

	/**
	 * Decrypt the given Vault-exported key.
	 *
	 * @param result Vault-exported result.
	 * @param password KEM password.
	 * @param privateKey RSA private key to use in decryption.
	 * @return Decrypted key.
	 */
	public static String kemDecrypt(ExportResult result, String password, PrivateKey privateKey) {
		var cipherEncoded = result.getPrivateKey();
		if (cipherEncoded == null) {
			cipherEncoded = result.getKey();
		}
		if (cipherEncoded == null) {
			throw new NullPointerException("At least one of 'PrivateKey' or 'Key' should not be null.");
		}

		final var cipherDecoded = Base64.decode(cipherEncoded);
		final var cipher = Arrays.copyOfRange(cipherDecoded, AES_GCM_IV_SIZE, cipherDecoded.length);
		final var iv = Arrays.copyOfRange(cipherDecoded, 0, AES_GCM_IV_SIZE);
		final var decodedEncryptedSalt = Base64.decode(result.getEncryptedSalt());

		final var decrypted = kemDecrypt(
			cipher,
			decodedEncryptedSalt,
			iv,
			privateKey,
			result.getAsymmetricAlgorithm(),
			result.getSymmetricAlgorithm(),
			password,
			result.getHashAlgorithm(),
			result.getIterationCount(),
			result.getKdf()
		);

		return new String(decrypted, StandardCharsets.UTF_8);
	}

	private static byte[] kemDecrypt(
		byte[] cipherText,
		byte[] encryptedSalt,
		byte[] iv,
		PrivateKey privateKey,
		ExportEncryptionAlgorithm asymmetricAlgorithm,
		String symmetricAlgorithm,
		String password,
		String hashAlgorithm,
		int iterationCount,
		String kdf
	) {
		if (!symmetricAlgorithm.equalsIgnoreCase("AES-GCM-256")) {
			throw new UnsupportedOperationException(
				String.format("invalid symmetricAlgorithm: %s", symmetricAlgorithm)
			);
		}

		if (asymmetricAlgorithm != ExportEncryptionAlgorithm.RSA_NO_PADDING_4096_KEM) {
			throw new UnsupportedOperationException(
				String.format("invalid asymmetricAlgorithm: %s", asymmetricAlgorithm)
			);
		}

		if (!kdf.equalsIgnoreCase("pbkdf2")) {
			throw new UnsupportedOperationException(String.format("invalid kdf: %s", kdf));
		}

		if (!hashAlgorithm.equalsIgnoreCase("sha512")) {
			throw new UnsupportedOperationException(String.format("invalid hashAlgorithm: %s", hashAlgorithm));
		}

		byte[] salt;
		try {
			salt = asymmetricDecrypt(asymmetricAlgorithm, privateKey, encryptedSalt);
		} catch (Exception error) {
			throw new RuntimeException("Failed to decrypt salt.", error);
		}

		final var keyLength = keyLength(symmetricAlgorithm);
		final var gen = new PKCS5S2ParametersGenerator(new SHA512Digest());
		gen.init(password.getBytes(StandardCharsets.UTF_8), salt, iterationCount);
		final var symmetricKey = ((KeyParameter) gen.generateDerivedParameters(keyLength * 8)).getKey();

		return aesGcmDecrypt(symmetricKey, cipherText, iv, null);
	}

	private static byte[] aesGcmDecrypt(byte[] key, byte[] cipherText, byte[] nonce, byte[] associatedData) {
		final var plaintextBytes = new byte[cipherText.length - AES_GCM_TAG_SIZE];

		final var cipher = GCMBlockCipher.newInstance(AESEngine.newInstance());
		final var parameters = new AEADParameters(new KeyParameter(key), AES_GCM_TAG_SIZE * 8, nonce, associatedData);
		cipher.init(false, parameters);

		final var offset = cipher.processBytes(cipherText, 0, cipherText.length, plaintextBytes, 0);

		try {
			cipher.doFinal(plaintextBytes, offset);
		} catch (Exception error) {
			throw new RuntimeException("Decryption failed.", error);
		}

		return plaintextBytes;
	}

	private static int keyLength(String algorithm) {
		if (algorithm.equalsIgnoreCase("AES-GCM-256")) {
			return 32;
		}

		throw new UnsupportedOperationException(String.format("unknown algorithm: %s", algorithm));
	}
}
