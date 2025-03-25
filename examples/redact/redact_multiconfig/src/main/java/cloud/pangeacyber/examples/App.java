package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.redact.RedactClient;
import cloud.pangeacyber.pangea.redact.requests.RedactTextRequest;
import cloud.pangeacyber.pangea.redact.responses.RedactTextResponse;

public class App {
    public static void main(String[] args) {
        String token = System.getenv("PANGEA_REDACT_MULTICONFIG_TOKEN");
        String urlTemplate = System.getenv("PANGEA_URL_TEMPLATE");
        String configId = System.getenv("PANGEA_REDACT_CONFIG_ID");

        Config cfg = Config.builder().token(token).baseURLTemplate(urlTemplate).build();

        RedactClient client = new RedactClient.Builder(cfg).withConfigID(configId).build();
        String text = "Hello, my phone number is 123-456-7890";
        RedactTextResponse response = null;
        try {
            response = client.redactText(RedactTextRequest.builder().text(text).build());
        } catch (Exception e) {
            System.out.println("Failed to perform redact: " + e);
            System.exit(1);
        }

        System.out.println(String.format("Redacting PII from: '%s'", text));
        System.out.println(
                String.format("Redacted text: '%s'", response.getResult().getRedactedText()));
    }
}
