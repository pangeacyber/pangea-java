package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.share.ShareClient;
import cloud.pangeacyber.pangea.share.requests.*;
import cloud.pangeacyber.pangea.share.responses.*;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.TransferMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;

public class App {
    public static void main(String[] args) {
        Config cfg = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String time = dtf.format(LocalDateTime.now());
        String filepath = "./src/main/java/cloud/pangeacyber/examples/testfile.pdf";

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
            System.out.println("Uploading file with multipart transfer method...");
            File file = new File(filepath);
            String name = time + "_file_multipart";
            PutResponse respPut = client.put(
                    new PutRequest.Builder().name(name).transferMethod(TransferMethod.MULTIPART).build(),
                    file);

            System.out.printf("File upload success. Item ID: %s\n", respPut.getResult().getObject().getID());

        } catch (Exception e) {
            System.out.println("Failed to perform share request: " + e);
            System.exit(1);
        }

    }
}
