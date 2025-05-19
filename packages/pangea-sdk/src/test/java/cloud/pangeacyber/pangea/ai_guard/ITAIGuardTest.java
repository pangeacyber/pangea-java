package cloud.pangeacyber.pangea.ai_guard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Helper;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.ai_guard.requests.TextGuardRequest;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import java.util.List;
import lombok.Value;
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
			TextGuardRequest.<List<Message>>builder().messages(List.of(new Message("user", "what was pangea?"))).build()
		);
		assertTrue(response.isOk());
		final var result = response.getResult();
		assertNotNull(result.getPromptMessages());
	}

	@Value
	private class Message {

		String role;
		String content;
	}
}
