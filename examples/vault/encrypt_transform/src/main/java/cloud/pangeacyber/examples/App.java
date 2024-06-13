// This example demonstrates how to use Vault's format-preserving encryption (FPE)
// to encrypt and decrypt text without changing its length.

package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.vault.requests.DecryptTransformRequest;
import cloud.pangeacyber.pangea.vault.requests.EncryptTransformRequest;
import cloud.pangeacyber.pangea.vault.requests.SymmetricGenerateRequest;
import cloud.pangeacyber.pangea.vault.VaultClient;
import cloud.pangeacyber.pangea.vault.models.KeyPurpose;
import cloud.pangeacyber.pangea.vault.models.SymmetricAlgorithm;
import cloud.pangeacyber.pangea.vault.models.TransformAlphabet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class App {
    public static void main(String[] args) {
		// Set up a Pangea Vault client.
		final var token = System.getenv("PANGEA_VAULT_TOKEN");
		final var domain = System.getenv("PANGEA_DOMAIN");
		final var config = new Config.Builder(token, domain).build();
        final var client = new VaultClient.Builder(config).build();

		// Plain text that we'll encrypt.
		final var plainText = "123-4567-8901";

		// Optional tweak string.
		final var tweak = "MTIzMTIzMT==";

		try {
			// Generate an encryption key.
			final var dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
			final var time = dtf.format(LocalDateTime.now());
			final var generated = client.symmetricGenerate(
				new SymmetricGenerateRequest
					.Builder(SymmetricAlgorithm.AES256_FF3_1, KeyPurpose.FPE, "java-fpe-example-" + time)
					.build()
			);
			final var keyId = generated.getResult().getId();

			// Encrypt the plain text.
			final var encrypted = client.encryptTransform(
				new EncryptTransformRequest
					.Builder(keyId, plainText, TransformAlphabet.NUMERIC)
					.tweak(tweak)
					.build()
			);
			final var encryptedText = encrypted.getResult().getCipherText();
			System.out.printf("Plain text: %s. Encrypted text: %s.\n", plainText, encryptedText);

			// Decrypt the result to get back the text we started with.
			final var decrypted = client.decryptTransform(
				new DecryptTransformRequest
					.Builder(keyId, encryptedText, tweak, TransformAlphabet.NUMERIC)
					.build()
			);
			final var decryptedText = decrypted.getResult().getPlainText();
			System.out.printf("Original text: %s. Decrypted text: %s.\n", plainText, decryptedText);
		}
		catch (Exception error) {
            System.out.println("Error: " + error);
            System.exit(1);
		}
    }
}
