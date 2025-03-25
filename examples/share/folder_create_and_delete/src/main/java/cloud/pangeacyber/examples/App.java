package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.share.ShareClient;
import cloud.pangeacyber.pangea.share.requests.*;
import cloud.pangeacyber.pangea.share.responses.*;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.Config;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class App {
    public static void main(String[] args) {
        Config cfg = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String time = dtf.format(LocalDateTime.now());
        String folderDelete = "/sdk_examples/delete/" + time;

        try {
            // Load service config from environment variables or create it with custom
            // values
            cfg = Config.fromEnvironment(ShareClient.serviceName);
        } catch (ConfigException e) {
            System.out.println(e);
            System.exit(1);
        }

        // Create Share client
        ShareClient client = new ShareClient.Builder(cfg).build();

        try {
            System.out.println("Creating folder...");
            FolderCreateResponse respCreate = client.folderCreate(
                    new FolderCreateRequest.Builder().folder(folderDelete).build());

            String id = respCreate.getResult().getObject().getID();
            System.out.printf("Create folder success. Folder ID: %s\n", id);

            System.out.println("Deleting folder...");
            DeleteResponse respDelete = client.delete(new DeleteRequest.Builder().id(id).build());
            System.out.printf("Deleted %d item(s)\n", respDelete.getResult().getCount());

        } catch (Exception e) {
            System.out.println("Failed to perform Share request: " + e);
            System.exit(1);
        }

    }
}
