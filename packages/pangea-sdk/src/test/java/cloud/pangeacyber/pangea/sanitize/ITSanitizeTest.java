package cloud.pangeacyber.pangea.sanitize;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cloud.pangeacyber.pangea.AttachedFile;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.FileData;
import cloud.pangeacyber.pangea.FileUploader;
import cloud.pangeacyber.pangea.Helper;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.TransferMethod;
import cloud.pangeacyber.pangea.Utils;
import cloud.pangeacyber.pangea.exceptions.AcceptedRequestException;
import cloud.pangeacyber.pangea.exceptions.AcceptedResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.file_scan.models.FileParams;
import cloud.pangeacyber.pangea.sanitize.models.Content;
import cloud.pangeacyber.pangea.sanitize.models.SanitizeFile;
import cloud.pangeacyber.pangea.sanitize.models.ShareOutput;
import cloud.pangeacyber.pangea.sanitize.requests.SanitizeRequest;
import cloud.pangeacyber.pangea.sanitize.responses.SanitizeResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ITSanitizeTest {

	final String TESTFILE_PATH = "./src/test/java/cloud/pangeacyber/pangea/testdata/ds11.pdf";

	SanitizeClient client;
	static TestEnvironment environment;

	@BeforeClass
	public static void setUpClass() throws Exception {
		environment = Helper.loadTestEnvironment("sanitize", TestEnvironment.LIVE);
	}

	@Before
	public void setUp() throws Exception {
		client = new SanitizeClient.Builder(Config.fromIntegrationEnvironment(environment)).build();
	}

	@Test
	public void testSanitizeAndShare() throws PangeaException, PangeaException, PangeaAPIException, IOException {
		File file = new File(TESTFILE_PATH);
		SanitizeResponse response = client.sanitize(
			new SanitizeRequest.Builder()
				.file(new SanitizeFile.Builder().cdrProvider("apryse").scanProvider("crowdstrike").build())
				.content(
					new Content.Builder()
						.urlIntel(true)
						.urlIntelProvider("crowdstrike")
						.domainIntel(true)
						.domainIntelProvider("crowdstrike")
						.defang(true)
						.defangThreshold(20)
						.removeInteractive(true)
						.removeAttachments(true)
						.redact(true)
						.build()
				)
				.shareOutput(new ShareOutput.Builder().enabled(true).outputFolder("sdk_test/sanitize/").build())
				.uploadedFileName("uploaded_file")
				.transferMethod(TransferMethod.POST_URL)
				.build(),
			file
		);

		assertTrue(response.isOk());
		assertNull(response.getResult().getDestURL());
		assertNotNull(response.getResult().getDestShareID());
		assertNotNull(response.getResult().getData().getRedact());
		assertTrue(response.getResult().getData().getRedact().getRedactionCount() > 0);
		assertNotNull(response.getResult().getData().getRedact().getSummaryCounts());
		assertNotNull(response.getResult().getData().getDefang());
		assertTrue(response.getResult().getData().getDefang().getExternalURLsCount() > 0);
		assertTrue(response.getResult().getData().getDefang().getExternalDomainsCount() > 0);
		assertEquals((int) response.getResult().getData().getDefang().getDefangedCount(), 0);
		assertNotNull(response.getResult().getData().getDefang().getDomainIntelSummary());
		assertEquals((int) response.getResult().getData().getCDR().getFileAttachmentsRemoved(), 0);
		assertEquals((int) response.getResult().getData().getCDR().getInteractiveContentsRemoved(), 0);
		assertFalse(response.getResult().getData().getMaliciousFile());
	}

	@Test
	public void testSanitizeNoShare() throws PangeaException, PangeaException, PangeaAPIException, IOException {
		File file = new File(TESTFILE_PATH);
		SanitizeResponse response = client.sanitize(
			new SanitizeRequest.Builder()
				.file(new SanitizeFile.Builder().cdrProvider("apryse").scanProvider("crowdstrike").build())
				.content(
					new Content.Builder()
						.urlIntel(true)
						.urlIntelProvider("crowdstrike")
						.domainIntel(true)
						.domainIntelProvider("crowdstrike")
						.defang(true)
						.defangThreshold(20)
						.removeInteractive(true)
						.removeAttachments(true)
						.redact(true)
						.build()
				)
				.shareOutput(new ShareOutput.Builder().enabled(false).build())
				.uploadedFileName("uploaded_file")
				.transferMethod(TransferMethod.POST_URL)
				.build(),
			file
		);

		assertTrue(response.isOk());
		assertNotNull(response.getResult().getDestURL());
		assertNull(response.getResult().getDestShareID());
		assertNotNull(response.getResult().getData().getRedact());
		assertTrue(response.getResult().getData().getRedact().getRedactionCount() > 0);
		assertNotNull(response.getResult().getData().getRedact().getSummaryCounts());
		assertNotNull(response.getResult().getData().getDefang());
		assertTrue(response.getResult().getData().getDefang().getExternalURLsCount() > 0);
		assertTrue(response.getResult().getData().getDefang().getExternalDomainsCount() > 0);
		assertEquals((int) response.getResult().getData().getDefang().getDefangedCount(), 0);
		assertNotNull(response.getResult().getData().getDefang().getDomainIntelSummary());
		assertEquals((int) response.getResult().getData().getCDR().getFileAttachmentsRemoved(), 0);
		assertEquals((int) response.getResult().getData().getCDR().getInteractiveContentsRemoved(), 0);
		assertFalse(response.getResult().getData().getMaliciousFile());

		AttachedFile attachedFile = client.downloadFile(response.getResult().getDestURL());
		attachedFile.save(Path.of("./", attachedFile.getFilename()));
	}

	@Test
	public void testSanitizeAllDefaults() throws PangeaException, PangeaException, PangeaAPIException, IOException {
		File file = new File(TESTFILE_PATH);
		SanitizeResponse response = client.sanitize(
			new SanitizeRequest.Builder().uploadedFileName("uploaded_file").build(),
			file
		);

		assertTrue(response.isOk());
		assertNotNull(response.getResult().getDestURL());
		assertNull(response.getResult().getDestShareID());
		assertNull(response.getResult().getData().getRedact());
		assertNotNull(response.getResult().getData().getDefang());
		assertTrue(response.getResult().getData().getDefang().getExternalURLsCount() > 0);
		assertTrue(response.getResult().getData().getDefang().getExternalDomainsCount() > 0);
		assertEquals((int) response.getResult().getData().getDefang().getDefangedCount(), 0);
		assertNotNull(response.getResult().getData().getDefang().getDomainIntelSummary());
		assertEquals((int) response.getResult().getData().getCDR().getFileAttachmentsRemoved(), 0);
		assertEquals((int) response.getResult().getData().getCDR().getInteractiveContentsRemoved(), 0);
		assertFalse(response.getResult().getData().getMaliciousFile());
	}

	@Test
	public void testSanitizeMultipart() throws PangeaException, PangeaException, PangeaAPIException, IOException {
		File file = new File(TESTFILE_PATH);
		SanitizeResponse response = client.sanitize(
			new SanitizeRequest.Builder()
				.file(new SanitizeFile.Builder().cdrProvider("apryse").scanProvider("crowdstrike").build())
				.content(
					new Content.Builder()
						.urlIntel(true)
						.urlIntelProvider("crowdstrike")
						.domainIntel(true)
						.domainIntelProvider("crowdstrike")
						.defang(true)
						.defangThreshold(20)
						.removeInteractive(true)
						.removeAttachments(true)
						.redact(true)
						.build()
				)
				.shareOutput(new ShareOutput.Builder().enabled(true).outputFolder("sdk_test/sanitize/").build())
				.uploadedFileName("uploaded_file")
				.transferMethod(TransferMethod.MULTIPART)
				.build(),
			file
		);

		assertTrue(response.isOk());
		assertNull(response.getResult().getDestURL());
		assertNotNull(response.getResult().getDestShareID());
		assertNotNull(response.getResult().getData().getRedact());
		assertTrue(response.getResult().getData().getRedact().getRedactionCount() > 0);
		assertNotNull(response.getResult().getData().getRedact().getSummaryCounts());
		assertNotNull(response.getResult().getData().getDefang());
		assertTrue(response.getResult().getData().getDefang().getExternalURLsCount() > 0);
		assertTrue(response.getResult().getData().getDefang().getExternalDomainsCount() > 0);
		assertEquals((int) response.getResult().getData().getDefang().getDefangedCount(), 0);
		assertNotNull(response.getResult().getData().getDefang().getDomainIntelSummary());
		assertEquals((int) response.getResult().getData().getCDR().getFileAttachmentsRemoved(), 0);
		assertEquals((int) response.getResult().getData().getCDR().getInteractiveContentsRemoved(), 0);
		assertFalse(response.getResult().getData().getMaliciousFile());
	}

	@Test
	public void testSanitize_PollResult()
		throws PangeaException, PangeaException, PangeaAPIException, IOException, ConfigException, InterruptedException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg.setQueuedRetryEnabled(false);
		client = new SanitizeClient.Builder(cfg).build();

		SanitizeResponse response;
		AcceptedRequestException exception = null;
		try {
			File file = new File(TESTFILE_PATH);
			response =
				client.sanitize(
					new SanitizeRequest.Builder()
						.file(new SanitizeFile.Builder().cdrProvider("apryse").scanProvider("crowdstrike").build())
						.content(
							new Content.Builder()
								.urlIntel(true)
								.urlIntelProvider("crowdstrike")
								.domainIntel(true)
								.domainIntelProvider("crowdstrike")
								.defang(true)
								.defangThreshold(20)
								.removeInteractive(true)
								.removeAttachments(true)
								.redact(true)
								.build()
						)
						.shareOutput(new ShareOutput.Builder().enabled(true).outputFolder("sdk_test/sanitize/").build())
						.uploadedFileName("uploaded_file")
						.transferMethod(TransferMethod.POST_URL)
						.build(),
					file
				);
			assertTrue(false);
		} catch (AcceptedRequestException e) {
			exception = e;
		}

		int maxRetry = 12;
		for (int retry = 0; retry < maxRetry; retry++) {
			try {
				// Sleep 10 seconds until result is (should) be ready
				Thread.sleep(10 * 1000);

				// Poll result, this could raise another AcceptedRequestException if result is
				// not ready
				response = client.pollResult(exception.getRequestId(), SanitizeResponse.class);
				assertTrue(response.isOk());

				assertTrue(response.isOk());
				assertNull(response.getResult().getDestURL());
				assertNotNull(response.getResult().getDestShareID());
				assertNotNull(response.getResult().getData().getRedact());
				assertTrue(response.getResult().getData().getRedact().getRedactionCount() > 0);
				assertNotNull(response.getResult().getData().getRedact().getSummaryCounts());
				assertNotNull(response.getResult().getData().getDefang());
				assertTrue(response.getResult().getData().getDefang().getExternalURLsCount() > 0);
				assertTrue(response.getResult().getData().getDefang().getExternalDomainsCount() > 0);
				assertEquals((int) response.getResult().getData().getDefang().getDefangedCount(), 0);
				assertNotNull(response.getResult().getData().getDefang().getDomainIntelSummary());
				assertEquals((int) response.getResult().getData().getCDR().getFileAttachmentsRemoved(), 0);
				assertEquals((int) response.getResult().getData().getCDR().getInteractiveContentsRemoved(), 0);
				assertFalse(response.getResult().getData().getMaliciousFile());

				break;
			} catch (PangeaAPIException e) {
				assertTrue(retry < maxRetry - 1);
			}
		}
	}

	@Test
	public void testSanitize_SplitUpload_Post()
		throws PangeaException, PangeaException, PangeaAPIException, IOException, ConfigException, InterruptedException {
		File file = new File(TESTFILE_PATH);
		FileParams fileParams = Utils.getFileUploadParams(file);
		SanitizeRequest request = new SanitizeRequest.Builder()
			.file(new SanitizeFile.Builder().cdrProvider("apryse").scanProvider("crowdstrike").build())
			.content(
				new Content.Builder()
					.urlIntel(true)
					.urlIntelProvider("crowdstrike")
					.domainIntel(true)
					.domainIntelProvider("crowdstrike")
					.defang(true)
					.defangThreshold(20)
					.removeInteractive(true)
					.removeAttachments(true)
					.redact(true)
					.build()
			)
			.shareOutput(new ShareOutput.Builder().enabled(true).outputFolder("sdk_test/sanitize/").build())
			.uploadedFileName("uploaded_file")
			.transferMethod(TransferMethod.POST_URL)
			.sha256(fileParams.getSHA256())
			.crc32c(fileParams.getCRC32C())
			.size(fileParams.getSize())
			.build();

		AcceptedResponse acceptedResponse = client.requestUploadURL(request);

		assertNull(acceptedResponse.getResult().getPutURL());

		String url = acceptedResponse.getResult().getPostURL();
		assertNotNull(url);
		Map<String, Object> details = acceptedResponse.getResult().getPostFormData();
		assertNotNull(details);

		FileData fileData = new FileData(file, "file", details);
		FileUploader fileUploader = new FileUploader.Builder().build();
		fileUploader.uploadFile(url, TransferMethod.POST_URL, fileData);

		SanitizeResponse response;
		int maxRetry = 12;
		for (int retry = 0; retry < maxRetry; retry++) {
			try {
				// Sleep 10 seconds until result is (should) be ready
				Thread.sleep(10 * 1000);

				// Poll result, this could raise another AcceptedRequestException if result is
				// not ready
				response = client.pollResult(acceptedResponse.getRequestId(), SanitizeResponse.class);

				assertTrue(response.isOk());
				assertNull(response.getResult().getDestURL());
				assertNotNull(response.getResult().getDestShareID());
				assertNotNull(response.getResult().getData().getRedact());
				assertTrue(response.getResult().getData().getRedact().getRedactionCount() > 0);
				assertNotNull(response.getResult().getData().getRedact().getSummaryCounts());
				assertNotNull(response.getResult().getData().getDefang());
				assertTrue(response.getResult().getData().getDefang().getExternalURLsCount() > 0);
				assertTrue(response.getResult().getData().getDefang().getExternalDomainsCount() > 0);
				assertEquals((int) response.getResult().getData().getDefang().getDefangedCount(), 0);
				assertNotNull(response.getResult().getData().getDefang().getDomainIntelSummary());
				assertEquals((int) response.getResult().getData().getCDR().getFileAttachmentsRemoved(), 0);
				assertEquals((int) response.getResult().getData().getCDR().getInteractiveContentsRemoved(), 0);
				assertFalse(response.getResult().getData().getMaliciousFile());

				break;
			} catch (PangeaAPIException e) {
				assertTrue(retry < maxRetry - 1);
			}
		}
	}

	@Test
	public void testSanitize_SplitUpload_Put()
		throws PangeaException, PangeaException, PangeaAPIException, IOException, ConfigException, InterruptedException {
		File file = new File(TESTFILE_PATH);
		FileParams fileParams = Utils.getFileUploadParams(file);
		SanitizeRequest request = new SanitizeRequest.Builder()
			.file(new SanitizeFile.Builder().cdrProvider("apryse").scanProvider("crowdstrike").build())
			.content(
				new Content.Builder()
					.urlIntel(true)
					.urlIntelProvider("crowdstrike")
					.domainIntel(true)
					.domainIntelProvider("crowdstrike")
					.defang(true)
					.defangThreshold(20)
					.removeInteractive(true)
					.removeAttachments(true)
					.redact(true)
					.build()
			)
			.shareOutput(new ShareOutput.Builder().enabled(true).outputFolder("sdk_test/sanitize/").build())
			.uploadedFileName("uploaded_file")
			.transferMethod(TransferMethod.PUT_URL)
			.sha256(fileParams.getSHA256())
			.crc32c(fileParams.getCRC32C())
			.size(fileParams.getSize())
			.build();

		AcceptedResponse acceptedResponse = client.requestUploadURL(request);

		assertNull(acceptedResponse.getResult().getPostURL());
		assertEquals(acceptedResponse.getResult().getPostFormData(), new HashMap<>());

		String url = acceptedResponse.getResult().getPutURL();
		assertNotNull(url);

		FileData fileData = new FileData(file, "file");
		FileUploader fileUploader = new FileUploader.Builder().build();
		fileUploader.uploadFile(url, TransferMethod.PUT_URL, fileData);

		SanitizeResponse response;
		int maxRetry = 12;
		for (int retry = 0; retry < maxRetry; retry++) {
			try {
				// Sleep 10 seconds until result is (should) be ready
				Thread.sleep(10 * 1000);

				// Poll result, this could raise another AcceptedRequestException if result is
				// not ready
				response = client.pollResult(acceptedResponse.getRequestId(), SanitizeResponse.class);

				assertTrue(response.isOk());
				assertNull(response.getResult().getDestURL());
				assertNotNull(response.getResult().getDestShareID());
				assertNotNull(response.getResult().getData().getRedact());
				assertTrue(response.getResult().getData().getRedact().getRedactionCount() > 0);
				assertNotNull(response.getResult().getData().getRedact().getSummaryCounts());
				assertNotNull(response.getResult().getData().getDefang());
				assertTrue(response.getResult().getData().getDefang().getExternalURLsCount() > 0);
				assertTrue(response.getResult().getData().getDefang().getExternalDomainsCount() > 0);
				assertEquals((int) response.getResult().getData().getDefang().getDefangedCount(), 0);
				assertNotNull(response.getResult().getData().getDefang().getDomainIntelSummary());
				assertEquals((int) response.getResult().getData().getCDR().getFileAttachmentsRemoved(), 0);
				assertEquals((int) response.getResult().getData().getCDR().getInteractiveContentsRemoved(), 0);
				assertFalse(response.getResult().getData().getMaliciousFile());

				break;
			} catch (PangeaAPIException e) {
				assertTrue(retry < maxRetry - 1);
			}
		}
	}
}
