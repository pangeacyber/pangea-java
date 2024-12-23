package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.vault.VaultClient;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.vault.requests.*;
import cloud.pangeacyber.pangea.vault.models.*;
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
		String secretV1 = "mysecret";
		String secretV2 = "mynewsecret";
		SecretStoreResponse storeResponse = null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		String time = dtf.format(LocalDateTime.now());
		String name = "my secret's name " + time;

		try {
			System.out.println("Store...");
			storeResponse = client.secretStore(SecretStoreRequest.builder().secret(secretV1).name(name).build());

			System.out.println("Result ID: " + storeResponse.getResult().getId());

			System.out.println("Rotate...");
			SecretRotateResponse rotateResponse = client.secretRotate(
					SecretRotateRequest.builder().id(storeResponse.getResult().getId()).secret(secretV2)
							.rotationState(ItemVersionState.SUSPENDED)
							.build());
			final var itemVersions = rotateResponse.getResult().getItemVersions();
			System.out.println("Result version: " + itemVersions.get(itemVersions.size() - 1).getVersion());

			System.out.println("Get...");
			GetResponse getResponse = client.get(
					GetRequest.builder().id(storeResponse.getResult().getId()).build());
			System.out.println("Result version: " + getResponse.getResult().getCurrentVersion());

			System.out.println("Success...");
		} catch (Exception e) {
			System.out.println("Error: " + e);
			System.exit(1);
		}
	}
}
