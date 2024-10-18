package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.share.ShareClient;
import cloud.pangeacyber.pangea.share.models.Authenticator;
import cloud.pangeacyber.pangea.share.models.AuthenticatorType;
import cloud.pangeacyber.pangea.share.models.LinkType;
import cloud.pangeacyber.pangea.share.models.ShareLinkCreateItem;
import cloud.pangeacyber.pangea.share.requests.PutRequest;
import cloud.pangeacyber.pangea.share.requests.ShareLinkCreateRequest;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * An example command line utility that creates an email+code, SMS+code, or
 * password-secured download/upload/editor share-link for a given file or for
 * each file in a given directory.
 */
public class App {
	@Parameter(names = { "--input", "-i" }, description = "Local path to upload.", required = true)
	private String inputPath;

	@Parameter(names = "--dest", description = "Destination path in Share.")
	private String dest = "/";

	@Parameter(names = "--email", description = "Email address to protect the share link with.")
	private String email;

	@Parameter(names = "--phone", description = "Phone number to protect the share link with.")
	private String phone;

	@Parameter(names = "--password", description = "Password to protect the share link with.")
	private String password;

	@Parameter(names = "--link-type", description = "Type of link.")
	private LinkType linkType = LinkType.DOWNLOAD;

	private void run() throws PangeaException, PangeaAPIException {
		if (email == null && phone == null && password == null) {
			throw new IllegalArgumentException("At least one of --email, --phone, or --password must be provided.");
		}

		final var authenticators = new ArrayList<Authenticator>();
		if (password != null) {
			authenticators.add(new Authenticator(AuthenticatorType.PASSWORD, password));
		}
		if (email != null) {
			authenticators.add(new Authenticator(AuthenticatorType.EMAIL_OTP, email));
		}
		if (phone != null) {
			authenticators.add(new Authenticator(AuthenticatorType.SMS_OTP, phone));
		}

		// Create a Secure Share client.
		final var token = System.getenv("PANGEA_VAULT_TOKEN");
		final var domain = System.getenv("PANGEA_DOMAIN");
		final var config = new Config.Builder(token, domain).build();
		final var client = new ShareClient.Builder(config).build();

		// Upload files.
		final var inputFile = new File(this.inputPath);
		final var files = inputFile.isDirectory()
				? inputFile.listFiles()
				: new File[] { inputFile };
		final var objectIds = new ArrayList<String>();
		for (var file : files) {
			final var uploadResponse = client
					.put(new PutRequest.Builder().folder(this.dest + "/" + file.getName()).build(), file);
			objectIds.add(uploadResponse.getResult().getObject().getID());
		}

		// Create share link.
		final var links = Arrays.asList(
				new ShareLinkCreateItem.Builder()
						.targets(objectIds)
						.linkType(this.linkType)
						.authenticators(authenticators)
						.build());
		final var response = client.shareLinkCreate(new ShareLinkCreateRequest.Builder().links(links).build());
		System.out.println(response.getResult().getShareLinkObjects().get(0).getLink());
	}

	public static void main(String[] args) throws PangeaException, PangeaAPIException {
		final var app = new App();
		final var commander = JCommander.newBuilder().addObject(app).build();
		try {
			commander.parse(args);
			app.run();
		} catch (ParameterException e) {
			commander.usage();
		}
	}
}
