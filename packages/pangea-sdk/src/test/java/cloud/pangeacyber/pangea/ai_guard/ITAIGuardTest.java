package cloud.pangeacyber.pangea.ai_guard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Helper;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.ai_guard.requests.TextGuardRequest;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ITAIGuardTest {

	static AIGuardClient client;
	static TestEnvironment environment;

	@BeforeAll
	public static void setUpClass() throws Exception {
		environment = Helper.loadTestEnvironment(AIGuardClient.serviceName, TestEnvironment.LIVE);
	}

	@BeforeEach
	public void setUp() throws Exception {
		client = new AIGuardClient.Builder(Config.fromIntegrationEnvironment(environment)).build();
	}

	@Test
	void testGuardText() throws PangeaException, PangeaAPIException {
		var response = client.guardText(TextGuardRequest.builder().text("hello world").build());
		assertTrue(response.isOk());
		var result = response.getResult();
		assertNotNull(result.getRedactedPrompt());
		assertEquals(0, result.getFindings().getArtifactCount());
		assertEquals(0, result.getFindings().getMaliciousCount());

		response = client.guardText(TextGuardRequest.builder().text("security@pangea.cloud").build());
		assertTrue(response.isOk());
		result = response.getResult();
		assertNotNull(result.getRedactedPrompt());
		assertEquals(1, result.getFindings().getArtifactCount());
		assertEquals(0, result.getFindings().getMaliciousCount());
	}
}
