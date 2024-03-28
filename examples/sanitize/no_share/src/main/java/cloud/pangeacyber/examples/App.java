package cloud.pangeacyber.examples;

import java.io.File;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.sanitize.SanitizeClient;
import cloud.pangeacyber.pangea.sanitize.requests.SanitizeRequest;

public class App {
    public static void main(String[] args) {
		var token = System.getenv("PANGEA_SANITIZE_TOKEN");
		var domain = System.getenv("PANGEA_DOMAIN");

		var config = new Config.Builder(token, domain).build();
        var client = new SanitizeClient.Builder(config).build();

		// Pick a file to sanitize.
		var file = new File("./sample.pdf");

		try {
			// Sanitize the file.
			var response = client.sanitize(
				new SanitizeRequest.Builder().uploadedFileName("sample.pdf").build(),
				file
			);
			var result = response.getResult();
			System.out.printf("Sanitized file is available at <%s>.\n", result.getDestURL());
		} catch (PangeaException error) {
			System.err.println("Operation error.");
			System.err.println(error);
			System.exit(1);
		} catch (PangeaAPIException error) {
			System.err.println("API error.");
			System.err.println(error);
			System.exit(1);
		}
    }
}
