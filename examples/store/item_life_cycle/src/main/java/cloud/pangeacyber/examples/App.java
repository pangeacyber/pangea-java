package cloud.pangeacyber.examples;

import cloud.pangeacyber.pangea.store.StoreClient;
import cloud.pangeacyber.pangea.store.models.ArchiveFormat;
import cloud.pangeacyber.pangea.store.models.Authenticator;
import cloud.pangeacyber.pangea.store.models.AuthenticatorType;
import cloud.pangeacyber.pangea.store.models.FilterList;
import cloud.pangeacyber.pangea.store.models.LinkType;
import cloud.pangeacyber.pangea.store.models.Metadata;
import cloud.pangeacyber.pangea.store.models.ShareLinkCreateItem;
import cloud.pangeacyber.pangea.store.models.ShareLinkItem;
import cloud.pangeacyber.pangea.store.models.Tags;
import cloud.pangeacyber.pangea.store.requests.*;
import cloud.pangeacyber.pangea.store.responses.*;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.AttachedFile;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.TransferMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import java.io.File;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        Config cfg = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String time = dtf.format(LocalDateTime.now());
        String folderFiles = "/sdk_examples/files/" + time;
        String filepath = "./src/main/java/cloud/pangeacyber/examples/testfile.pdf";

        try {
            // Load service config from environment variables or create it with custom
            // values
            cfg = Config.fromEnvironment(StoreClient.serviceName);
        } catch (ConfigException e) {
            System.out.println(e);
            System.exit(1);
        }

        // Create Store client
        StoreClient client = new StoreClient.Builder(cfg).build();

        try {

            // Create a folder
            System.out.println("Creating folder...");
            FolderCreateResponse respCreate = client.folderCreate(
                    new FolderCreateRequest.Builder().path(folderFiles).build());
            String folderID = respCreate.getResult().getObject().getID();
            System.out.printf("Folder created success. Folder ID: %s\n", folderID);

            // Upload a file with path as unique param
            System.out.println("\nUploading file with path...");
            String path = folderFiles + "/" + time + "_file_multipart_1";
            File file = new File(filepath);
            // Set only path field with an absolute path to the file
            PutResponse respPutPath = client.put(
                    new PutRequest.Builder().path(path).transferMethod(TransferMethod.MULTIPART).build(),
                    file);
            System.out.printf("Upload success. Item ID: %s\n", respPutPath.getResult().getObject().getID());
            System.out.printf("\tParent ID: %s\n", respPutPath.getResult().getObject().getParentID());
            System.out.printf("\tMetadata: %s\n", respPutPath.getResult().getObject().getMetadata());
            System.out.printf("\tTags ID: %s\n", respPutPath.getResult().getObject().getTags());

            // Upload a file with parent id and name
            System.out.println("\nUploading file with name and parent id...");
            String name = time + "_file_multipart_2";
            Metadata metadata = new Metadata();
            Tags tags = new Tags();
            metadata.put("field1", "value1");
            metadata.put("field2", "value2");
            tags.add("tag1");
            tags.add("tag2");

            // Set a single name and a parent id.
            PutResponse respPutID = client.put(
                    new PutRequest.Builder()
                            .name(name)
                            .parentID(folderID)
                            .transferMethod(TransferMethod.MULTIPART)
                            .metadata(metadata)
                            .tags(tags)
                            .build(),
                    file);

            System.out.printf("Upload success. Item ID: %s\n", respPutID.getResult().getObject().getID());
            System.out.printf("\tParent ID: %s\n", respPutID.getResult().getObject().getParentID());
            System.out.printf("\tMetadata: %s\n", respPutID.getResult().getObject().getMetadata());
            System.out.printf("\tTags ID: %s\n", respPutID.getResult().getObject().getTags());

            // Update file with full metadata and tags
            System.out.println("\nUpdating file with full metadata and tags...");
            UpdateResponse respUpdate = client.update(
                    new UpdateRequest.Builder(respPutPath.getResult().getObject().getID()).metadata(metadata).tags(tags)
                            .build());

            System.out.printf("Upload success. Item ID: %s\n", respUpdate.getResult().getObject().getID());
            System.out.printf("\tParent ID: %s\n", respUpdate.getResult().getObject().getParentID());
            System.out.printf("\tMetadata: %s\n", respUpdate.getResult().getObject().getMetadata());
            System.out.printf("\tTags ID: %s\n", respUpdate.getResult().getObject().getTags());

            // Update file adding metadata and tags
            System.out.println("\nUpdating file adding metadata and tags...");
            Metadata addMetadata = new Metadata();
            Tags addTags = new Tags();
            addMetadata.put("field3", "value3");
            addTags.add("tag3");
            UpdateResponse respUpdateAdd = client.update(
                    new UpdateRequest.Builder(respPutPath.getResult().getObject().getID())
                            .addMetadata(addMetadata)
                            .addTags(addTags)
                            .build());
            System.out.printf("Upload success. Item ID: %s\n", respUpdateAdd.getResult().getObject().getID());
            System.out.printf("\tParent ID: %s\n", respUpdateAdd.getResult().getObject().getParentID());
            System.out.printf("\tMetadata: %s\n", respUpdateAdd.getResult().getObject().getMetadata());
            System.out.printf("\tTags ID: %s\n", respUpdateAdd.getResult().getObject().getTags());

            // Get archive multipart
            System.out.println("\nGetting archive with multipart transfer method...");
            GetArchiveResponse respGetArchive = client.getArchive(
                    new GetArchiveRequest.Builder(Arrays.asList(folderID))
                            .format(ArchiveFormat.TAR)
                            .transferMethod(TransferMethod.MULTIPART)
                            .build());
            // As transfer method is multipart, it should receive one attached file and dest
            // url empty
            System.out.printf("Got %d attached file(s)\n", respGetArchive.getAttachedFiles().size());
            System.out.printf("Got URL: %s\n", respGetArchive.getResult().getDestUrl());

            for (AttachedFile attachedFile : respGetArchive.getAttachedFiles()) {
                // Save each attached file. Should be just one.
                attachedFile.save(Paths.get("./download/", time, "archive.tar"));
            }

            // Get archive url
            System.out.println("\nGetting archive with dest-url transfer method...");
            GetArchiveResponse respGetArchiveURL = client.getArchive(
                    new GetArchiveRequest.Builder(Arrays.asList(folderID))
                            .format(ArchiveFormat.TAR)
                            .transferMethod(TransferMethod.DEST_URL)
                            .build());
            // As transfer method is dest-url, it should receive no attached files and dest
            // url not empty
            System.out.printf("Got %d attached file(s)\n", respGetArchiveURL.getAttachedFiles().size());
            System.out.printf("Got URL: %s\n", respGetArchiveURL.getResult().getDestUrl());

            // Download file
            String url = respGetArchiveURL.getResult().getDestUrl();
            AttachedFile attachedFile = client.downloadFile(url);

            // Create share link
            System.out.println("\nCreating a share link...");

            // It's necessary to create an authenticator list with the allowed authenticator
            // method to access the link
            List<Authenticator> authenticators = Arrays.asList(
                    new Authenticator(AuthenticatorType.PASSWORD, "somepassword"));

            // Create a link list, each with its target, link type and additional parameters
            List<ShareLinkCreateItem> linkList = Arrays.asList(
                    new ShareLinkCreateItem.Builder()
                            .targets(Arrays.asList(folderID))
                            .linkType(LinkType.EDITOR)
                            .maxAccessCount(3)
                            .authenticators(authenticators)
                            .build());

            // Send the request with the link list previously created
            ShareLinkCreateResponse respCreateLink = client.shareLinkCreate(
                    new ShareLinkCreateRequest.Builder().links(linkList).build());
            List<ShareLinkItem> links = respCreateLink.getResult().getShareLinkObjects();
            System.out.printf("Created %d link(s)\n", links.size());

            ShareLinkItem link = links.get(0);
            System.out.printf("Link ID: %s. Link: %s\n", link.getID(), link.getLink());

            // Get share link
            System.out.println("\nGetting already created link...");
            ShareLinkGetResponse respGetLink = client
                    .shareLinkGet(new ShareLinkGetRequest.Builder(link.getID()).build());
            System.out.printf("Got link ID: %s. Link: %s\n", respGetLink.getResult().getShareLinkObject().getID(),
                    respGetLink.getResult().getShareLinkObject().getLink());

            // List share link
            System.out.println("\nListing shared links...");
            ShareLinkListResponse respListLink = client.shareLinkList(new ShareLinkListRequest.Builder().build());
            System.out.printf("Got %d link(s)\n", respListLink.getResult().getCount());

            // Delete share link
            System.out.println("\nDeleting shared link...");
            ShareLinkDeleteResponse respDeleteLink = client.shareLinkDelete(
                    new ShareLinkDeleteRequest.Builder(Arrays.asList(link.getID())).build());

            System.out.printf("Deleted %d item(s)\n", respDeleteLink.getResult().getShareLinkObjects().size());

            // List files in folder
            System.out.println("\nListing files in folder...");

            // Create a FilterList and set its options
            FilterList filterList = new FilterList();
            filterList.folder().set(folderFiles);

            ListResponse respList = client.list(new ListRequest.Builder().filter(filterList).build());
            System.out.printf("Got %d file(s)\n", respList.getResult().getCount());

        } catch (Exception e) {
            System.out.println("Failed to perform store request: " + e);
            System.exit(1);
        }

    }
}
