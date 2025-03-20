package cloud.pangeacyber.pangea.intel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Helper;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.UnauthorizedException;
import cloud.pangeacyber.pangea.exceptions.ValidationException;
import cloud.pangeacyber.pangea.intel.models.FileReputationBulkData;
import cloud.pangeacyber.pangea.intel.models.IntelReputationData;
import cloud.pangeacyber.pangea.intel.requests.FileHashReputationBulkRequest;
import cloud.pangeacyber.pangea.intel.requests.FileHashReputationRequest;
import cloud.pangeacyber.pangea.intel.responses.FileReputationBulkResponse;
import cloud.pangeacyber.pangea.intel.responses.FileReputationResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ITFileIntelTest {

	FileIntelClient client;
	static TestEnvironment environment;

	@BeforeAll
	public static void setUpClass() throws Exception {
		environment = Helper.loadTestEnvironment("file-intel", TestEnvironment.LIVE);
	}

	@BeforeEach
	public void setUp() throws Exception {
		client = new FileIntelClient.Builder(Config.fromIntegrationEnvironment(environment)).build();
	}

	@Test
	public void testFileReputationMalicious_1() throws PangeaException, PangeaAPIException {
		// Provider, no verbose and no raw data
		FileReputationResponse response = client.reputation(
			new FileHashReputationRequest.Builder(
				"142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e",
				"sha256"
			)
				.provider("reversinglabs")
				.verbose(false)
				.raw(false)
				.build()
		);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertEquals("Trojan", data.getCategory()[0]);
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testFileReputationMalicious_2() throws PangeaException, PangeaAPIException {
		// Provider, verbose, raw data
		FileReputationResponse response = client.reputation(
			new FileHashReputationRequest.Builder(
				"142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e",
				"sha256"
			)
				.provider("reversinglabs")
				.verbose(true)
				.raw(true)
				.build()
		);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertEquals("Trojan", data.getCategory()[0]);
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testFileReputationMalicious_3() throws PangeaException, PangeaAPIException {
		// Provider, no verbose by default, no raw data by default
		FileReputationResponse response = client.reputation(
			new FileHashReputationRequest.Builder(
				"142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e",
				"sha256"
			)
				.provider("reversinglabs")
				.build()
		);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertEquals("Trojan", data.getCategory()[0]);
		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testFileReputationMalicious_4() throws PangeaException, PangeaAPIException {
		// Default provider, no verbose by default, no raw data by default
		FileReputationResponse response = client.reputation(
			new FileHashReputationRequest.Builder(
				"142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e",
				"sha256"
			)
				.build()
		);

		// NOTE: because we're using the default provider in this test,
		// the resulting verdict can change based on the provider set as default
		// so only assert the response is successful
		assertTrue(response.isOk());

		assertNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testFileReputationMalicious_5() throws PangeaException, PangeaAPIException {
		// Provider, verbose, no raw data
		FileReputationResponse response = client.reputation(
			new FileHashReputationRequest.Builder(
				"142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e",
				"sha256"
			)
				.provider("reversinglabs")
				.verbose(true)
				.raw(false)
				.build()
		);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertEquals("Trojan", data.getCategory()[0]);
		assertNotNull(response.getResult().getParameters());
		assertNull(response.getResult().getRawData());
	}

	@Test
	public void testFileReputationMalicious_6() throws PangeaException, PangeaAPIException {
		// Provider, no verbose, raw data
		FileReputationResponse response = client.reputation(
			new FileHashReputationRequest.Builder(
				"142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e",
				"sha256"
			)
				.provider("reversinglabs")
				.verbose(false)
				.raw(true)
				.build()
		);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("malicious", data.getVerdict());
		assertEquals("Trojan", data.getCategory()[0]);
		assertNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testFileReputationMaliciousBulk() throws PangeaException, PangeaAPIException {
		// Provider, no verbose, raw data
		String[] hashes = {
			"142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e",
			"179e2b8a4162372cd9344b81793cbf74a9513a002eda3324e6331243f3137a63",
		};

		FileReputationBulkResponse response = client.reputationBulk(
			new FileHashReputationBulkRequest.Builder(hashes, "sha256")
				.provider("reversinglabs")
				.verbose(true)
				.raw(true)
				.build()
		);
		assertTrue(response.isOk());

		FileReputationBulkData data = response.getResult().getData();
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
		assertEquals(2, data.size());
	}

	@Test
	public void testFileReputationNotProvided() throws PangeaException, PangeaAPIException {
		FileReputationResponse response = client.reputation(
			new FileHashReputationRequest.Builder(
				"178e2b8a4162372cd9344b81793cbf74a9513a002eda3324e6331243f3137a63",
				"sha256"
			)
				.provider("reversinglabs")
				.verbose(true)
				.raw(true)
				.build()
		);
		assertTrue(response.isOk());

		IntelReputationData data = response.getResult().getData();
		assertEquals("unknown", data.getVerdict());
		assertNotNull(data.getCategory());
		assertNotNull(response.getResult().getParameters());
		assertNotNull(response.getResult().getRawData());
	}

	@Test
	public void testEmptyProvider() throws PangeaException, PangeaAPIException {
		assertThrows(
			ValidationException.class,
			() ->
				client.reputation(
					new FileHashReputationRequest.Builder(
						"322ccbd42b7e4fd3a9d0167ca2fa9f6483d9691364c431625f1df542706",
						"sha256"
					)
						.provider("")
						.verbose(true)
						.raw(true)
						.build()
				)
		);
	}

	@Test
	public void testNotValidProvider() throws PangeaException, PangeaAPIException {
		assertThrows(
			ValidationException.class,
			() ->
				client.reputation(
					new FileHashReputationRequest.Builder(
						"322ccbd42b7e4fd3a9d0167ca2fa9f6483d9691364c431625f1df542706",
						"sha256"
					)
						.provider("notvalid")
						.verbose(true)
						.raw(true)
						.build()
				)
		);
	}

	@Test
	public void testEmptyHash() throws PangeaException, PangeaAPIException {
		assertThrows(
			ValidationException.class,
			() ->
				client.reputation(
					new FileHashReputationRequest.Builder("", "sha256")
						.provider("reversinglabs")
						.verbose(true)
						.raw(true)
						.build()
				)
		);
	}

	@Test
	public void testNotValidHash() throws PangeaException, PangeaAPIException {
		assertThrows(
			ValidationException.class,
			() ->
				client.reputation(
					new FileHashReputationRequest.Builder("notavalidhash", "sha256")
						.provider("reversinglabs")
						.verbose(true)
						.raw(true)
						.build()
				)
		);
	}

	@Test
	public void testEmptyHashType() throws PangeaException, PangeaAPIException {
		assertThrows(
			ValidationException.class,
			() ->
				client.reputation(
					new FileHashReputationRequest.Builder(
						"322ccbd42b7e4fd3a9d0167ca2fa9f6483d9691364c431625f1df542706",
						"sha256"
					)
						.provider("")
						.verbose(true)
						.raw(true)
						.build()
				)
		);
	}

	@Test
	public void testNotValidHashType() throws PangeaException, PangeaAPIException {
		assertThrows(
			ValidationException.class,
			() ->
				client.reputation(
					new FileHashReputationRequest.Builder(
						"322ccbd42b7e4fd3a9d0167ca2fa9f6483d9691364c431625f1df542706",
						"notvalid"
					)
						.provider("reversinglabs")
						.verbose(true)
						.raw(true)
						.build()
				)
		);
	}

	@Test
	public void testUnauthorized() throws PangeaException, PangeaAPIException, ConfigException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg = Config.builder().token("notarealtoken").baseURLTemplate(cfg.getBaseURLTemplate()).build();
		FileIntelClient fakeClient = new FileIntelClient.Builder(cfg).build();
		assertThrows(
			UnauthorizedException.class,
			() ->
				fakeClient.reputation(
					new FileHashReputationRequest.Builder(
						"322ccbd42b7e4fd3a9d0167ca2fa9f6483d9691364c431625f1df542706",
						"sha256"
					)
						.provider("reversinglabs")
						.verbose(true)
						.raw(true)
						.build()
				)
		);
	}

	@Test
	public void testSHA256fromFilepath() throws PangeaException {
		String hash = FileIntelClient.calculateSHA256fromFile("./README.md");
		assertNotNull(hash);
		assertNotEquals(hash, "");
	}

	@Test
	public void testSHA256fromFilepathNoFile() throws PangeaException {
		assertThrows(
			PangeaException.class,
			() -> FileIntelClient.calculateSHA256fromFile("./not/a/real/path/file.exe")
		);
	}
}
