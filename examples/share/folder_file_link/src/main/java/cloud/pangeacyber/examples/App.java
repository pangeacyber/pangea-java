package cloud.pangeacyber.examples;

import java.io.File;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.TransferMethod;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.share.ShareClient;
import cloud.pangeacyber.pangea.share.models.Authenticator;
import cloud.pangeacyber.pangea.share.models.AuthenticatorType;
import cloud.pangeacyber.pangea.share.models.LinkType;
import cloud.pangeacyber.pangea.share.models.ShareLinkCreateItem;
import cloud.pangeacyber.pangea.share.requests.DeleteRequest;
import cloud.pangeacyber.pangea.share.requests.FolderCreateRequest;
import cloud.pangeacyber.pangea.share.requests.PutRequest;
import cloud.pangeacyber.pangea.share.requests.ShareLinkCreateRequest;

public class App {
    public static void main(String[] args) {
		var token = System.getenv("PANGEA_SHARE_TOKEN");
		var domain = System.getenv("PANGEA_DOMAIN");

		var config = new Config.Builder(token, domain).build();
        var client = new ShareClient.Builder(config).build();

		// Mock data.
		var dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		var time = dtf.format(OffsetDateTime.now());
		var folder = String.format("/sdk_examples/%s", time);

		try {
			// Create a folder.
			var folderCreateResponse = client.folderCreate(new FolderCreateRequest.Builder().path(folder).build());
			var folderId = folderCreateResponse.getResult().getObject().getID();
			System.out.printf("Created folder with ID '%s'.\n", folderId);

			// Upload a file to the folder.
			var file = new File("./sample.txt");
			var putResponse = client.put(
				new PutRequest.Builder().parentID(folderId).transferMethod(TransferMethod.POST_URL).build(),
				file
			);
			var fileId = putResponse.getResult().getObject().getID();
			System.out.printf("Uploaded file with ID '%s'.\n", fileId);

			// Create a share link for the file.
			var authenticators = Arrays.asList(
				new Authenticator(AuthenticatorType.PASSWORD, "somepassword")
			);
			var links = Arrays.asList(
				new ShareLinkCreateItem.Builder()
					.targets(Arrays.asList(fileId))
					.linkType(LinkType.DOWNLOAD)
					.authenticators(authenticators)
					.build()
			);
			var shareResponse = client.shareLinkCreate(new ShareLinkCreateRequest.Builder().links(links).build());
			var shareLink = shareResponse.getResult().getShareLinkObjects().get(0).getLink();
			System.out.printf("Created share link: <%s>.\n", shareLink);

			// Later on, if desired, the folder can be deleted.
			client.delete(new DeleteRequest.Builder().id(folderId).force(true).build());
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
