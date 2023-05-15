package cloud.pangeacyber.pangea.intel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.exceptions.AcceptedRequestException;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.intel.models.FileScanData;
import cloud.pangeacyber.pangea.intel.models.FileScanRequest;
import cloud.pangeacyber.pangea.intel.models.FileScanResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class ITFileScanTest {

	final String EICAR = "X5O!P%@AP[4\\PZX54(P^)7CC)7}$EICAR-STANDARD-ANTIVIRUS-TEST-FILE!$H+H*\n";
	FileScanClient client;
	TestEnvironment environment = TestEnvironment.LIVE;

	@Before
	public void setUp() throws ConfigException {
		client = new FileScanClient(Config.fromIntegrationEnvironment(environment));
		client.setCustomUserAgent("test");
	}

	public File eicar() throws IOException {
		File file = new File("file.exe");
		try (FileOutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(EICAR.getBytes());
		}

		return file;
	}

	@Test
	public void testFileScan_Scan() throws PangeaException, PangeaException, PangeaAPIException, IOException {
		File file = eicar();
		FileScanResponse response = client.scan(
			new FileScanRequest.Builder().setProvider("reversinglabs").build(),
			file
		);
		assertTrue(response.isOk());

		FileScanData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test(expected = AcceptedRequestException.class)
	public void testFileScan_ScanAsync()
		throws PangeaException, PangeaException, PangeaAPIException, IOException, ConfigException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg.setQueuedRetryEnabled(false);
		client = new FileScanClient(cfg);
		client.setCustomUserAgent("test");

		File file = eicar();
		FileScanResponse response = client.scan(
			new FileScanRequest.Builder().setProvider("reversinglabs").build(),
			file
		);
	}

	@Test
	public void testFileScan_ScanAsyncPollResult()
		throws PangeaException, PangeaException, PangeaAPIException, IOException, ConfigException, InterruptedException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg.setQueuedRetryEnabled(false);
		client = new FileScanClient(cfg);
		client.setCustomUserAgent("test");

		FileScanResponse response;
		File file = eicar();
		AcceptedRequestException exception = null;
		try {
			response = client.scan(new FileScanRequest.Builder().setProvider("reversinglabs").build(), file);
			assertTrue(false);
		} catch (AcceptedRequestException e) {
			exception = e;
		}

		// Sleep 60 seconds until result is (should) be ready
		Thread.sleep(60 * 1000);

		// Poll result, this could raise another AcceptedRequestException if result is not ready
		response = client.pollResult(exception.getRequestId(), FileScanResponse.class);
		assertTrue(response.isOk());

		FileScanData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}
}
