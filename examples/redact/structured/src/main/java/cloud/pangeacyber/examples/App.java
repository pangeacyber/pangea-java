package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.redact.RedactClient;
import cloud.pangeacyber.pangea.redact.requests.RedactStructuredRequest;
import cloud.pangeacyber.pangea.redact.responses.RedactStructuredResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;

import java.util.HashMap;

import cloud.pangeacyber.pangea.Config;

public class App {
	public static void main(String[] args) {
		Config cfg = null;
		try {
			cfg = Config.fromEnvironment(RedactClient.serviceName);
		} catch (ConfigException e) {
			System.out.println(e);
			System.exit(1);
			return;
		}

		final var client = new RedactClient.Builder(cfg).build();
		RedactStructuredResponse response = null;
		final var data = new HashMap<String, String>();
		data.put("Name", "My name is Andres");
		data.put("Contact", "My phone number is 123-456-7890");
		try {
			response = client.redactStructured(RedactStructuredRequest.builder().data(data).build());
		} catch (Exception e) {
			System.out.println("Failed to perform redact: " + e);
			System.exit(1);
			return;
		}

		System.out.println("Redact success");
		System.out.println("Redacted data: " + response.getResult().getRedactedData());
	}
}
