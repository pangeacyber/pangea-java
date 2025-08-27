package cloud.pangeacyber.pangea.ai_guard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Helper;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.ai_guard.models.Message;
import cloud.pangeacyber.pangea.ai_guard.requests.TextGuardRequest;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import java.util.List;
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
		final var response = client.guardText(
			TextGuardRequest.builder().text("hello world").recipe("pangea_prompt_guard").build()
		);
		assertTrue(response.isOk());
		final var result = response.getResult();
		assertNotNull(result.getPromptText());
		if (result.getDetectors().getMaliciousEntity() != null) {
			assertFalse(result.getDetectors().getMaliciousEntity().isDetected());
		}
		if (result.getDetectors().getPiiEntity() != null) {
			assertFalse(result.getDetectors().getPiiEntity().isDetected());
		}
		assertFalse(result.getDetectors().getPromptInjection().isDetected());
	}

	@Test
	void testGuardTextMessages() throws PangeaException, PangeaAPIException {
		final var response = client.guardText(
			TextGuardRequest
				.builder()
				.messages(List.of(Message.builder().role("user").content("what was pangea?").build()))
				.build()
		);
		assertTrue(response.isOk());
		final var result = response.getResult();
		assertNotNull(result.getPromptMessages());
		assertEquals(1, result.getPromptMessages().size());
	}

	@Test
	void testRelevantContent() throws PangeaException, PangeaAPIException {
		final var response = client.guardText(
			TextGuardRequest
				.builder()
				.messages(
					List.of(
						Message.builder().role("system").content("what was pangea?").build(),
						Message.builder().role("user").content("what was pangea?").build(),
						Message.builder().role("context").content("what was pangea?").build(),
						Message.builder().role("assistant").content("what was pangea?").build(),
						Message.builder().role("tool").content("what was pangea?").build(),
						Message.builder().role("context").content("what was pangea?").build()
					)
				)
				.build(),
			true
		);
		assertTrue(response.isOk());
		final var result = response.getResult();
		assertNotNull(result.getPromptMessages());
		assertEquals(6, result.getPromptMessages().size());
	}
}
