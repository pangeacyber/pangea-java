package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.vault.VaultClient;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.vault.requests.*;
import cloud.pangeacyber.pangea.vault.models.*;
import cloud.pangeacyber.pangea.Utils;
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

		VaultClient client = new VaultClient.Builder(cfg).build();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		String time = dtf.format(LocalDateTime.now());
		String name = "my key's name " + time;

		try {
			AsymmetricGenerateRequest generateRequest = AsymmetricGenerateRequest.builder()
					.algorithm(AsymmetricAlgorithm.ED25519)
					.purpose(KeyPurpose.SIGNING)
					.name(name)
					.build();

			// Generate
			System.out.println("Generate key...");
			AsymmetricGenerateResponse generateResp = client.asymmetricGenerate(generateRequest);
			String id = generateResp.getResult().getId();
			System.out.println("Key ID: " + id);

			System.out.println("Sign...");
			String data = Utils.stringToStringB64("thisisamessagetosign");

			// Sign 1
			SignResponse signResponse = client.sign(id, data);
			System.out.println("Signature: " + signResponse.getResult().getSignature());

			System.out.println("Verify...");
			// Verify 1
			VerifyResponse verifyResponse = client.verify(id, data, signResponse.getResult().getSignature(), 1);
			if (verifyResponse.getResult().isValidSignature()) {
				System.out.println("Signature verification success");
			} else {
				System.out.println("Signature verification failed");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
			System.exit(1);
		}
	}
}
