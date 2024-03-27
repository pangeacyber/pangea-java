package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.share.ShareClient;
import cloud.pangeacyber.pangea.share.ShareFileUploader;
import cloud.pangeacyber.pangea.share.requests.*;
import cloud.pangeacyber.pangea.share.responses.*;
import cloud.pangeacyber.pangea.exceptions.AcceptedRequestException;
import cloud.pangeacyber.pangea.exceptions.AcceptedResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.FileData;
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
            System.out.println("Requesting upload URL with put-url transfer method...");
            String name = time + "_file_split_put_url";

            // Request Upload URL with put-url transfer method
            AcceptedResponse acceptedResponse = client.requestUploadURL(
                    new PutRequest.Builder().name(name).transferMethod(TransferMethod.PUT_URL).build());

            String url = acceptedResponse.getResult().getPutURL();
            System.out.printf("Got URL: %s\n", url);

            // Create a File and FileData to upload
            File file = new File(filepath);
            FileData fileData = new FileData(file, "file");

            // Create a ShareFileUploader. It will be used to post the file to the upload
            // url
            ShareFileUploader fileUploader = new ShareFileUploader.Builder().build();

            System.out.println("Uploading file...");
            fileUploader.uploadFile(url, TransferMethod.PUT_URL, fileData);

            PutResponse response;
            int maxRetry = 12, retry;
            // After uploading the file, it's necessary to poll the result from Share service
            for (retry = 0; retry < maxRetry; retry++) {
                try {
                    System.out.printf("Polling result. Retry: %d\n", retry);
                    // Sleep 10 seconds until result is (should) be ready
                    Thread.sleep(10 * 1000);

                    // Poll result, this could raise another AcceptedRequestException if result is
                    // not ready
                    response = client.pollResult(acceptedResponse.getRequestId(), PutResponse.class);
                    System.out.printf("Poll result success. Item ID: %s\n", response.getResult().getObject().getID());
                    break;
                } catch (AcceptedRequestException e) {
                    System.out.println("Result is not ready yet...");
                }
            }

            if (retry >= 12) {
                System.out.println("Failed to poll result. Reached max retries.");
            }

        } catch (Exception e) {
            System.out.println("Failed to perform share request: " + e);
            System.exit(1);
        }

    }
}
