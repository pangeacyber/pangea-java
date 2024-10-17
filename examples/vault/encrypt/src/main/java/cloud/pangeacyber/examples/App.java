package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.vault.VaultClient;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.Utils;
import cloud.pangeacyber.pangea.vault.models.*;
import cloud.pangeacyber.pangea.vault.requests.*;
import cloud.pangeacyber.pangea.vault.responses.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import cloud.pangeacyber.pangea.Config;

public class App {
	public static void main(String[] args) {
		Config cfg = null;
		try {
			cfg = Config.fromEnvironment(VaultClient.serviceName);
		} catch (ConfigException e) {
			System.out.println(e);
			System.exit(1);
		}

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		String time = dtf.format(LocalDateTime.now());

		VaultClient client = new VaultClient.Builder(cfg).build();
		String name = "my key's name " + time;

		try {
			System.out.println("Create key...");
			SymmetricGenerateRequest generateRequest = SymmetricGenerateRequest.builder()
					.algorithm(SymmetricAlgorithm.AES128_CFB)
					.purpose(KeyPurpose.ENCRYPTION)
					.name(name).build();

			SymmetricGenerateResponse generateResp = client.symmetricGenerate(generateRequest);
			String id = generateResp.getResult().getId();
			System.out.println("Key ID: " + id);

			System.out.println("Encrypt...");
			String message = "thisisamessagetoencrypt";
			String dataB64 = Utils.stringToStringB64(message);

			// Encrypt
			EncryptResponse encryptResponse = client
					.encrypt(EncryptRequest.builder().id(id).plainText(dataB64).build());
			System.out.println("Cipher text: " + encryptResponse.getResult().getCipherText());

			System.out.println("Decrypt...");
			// Decrypt
			DecryptResponse decryptResponse1 = client.decrypt(
					DecryptRequest.builder().id(id).cipherText(encryptResponse.getResult().getCipherText()).version(1)
							.build());
			System.out.println(decryptResponse1.getResult().getPlainText());
			System.out.println(dataB64);

			if (dataB64.equals(decryptResponse1.getResult().getPlainText())) {
				System.out.println("Encrypt/decrypt success");
			} else {
				System.out.println("Encrypt/decrypt failed");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
			System.exit(1);
		}
	}
}
