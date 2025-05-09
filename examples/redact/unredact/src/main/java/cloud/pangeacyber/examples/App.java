package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.redact.RedactClient;
import cloud.pangeacyber.pangea.redact.models.FPEAlphabet;
import cloud.pangeacyber.pangea.redact.models.RedactType;
import cloud.pangeacyber.pangea.redact.models.RedactionMethodOverrides;
import cloud.pangeacyber.pangea.redact.requests.RedactTextRequest;
import cloud.pangeacyber.pangea.redact.requests.UnredactRequest;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import java.util.Map;
import cloud.pangeacyber.pangea.Config;

public class App {
	public static void main(String[] args) throws ConfigException, PangeaException, PangeaAPIException {
		final var cfg = Config.fromEnvironment(RedactClient.serviceName);
		final var client = new RedactClient.Builder(cfg).build();

		final var text = "Hello, my phone number is 123-456-7890";
		System.out.printf("Redacting PII from %s\n", text);
		final var redacted = client.redactText(
				RedactTextRequest
						.builder()
						.text(text)
						.redactionMethodOverrides(Map.of("PHONE_NUMBER",
								RedactionMethodOverrides.builder().redactionType(RedactType.FPE)
										.fpeAlphabet(FPEAlphabet.NUMERIC).build()))
						.build());
		System.out.printf("Redacted text: %s\n", redacted.getResult().getRedactedText());

		final var unredacted = client.unredact(
				UnredactRequest
						.builder()
						.redactedData(redacted.getResult().getRedactedText())
						.fpeContext(redacted.getResult().getFpeContext())
						.build());
		System.out.printf("Unredacted text: %s\n", unredacted.getResult().getData());
	}
}
