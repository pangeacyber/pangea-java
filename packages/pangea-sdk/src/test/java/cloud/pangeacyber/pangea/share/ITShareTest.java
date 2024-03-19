package cloud.pangeacyber.pangea.share;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cloud.pangeacyber.pangea.AttachedFile;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.FileData;
import cloud.pangeacyber.pangea.FileUploader;
import cloud.pangeacyber.pangea.Helper;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.TransferMethod;
import cloud.pangeacyber.pangea.Utils;
import cloud.pangeacyber.pangea.exceptions.AcceptedResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.file_scan.models.FileParams;
import cloud.pangeacyber.pangea.share.models.*;
import cloud.pangeacyber.pangea.share.requests.*;
import cloud.pangeacyber.pangea.share.responses.*;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ITShareTest {

	ShareClient client;
	static TestEnvironment environment;
	final String TESTFILE_PATH = "./src/test/java/cloud/pangeacyber/pangea/testdata/testfile.pdf";
	final String ZERO_BYTES_FILE_PATH = "./src/test/java/cloud/pangeacyber/pangea/testdata/zerobytes.txt";
	private String FOLDER_DELETE;
	private String FOLDER_FILES;
	private final Metadata metadata = new Metadata();
	private final Metadata addMetadata = new Metadata();
	private final Tags tags = new Tags();
	private final Tags addTags = new Tags();
	String time;

	@BeforeAll
	public static void setUpClass() throws Exception {
		environment = Helper.loadTestEnvironment("share", TestEnvironment.LIVE);
	}

	@BeforeEach
	public void setUp() throws ConfigException {
		client = new ShareClient.Builder(Config.fromIntegrationEnvironment(environment)).build();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		time = dtf.format(LocalDateTime.now());
		FOLDER_DELETE = "/sdk_tests/delete/" + time;
		FOLDER_FILES = "/sdk_tests/files/" + time;
		metadata.put("field1", "value1");
		metadata.put("field2", "value2");
		addMetadata.put("field3", "value3");
		tags.add("tag1");
		tags.add("tag2");
		addTags.add("tag3");
	}

	@Test
	public void testFolder() throws PangeaException, PangeaAPIException {
		FolderCreateResponse respCreate = client.folderCreate(
			new FolderCreateRequest.Builder().path(FOLDER_DELETE).build()
		);

		assertTrue(respCreate.isOk());
		assertNotNull(respCreate.getResult().getObject().getID());
		assertNotEquals(respCreate.getResult().getObject().getID(), "");
		assertNotNull(respCreate.getResult().getObject().getName());
		assertNotEquals(respCreate.getResult().getObject().getName(), "");
		assertNotNull(respCreate.getResult().getObject().getCreatedAt());
		assertNotEquals(respCreate.getResult().getObject().getCreatedAt(), "");
		assertNotNull(respCreate.getResult().getObject().getUpdatedAt());
		assertNotEquals(respCreate.getResult().getObject().getUpdatedAt(), "");

		String id = respCreate.getResult().getObject().getID();

		DeleteResponse respDelete = client.delete(new DeleteRequest.Builder().id(id).build());
		assertTrue(respDelete.isOk());
		assertEquals(respDelete.getResult().getCount(), 1);
	}

	@Test
	public void testPut_TransferMethod_PostURL() throws PangeaException, PangeaAPIException {
		File file = new File(TESTFILE_PATH);
		String name = time + "_file_post_url";
		PutResponse respPut = client.put(
			new PutRequest.Builder().name(name).transferMethod(TransferMethod.POST_URL).build(),
			file
		);

		assertTrue(respPut.isOk());
		String id = respPut.getResult().getObject().getID();
		assertNotNull(id);

		// Get multipart
		GetResponse respGet = client.get(
			new GetRequest.Builder().id(id).transferMethod(TransferMethod.MULTIPART).build()
		);

		assertTrue(respGet.isOk());
		assertNull(respGet.getResult().getDestUrl());
		assertEquals(respGet.getAttachedFiles().size(), 1);
		AttachedFile attachedFile = respGet.getAttachedFiles().get(0);
		attachedFile.save(Paths.get("./download/", attachedFile.getFilename()));

		// Get dest-url
		respGet = client.get(new GetRequest.Builder().id(id).transferMethod(TransferMethod.DEST_URL).build());

		assertTrue(respGet.isOk());
		assertEquals(respGet.getAttachedFiles().size(), 0);
		assertNotNull(respGet.getResult().getDestUrl());
	}

	@Test
	public void testPut_ZeroBytes_TransferMethod_PostURL() throws PangeaException, PangeaAPIException {
		File file = new File(ZERO_BYTES_FILE_PATH);
		String name = time + "_file_post_url";
		PutResponse respPut = client.put(
			new PutRequest.Builder().name(name).transferMethod(TransferMethod.POST_URL).build(),
			file
		);

		assertTrue(respPut.isOk());
		String id = respPut.getResult().getObject().getID();
		assertNotNull(id);

		// Get multipart
		GetResponse respGet = client.get(
			new GetRequest.Builder().id(id).transferMethod(TransferMethod.MULTIPART).build()
		);

		assertTrue(respGet.isOk());
		assertNull(respGet.getResult().getDestUrl());
		assertEquals(respGet.getAttachedFiles().size(), 1);
		AttachedFile attachedFile = respGet.getAttachedFiles().get(0);
		attachedFile.save(Paths.get("./download/", attachedFile.getFilename()));

		// Get dest-url
		respGet = client.get(new GetRequest.Builder().id(id).transferMethod(TransferMethod.DEST_URL).build());

		assertTrue(respGet.isOk());
		assertEquals(respGet.getAttachedFiles().size(), 0);
		assertNull(respGet.getResult().getDestUrl());
	}

	@Test
	public void testPut_TransferMethod_Multipart() throws PangeaException, PangeaAPIException {
		File file = new File(TESTFILE_PATH);
		String name = time + "_file_multipart";

		PutResponse respPut = client.put(
			new PutRequest.Builder().name(name).transferMethod(TransferMethod.MULTIPART).build(),
			file
		);

		assertTrue(respPut.isOk());

		String id = respPut.getResult().getObject().getID();
		assertNotNull(id);

		// Get multipart
		GetResponse respGet = client.get(
			new GetRequest.Builder().id(id).transferMethod(TransferMethod.MULTIPART).build()
		);

		assertTrue(respGet.isOk());
		assertNull(respGet.getResult().getDestUrl());
		assertEquals(respGet.getAttachedFiles().size(), 1);
		AttachedFile attachedFile = respGet.getAttachedFiles().get(0);
		attachedFile.save(Paths.get("./download/", attachedFile.getFilename()));

		// Get dest-url
		respGet = client.get(new GetRequest.Builder().id(id).transferMethod(TransferMethod.DEST_URL).build());

		assertTrue(respGet.isOk());
		assertEquals(respGet.getAttachedFiles().size(), 0);
		assertNotNull(respGet.getResult().getDestUrl());
	}

	@Test
	public void testPut_ZeroBytes_TransferMethod_Multipart() throws PangeaException, PangeaAPIException {
		File file = new File(ZERO_BYTES_FILE_PATH);
		String name = time + "_file_multipart";

		PutResponse respPut = client.put(
			new PutRequest.Builder().name(name).transferMethod(TransferMethod.MULTIPART).build(),
			file
		);

		assertTrue(respPut.isOk());

		String id = respPut.getResult().getObject().getID();
		assertNotNull(id);

		// Get multipart
		GetResponse respGet = client.get(
			new GetRequest.Builder().id(id).transferMethod(TransferMethod.MULTIPART).build()
		);

		assertTrue(respGet.isOk());
		assertNull(respGet.getResult().getDestUrl());
		assertEquals(respGet.getAttachedFiles().size(), 1);
		AttachedFile attachedFile = respGet.getAttachedFiles().get(0);
		attachedFile.save(Paths.get("./download/", attachedFile.getFilename()));

		// Get dest-url
		respGet = client.get(new GetRequest.Builder().id(id).transferMethod(TransferMethod.DEST_URL).build());

		assertTrue(respGet.isOk());
		assertEquals(respGet.getAttachedFiles().size(), 0);
		assertNull(respGet.getResult().getDestUrl());
	}

	@Test
	public void testFileScan_SplitUpload_Post() throws PangeaException, PangeaAPIException, InterruptedException {
		String name = time + "_file_split_post_url";
		File file = new File(TESTFILE_PATH);

		FileParams fileParams = Utils.getFileUploadParams(file);

		AcceptedResponse acceptedResponse = client.requestUploadURL(
			new PutRequest.Builder()
				.name(name)
				.transferMethod(TransferMethod.POST_URL)
				.sha256(fileParams.getSHA256())
				.crc32c(fileParams.getCRC32C())
				.size(fileParams.getSize())
				.build()
		);

		String url = acceptedResponse.getResult().getPostURL();
		Map<String, Object> details = acceptedResponse.getResult().getPostFormData();

		FileData fileData = new FileData(file, "file", details);

		FileUploader fileUploader = new FileUploader.Builder().build();
		fileUploader.uploadFile(url, TransferMethod.POST_URL, fileData);

		PutResponse response;
		int maxRetry = 12;
		for (int retry = 0; retry < maxRetry; retry++) {
			try {
				// Sleep 10 seconds until result is (should) be ready
				Thread.sleep(10 * 1000);

				// Poll result, this could raise another AcceptedRequestException if result is not ready
				response = client.pollResult(acceptedResponse.getRequestId(), PutResponse.class);
				assertTrue(response.isOk());
				break;
			} catch (PangeaAPIException e) {
				assertTrue(retry < maxRetry - 1);
			}
		}
	}

	@Test
	public void testFileScan_SplitUpload_Put() throws PangeaException, PangeaAPIException, InterruptedException {
		String name = time + "_file_split_put_url";

		AcceptedResponse acceptedResponse = client.requestUploadURL(
			new PutRequest.Builder().name(name).transferMethod(TransferMethod.PUT_URL).build()
		);

		String url = acceptedResponse.getResult().getPutURL();
		File file = new File(TESTFILE_PATH);
		FileData fileData = new FileData(file, "file");

		FileUploader fileUploader = new FileUploader.Builder().build();
		fileUploader.uploadFile(url, TransferMethod.PUT_URL, fileData);

		PutResponse response;
		int maxRetry = 12;
		for (int retry = 0; retry < maxRetry; retry++) {
			try {
				// Sleep 10 seconds until result is (should) be ready
				Thread.sleep(10 * 1000);

				// Poll result, this could raise another AcceptedRequestException if result is not ready
				response = client.pollResult(acceptedResponse.getRequestId(), PutResponse.class);
				assertTrue(response.isOk());
				break;
			} catch (PangeaAPIException e) {
				assertTrue(retry < maxRetry - 1);
			}
		}
	}

	@Test
	public void testLifeCycle() throws PangeaException, PangeaAPIException {
		// Create a folder
		FolderCreateResponse respCreate = client.folderCreate(
			new FolderCreateRequest.Builder().path(FOLDER_FILES).build()
		);

		assertTrue(respCreate.isOk());
		String folderID = respCreate.getResult().getObject().getID();

		// Upload a file with path as unique param
		String path = FOLDER_FILES + "/" + time + "_file_multipart_1";
		File file = new File(TESTFILE_PATH);
		PutResponse respPutPath = client.put(
			new PutRequest.Builder().path(path).transferMethod(TransferMethod.MULTIPART).build(),
			file
		);

		assertTrue(respPutPath.isOk());
		assertEquals(folderID, respPutPath.getResult().getObject().getParentID());
		assertNull(respPutPath.getResult().getObject().getMetadata());
		assertNull(respPutPath.getResult().getObject().getTags());
		assertNull(respPutPath.getResult().getObject().getMD5());
		assertNull(respPutPath.getResult().getObject().getSHA512());
		assertNotNull(respPutPath.getResult().getObject().getSHA256());

		// Upload a file with parent id and name
		String name = time + "_file_multipart_2";
		PutResponse respPutID = client.put(
			new PutRequest.Builder()
				.name(name)
				.parentID(folderID)
				.transferMethod(TransferMethod.MULTIPART)
				.metadata(metadata)
				.tags(tags)
				.build(),
			file
		);

		assertTrue(respPutID.isOk());
		assertEquals(folderID, respPutID.getResult().getObject().getParentID());
		assertEquals(respPutID.getResult().getObject().getMetadata(), metadata);
		assertEquals(respPutID.getResult().getObject().getTags(), tags);
		assertNull(respPutID.getResult().getObject().getMD5());
		assertNull(respPutID.getResult().getObject().getSHA512());
		assertNotNull(respPutID.getResult().getObject().getSHA256());

		// Update file. full metadata and tags
		UpdateResponse respUpdate = client.update(
			new UpdateRequest.Builder(respPutPath.getResult().getObject().getID()).metadata(metadata).tags(tags).build()
		);
		assertTrue(respUpdate.isOk());
		assertEquals(respUpdate.getResult().getObject().getMetadata(), metadata);
		assertEquals(respUpdate.getResult().getObject().getTags(), tags);

		// Update file. add metadata and tags
		UpdateResponse respUpdateAdd = client.update(
			new UpdateRequest.Builder(respPutPath.getResult().getObject().getID())
				.addMetadata(addMetadata)
				.addTags(addTags)
				.build()
		);
		assertTrue(respUpdateAdd.isOk());
		Metadata metadataFinal = new Metadata();
		metadataFinal.putAll(metadata);
		metadataFinal.putAll(addMetadata);

		Tags tagsFinal = new Tags();
		tagsFinal.addAll(tags);
		tagsFinal.addAll(addTags);

		assertEquals(respUpdateAdd.getResult().getObject().getMetadata(), metadataFinal);
		assertEquals(respUpdateAdd.getResult().getObject().getTags(), tagsFinal);

		// Get archive multipart
		GetArchiveResponse respGetArchive = client.getArchive(
			new GetArchiveRequest.Builder(Arrays.asList(folderID))
				.format(ArchiveFormat.TAR)
				.transferMethod(TransferMethod.MULTIPART)
				.build()
		);
		assertTrue(respGetArchive.isOk());
		assertEquals(respGetArchive.getAttachedFiles().size(), 1);
		assertNull(respGetArchive.getResult().getDestUrl());

		for (AttachedFile attachedFile : respGetArchive.getAttachedFiles()) {
			assertEquals(attachedFile.getContentType(), "application/octet-stream");
			attachedFile.save(Paths.get("./download/", time, "archive.tar"));
			// Test double save. Should save it anyway creating a new "_1" postfix file
			attachedFile.save(Paths.get("./download/", time, "archive.tar"));
		}

		// Get archive url
		GetArchiveResponse respGetArchiveURL = client.getArchive(
			new GetArchiveRequest.Builder(Arrays.asList(folderID))
				.format(ArchiveFormat.TAR)
				.transferMethod(TransferMethod.DEST_URL)
				.build()
		);
		assertTrue(respGetArchiveURL.isOk());
		assertEquals(respGetArchiveURL.getAttachedFiles().size(), 0);
		assertNotNull(respGetArchiveURL.getResult().getDestUrl());

		// Download file
		String url = respGetArchiveURL.getResult().getDestUrl();
		AttachedFile attachedFile = client.downloadFile(url);
		assertNotNull(attachedFile);
		assertNotNull(attachedFile.getContentType());
		assertNotNull(attachedFile.getFilename());
		assertNotNull(attachedFile.getFileContent());

		// Create share link
		List<Authenticator> authenticators = Arrays.asList(
			new Authenticator(AuthenticatorType.PASSWORD, "somepassword")
		);
		List<ShareLinkCreateItem> linkList = Arrays.asList(
			new ShareLinkCreateItem.Builder()
				.targets(Arrays.asList(folderID))
				.linkType(LinkType.EDITOR)
				.maxAccessCount(3)
				.authenticators(authenticators)
				.build()
		);

		ShareLinkCreateResponse respCreateLink = client.shareLinkCreate(
			new ShareLinkCreateRequest.Builder().links(linkList).build()
		);
		assertTrue(respCreateLink.isOk());
		List<ShareLinkItem> links = respCreateLink.getResult().getShareLinkObjects();
		assertEquals(links.size(), 1);

		ShareLinkItem link = links.get(0);
		assertEquals(link.getAccessCount(), 0);
		assertEquals(link.getMaxAccessCount(), 3);
		assertEquals(link.getAuthenticators().size(), 1);
		assertEquals(link.getAuthenticators().get(0).getAuthType(), AuthenticatorType.PASSWORD);
		assertNotNull(link.getLink());
		assertNotNull(link.getID());
		assertEquals(link.getTargets().size(), 1);

		// Get share link
		ShareLinkGetResponse respGetLink = client.shareLinkGet(new ShareLinkGetRequest.Builder(link.getID()).build());
		assertTrue(respGetLink.isOk());
		assertEquals(respGetLink.getResult().getShareLinkObject().getLink(), link.getLink());
		assertEquals(respGetLink.getResult().getShareLinkObject().getAccessCount(), 0);
		assertEquals(respGetLink.getResult().getShareLinkObject().getMaxAccessCount(), 3);
		assertEquals(respGetLink.getResult().getShareLinkObject().getExpiresAt(), link.getExpiresAt());
		assertEquals(respGetLink.getResult().getShareLinkObject().getCreatedAt(), link.getCreatedAt());

		// List share link
		ShareLinkListResponse respListLink = client.shareLinkList(new ShareLinkListRequest.Builder().build());
		assertTrue(respListLink.isOk());
		assertTrue(respListLink.getResult().getCount() > 0);
		assertTrue(respListLink.getResult().getShareLinkObjects().size() > 0);

		// Delete share link
		ShareLinkDeleteResponse respDeleteLink = client.shareLinkDelete(
			new ShareLinkDeleteRequest.Builder(Arrays.asList(link.getID())).build()
		);
		assertTrue(respDeleteLink.isOk());
		assertEquals(respDeleteLink.getResult().getShareLinkObjects().size(), 1);

		// List files in folder
		FilterList filterList = new FilterList();
		filterList.folder().set(FOLDER_FILES);
		ListResponse respList = client.list(new ListRequest.Builder().filter(filterList).build());
		assertTrue(respList.isOk());
		assertEquals(respList.getResult().getCount(), 2);
		assertEquals(respList.getResult().getObjects().size(), 2);
	}
}
