package cloud.pangeacyber.pangea.file_scan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.FileData;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.TransferMethod;
import cloud.pangeacyber.pangea.Utils;
import cloud.pangeacyber.pangea.exceptions.AcceptedRequestException;
import cloud.pangeacyber.pangea.exceptions.AcceptedResponse;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.file_scan.models.FileParams;
import cloud.pangeacyber.pangea.file_scan.models.FileScanData;
import cloud.pangeacyber.pangea.file_scan.requests.FileScanRequest;
import cloud.pangeacyber.pangea.file_scan.requests.FileScanUploadURLRequest;
import cloud.pangeacyber.pangea.file_scan.responses.FileScanResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ITFileScanTest {

	final String TESTFILE_PATH = "./src/test/java/cloud/pangeacyber/pangea/testdata/testfile.pdf";

	FileScanClient client;
	TestEnvironment environment = TestEnvironment.LIVE;

	@Before
	public void setUp() throws ConfigException {
		client = new FileScanClient.Builder(Config.fromIntegrationEnvironment(environment)).build();
	}

	@Test
	public void testFileScan_Scan_crowdstrike()
		throws PangeaException, PangeaException, PangeaAPIException, IOException {
		File file = new File(TESTFILE_PATH);
		FileScanResponse response = client.scan(
			new FileScanRequest.Builder().provider("crowdstrike").raw(true).verbose(false).build(),
			file
		);
		assertTrue(response.isOk());

		FileScanData data = response.getResult().getData();
		assertEquals("benign", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testFileScan_Scan_multipart() throws PangeaException, PangeaException, PangeaAPIException, IOException {
		File file = new File(TESTFILE_PATH);
		FileScanResponse response = client.scan(
			new FileScanRequest.Builder().transferMethod(TransferMethod.MULTIPART).raw(true).verbose(false).build(),
			file
		);
		assertTrue(response.isOk());

		FileScanData data = response.getResult().getData();
		assertEquals("benign", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test(expected = AcceptedRequestException.class)
	public void testFileScan_ScanAsync_crowdstrike()
		throws PangeaException, PangeaException, PangeaAPIException, IOException, ConfigException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg.setQueuedRetryEnabled(false);
		client = new FileScanClient.Builder(cfg).build();

		File file = new File(TESTFILE_PATH);
		FileScanResponse response = client.scan(new FileScanRequest.Builder().provider("crowdstrike").build(), file);
	}

	@Test
	public void testFileScan_ScanAsyncPollResult_crowdstrike()
		throws PangeaException, PangeaException, PangeaAPIException, IOException, ConfigException, InterruptedException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg.setQueuedRetryEnabled(false);
		client = new FileScanClient.Builder(cfg).build();

		FileScanResponse response;
		AcceptedRequestException exception = null;
		try {
			File file = new File(TESTFILE_PATH);
			response =
				client.scan(
					new FileScanRequest.Builder().provider("crowdstrike").raw(true).verbose(false).build(),
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

				// Poll result, this could raise another AcceptedRequestException if result is not ready
				response = client.pollResult(exception.getRequestId(), FileScanResponse.class);
				assertTrue(response.isOk());

				FileScanData data = response.getResult().getData();
				assertEquals("benign", data.getVerdict());
				assertNull(response.getResult().getParameters());
				assertNotNull(response.getResult().getRawData());
				break;
			} catch (PangeaAPIException e) {
				assertTrue(retry < maxRetry - 1);
			}
		}
	}

	@Test
	public void testFileScan_Scan_reversinglabs()
		throws PangeaException, PangeaException, PangeaAPIException, IOException {
		File file = new File(TESTFILE_PATH);
		FileScanResponse response = client.scan(
			new FileScanRequest.Builder().provider("reversinglabs").raw(true).verbose(false).build(),
			file
		);
		assertTrue(response.isOk());

		FileScanData data = response.getResult().getData();
		assertEquals("benign", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test(expected = AcceptedRequestException.class)
	public void testFileScan_ScanAsync_reversinglabs()
		throws PangeaException, PangeaException, PangeaAPIException, IOException, ConfigException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg.setQueuedRetryEnabled(false);
		client = new FileScanClient.Builder(cfg).build();

		File file = new File(TESTFILE_PATH);
		FileScanResponse response = client.scan(new FileScanRequest.Builder().provider("reversinglabs").build(), file);
	}

	@Test
	public void testFileScan_ScanAsyncPollResult_reversinglabs()
		throws PangeaException, PangeaException, PangeaAPIException, IOException, ConfigException, InterruptedException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg.setQueuedRetryEnabled(false);
		client = new FileScanClient.Builder(cfg).build();

		FileScanResponse response;
		AcceptedRequestException exception = null;
		try {
			File file = new File(TESTFILE_PATH);
			response =
				client.scan(
					new FileScanRequest.Builder().provider("reversinglabs").raw(true).verbose(false).build(),
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

				// Poll result, this could raise another AcceptedRequestException if result is not ready
				response = client.pollResult(exception.getRequestId(), FileScanResponse.class);
				assertTrue(response.isOk());

				FileScanData data = response.getResult().getData();
				assertEquals("benign", data.getVerdict());
				assertNull(response.getResult().getParameters());
				assertNotNull(response.getResult().getRawData());
				break;
			} catch (PangeaAPIException e) {
				assertTrue(retry < maxRetry - 1);
			}
		}
	}

	@Test
	public void testFileScan_SplitUpload_Post()
		throws PangeaException, PangeaException, PangeaAPIException, IOException, ConfigException, InterruptedException {
		File file = new File(TESTFILE_PATH);
		FileParams fileParams = Utils.getFileUploadParams(file);
		FileScanUploadURLRequest request = new FileScanUploadURLRequest.Builder()
			.provider("reversinglabs")
			.raw(true)
			.verbose(false)
			.fileParams(fileParams)
			.build();

		AcceptedResponse acceptedResponse = client.requestUploadURL(request);

		String url = acceptedResponse.getResult().getPostURL();
		Map<String, Object> details = acceptedResponse.getResult().getPostFormData();

		FileData fileData = new FileData(file, "file", details);

		FileUploader fileUploader = new FileUploader.Builder().build();
		fileUploader.uploadFile(url, TransferMethod.POST_URL, fileData);

		FileScanResponse response;
		int maxRetry = 12;
		for (int retry = 0; retry < maxRetry; retry++) {
			try {
				// Sleep 10 seconds until result is (should) be ready
				Thread.sleep(10 * 1000);

				// Poll result, this could raise another AcceptedRequestException if result is not ready
				response = client.pollResult(acceptedResponse.getRequestId(), FileScanResponse.class);
				assertTrue(response.isOk());

				FileScanData data = response.getResult().getData();
				assertEquals("benign", data.getVerdict());
				assertNull(response.getResult().getParameters());
				assertNotNull(response.getResult().getRawData());
				break;
			} catch (PangeaAPIException e) {
				assertTrue(retry < maxRetry - 1);
			}
		}
	}

	@Test
	public void testFileScan_SplitUpload_Put()
		throws PangeaException, PangeaException, PangeaAPIException, IOException, ConfigException, InterruptedException {
		File file = new File(TESTFILE_PATH);
		FileScanUploadURLRequest request = new FileScanUploadURLRequest.Builder()
			.provider("reversinglabs")
			.raw(true)
			.verbose(false)
			.transferMethod(TransferMethod.PUT_URL)
			.build();

		AcceptedResponse acceptedResponse = client.requestUploadURL(request);

		String url = acceptedResponse.getResult().getPutURL();

		FileData fileData = new FileData(file, "file");

		FileUploader fileUploader = new FileUploader.Builder().build();
		fileUploader.uploadFile(url, TransferMethod.PUT_URL, fileData);

		FileScanResponse response;
		int maxRetry = 12;
		for (int retry = 0; retry < maxRetry; retry++) {
			try {
				// Sleep 10 seconds until result is (should) be ready
				Thread.sleep(10 * 1000);

				// Poll result, this could raise another AcceptedRequestException if result is not ready
				response = client.pollResult(acceptedResponse.getRequestId(), FileScanResponse.class);
				assertTrue(response.isOk());

				FileScanData data = response.getResult().getData();
				assertEquals("benign", data.getVerdict());
				assertNull(response.getResult().getParameters());
				assertNotNull(response.getResult().getRawData());
				break;
			} catch (PangeaAPIException e) {
				assertTrue(retry < maxRetry - 1);
			}
		}
	}
}
