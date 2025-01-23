package cloud.pangeacyber.pangea.sanitize;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.abort;

import cloud.pangeacyber.pangea.AttachedFile;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.FileData;
import cloud.pangeacyber.pangea.FileUploader;
import cloud.pangeacyber.pangea.Helper;
import cloud.pangeacyber.pangea.SkipAccepted;
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
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodName.class)
public class ITSanitizeTest {

	final String TESTFILE_PATH = "./src/test/java/cloud/pangeacyber/pangea/testdata/test-sanitize.txt";

	SanitizeClient client;
	static TestEnvironment environment;

	@BeforeAll
	public static void setUpClass() throws Exception {
		environment = Helper.loadTestEnvironment("sanitize", TestEnvironment.LIVE);
	}

	@BeforeEach
	public void setUp() throws Exception {
		client = new SanitizeClient.Builder(Config.fromIntegrationEnvironment(environment)).build();
	}

	@Test
	@SkipAccepted
	void testSanitizeAndShare() throws PangeaException, PangeaAPIException {
		File file = new File(TESTFILE_PATH);
		SanitizeResponse response = client.sanitize(
			new SanitizeRequest.Builder()
				.file(new SanitizeFile.Builder().scanProvider("crowdstrike").build())
				.content(
					new Content.Builder()
						.urlIntel(true)
						.urlIntelProvider("crowdstrike")
						.domainIntel(true)
						.domainIntelProvider("crowdstrike")
						.defang(true)
						.defangThreshold(20)
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
		assertEquals(0, (int) response.getResult().getData().getDefang().getDefangedCount());
		assertNotNull(response.getResult().getData().getDefang().getDomainIntelSummary());
		assertFalse(response.getResult().getData().getMaliciousFile());
	}

	@Test
	@SkipAccepted
	void testSanitizeNoShare() throws PangeaException, PangeaAPIException {
		File file = new File(TESTFILE_PATH);
		SanitizeResponse response = client.sanitize(
			new SanitizeRequest.Builder()
				.file(new SanitizeFile.Builder().scanProvider("crowdstrike").build())
				.content(
					new Content.Builder()
						.urlIntel(true)
						.urlIntelProvider("crowdstrike")
						.domainIntel(true)
						.domainIntelProvider("crowdstrike")
						.defang(true)
						.defangThreshold(20)
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
		assertEquals(0, (int) response.getResult().getData().getDefang().getDefangedCount());
		assertNotNull(response.getResult().getData().getDefang().getDomainIntelSummary());
		assertFalse(response.getResult().getData().getMaliciousFile());

		AttachedFile attachedFile = client.downloadFile(response.getResult().getDestURL());
		attachedFile.save(Path.of("./", attachedFile.getFilename()));
	}

	@Test
	@SkipAccepted
	void testSanitizeAllDefaults() throws PangeaException, PangeaAPIException {
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
		assertEquals(0, (int) response.getResult().getData().getDefang().getDefangedCount());
		assertNotNull(response.getResult().getData().getDefang().getDomainIntelSummary());
		assertFalse(response.getResult().getData().getMaliciousFile());
	}

	@Test
	@SkipAccepted
	void testSanitizeMultipart() throws PangeaException, PangeaAPIException {
		File file = new File(TESTFILE_PATH);
		SanitizeResponse response = client.sanitize(
			new SanitizeRequest.Builder()
				.file(new SanitizeFile.Builder().scanProvider("crowdstrike").build())
				.content(
					new Content.Builder()
						.urlIntel(true)
						.urlIntelProvider("crowdstrike")
						.domainIntel(true)
						.domainIntelProvider("crowdstrike")
						.defang(true)
						.defangThreshold(20)
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
		assertEquals(0, (int) response.getResult().getData().getDefang().getDefangedCount());
		assertNotNull(response.getResult().getData().getDefang().getDomainIntelSummary());
		assertFalse(response.getResult().getData().getMaliciousFile());
	}

	@Test
	void testSanitize_PollResult() throws PangeaException, PangeaAPIException, ConfigException, InterruptedException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg.setQueuedRetryEnabled(false);
		client = new SanitizeClient.Builder(cfg).build();

		SanitizeResponse response;
		AcceptedRequestException exception = null;
		try {
			File file = new File(TESTFILE_PATH);
			client.sanitize(
				new SanitizeRequest.Builder()
					.file(new SanitizeFile.Builder().scanProvider("crowdstrike").build())
					.content(
						new Content.Builder()
							.urlIntel(true)
							.urlIntelProvider("crowdstrike")
							.domainIntel(true)
							.domainIntelProvider("crowdstrike")
							.defang(true)
							.defangThreshold(20)
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
				assertEquals(0, (int) response.getResult().getData().getDefang().getDefangedCount());
				assertNotNull(response.getResult().getData().getDefang().getDomainIntelSummary());
				assertFalse(response.getResult().getData().getMaliciousFile());

				return;
			} catch (AcceptedRequestException e) {
				// No-op.
			}
		}

		abort(String.format("Result of request '%s' took too long.", exception.getRequestId()));
	}

	@Test
	void testSanitize_SplitUpload_Post() throws PangeaException, PangeaAPIException, InterruptedException {
		File file = new File(TESTFILE_PATH);
		FileParams fileParams = Utils.getFileUploadParams(file);
		SanitizeRequest request = new SanitizeRequest.Builder()
			.file(new SanitizeFile.Builder().scanProvider("crowdstrike").build())
			.content(
				new Content.Builder()
					.urlIntel(true)
					.urlIntelProvider("crowdstrike")
					.domainIntel(true)
					.domainIntelProvider("crowdstrike")
					.defang(true)
					.defangThreshold(20)
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
				assertEquals(0, (int) response.getResult().getData().getDefang().getDefangedCount());
				assertNotNull(response.getResult().getData().getDefang().getDomainIntelSummary());
				assertFalse(response.getResult().getData().getMaliciousFile());

				return;
			} catch (AcceptedRequestException e) {
				// No-op.
			}
		}

		abort(String.format("Result of request '%s' took too long.", acceptedResponse.getRequestId()));
	}

	@Test
	void testSanitize_SplitUpload_Put() throws PangeaException, PangeaAPIException, InterruptedException {
		File file = new File(TESTFILE_PATH);
		FileParams fileParams = Utils.getFileUploadParams(file);
		SanitizeRequest request = new SanitizeRequest.Builder()
			.file(new SanitizeFile.Builder().scanProvider("crowdstrike").build())
			.content(
				new Content.Builder()
					.urlIntel(true)
					.urlIntelProvider("crowdstrike")
					.domainIntel(true)
					.domainIntelProvider("crowdstrike")
					.defang(true)
					.defangThreshold(20)
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
				assertEquals(0, (int) response.getResult().getData().getDefang().getDefangedCount());
				assertNotNull(response.getResult().getData().getDefang().getDomainIntelSummary());
				assertFalse(response.getResult().getData().getMaliciousFile());

				return;
			} catch (AcceptedRequestException e) {
				// No-op.
			}
		}

		abort(String.format("Result of request '%s' took too long.", acceptedResponse.getRequestId()));
	}
}
