package cloud.pangeacyber.pangea.file_scan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.exceptions.AcceptedRequestException;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.file_scan.models.FileScanData;
import cloud.pangeacyber.pangea.file_scan.requests.FileScanRequest;
import cloud.pangeacyber.pangea.file_scan.responses.FileScanResponse;
import java.io.File;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class ITFileScanTest {

	final String TESTFILE_PATH = "./src/test/java/cloud/pangeacyber/pangea/testdata/testfile.pdf";

	FileScanClient client;
	TestEnvironment environment = TestEnvironment.LIVE;

	@Before
	public void setUp() throws ConfigException {
		client = new FileScanClient.Builder(Config.fromIntegrationEnvironment(environment)).build();
	}

	@Test
	public void testFileScan_Scan() throws PangeaException, PangeaException, PangeaAPIException, IOException {
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

	@Test(expected = AcceptedRequestException.class)
	public void testFileScan_ScanAsync()
		throws PangeaException, PangeaException, PangeaAPIException, IOException, ConfigException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg.setQueuedRetryEnabled(false);
		client = new FileScanClient.Builder(cfg).build();

		File file = new File(TESTFILE_PATH);
		FileScanResponse response = client.scan(new FileScanRequest.Builder().provider("crowdstrike").build(), file);
	}

	@Test
	public void testFileScan_ScanAsyncPollResult()
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

		// Sleep 20 seconds until result is (should) be ready
		Thread.sleep(20 * 1000);

		// Poll result, this could raise another AcceptedRequestException if result is not ready
		response = client.pollResult(exception.getRequestId(), FileScanResponse.class);
		assertTrue(response.isOk());

		FileScanData data = response.getResult().getData();
		assertEquals("benign", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}
}
