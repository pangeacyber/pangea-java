package cloud.pangeacyber.pangea.redact;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import cloud.pangeacyber.pangea.redact.requests.RedactStructuredRequest;
import cloud.pangeacyber.pangea.redact.requests.RedactTextRequest;
import cloud.pangeacyber.pangea.redact.responses.RedactStructuredResponse;
import cloud.pangeacyber.pangea.redact.responses.RedactTextResponse;
import cloud.pangeacyber.pangea.redact.results.RedactStructuredResult;
import cloud.pangeacyber.pangea.redact.results.RedactTextResult;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ITRedactTest {

	RedactClient client;
	static TestEnvironment environment;

	@BeforeAll
	public static void setUpClass() throws Exception {
		environment = Helper.loadTestEnvironment("redact", TestEnvironment.LIVE);
	}

	@BeforeEach
	public void setUp() throws Exception {
		client = new RedactClient.Builder(Config.fromIntegrationEnvironment(environment)).build();
	}

	@Test
	public void testRedactRequest_1() throws PangeaException, PangeaAPIException {
		RedactTextResponse response = client.redactText(
			new RedactTextRequest.Builder("Jenny Jenny... 415-867-5309").build()
		);
		assertTrue(response.isOk());

		RedactTextResult result = response.getResult();
		assertEquals("<PERSON>... <PHONE_NUMBER>", result.getRedactedText());
		assertEquals(2, result.getCount());
		assertNull(result.getReport());
	}

	@Test
	public void testRedactRequest_2() throws PangeaException, PangeaAPIException {
		RedactTextResponse response = client.redactText(
			new RedactTextRequest.Builder("Jenny Jenny... 415-867-5309").setDebug(true).build()
		);
		assertTrue(response.isOk());

		RedactTextResult result = response.getResult();
		assertEquals("<PERSON>... <PHONE_NUMBER>", result.getRedactedText());
		assertNotNull(result.getReport());
	}

	@Test
	public void testRedactRequest_3() throws PangeaException, PangeaAPIException {
		RedactTextResponse response = client.redactText(
			new RedactTextRequest.Builder("Jenny Jenny... 415-867-5309").setDebug(false).build()
		);
		assertTrue(response.isOk());

		RedactTextResult result = response.getResult();
		assertEquals("<PERSON>... <PHONE_NUMBER>", result.getRedactedText());
		assertNull(result.getReport());
	}

	@Test
	public void testStructuredRequest_1() throws PangeaException, PangeaAPIException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");
		data.put("IP", "Its ip is 127.0.0.1");

		RedactStructuredResponse response = client.redactStructured(new RedactStructuredRequest.Builder(data).build());
		assertTrue(response.isOk());

		RedactStructuredResult result = response.getResult();

		Map<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("Name", "<PERSON>");
		expected.put("Phone", "This is its number: <PHONE_NUMBER>");
		expected.put("IP", "Its ip is 127.0.0.1");
		assertEquals(2, result.getCount());

		assertEquals(expected, result.getRedactedData());
		assertNull(result.getReport());
	}

	@Test
	public void testStructuredRequest_2() throws PangeaException, PangeaAPIException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");
		data.put("IP", "Its ip is 127.0.0.1");

		RedactStructuredResponse response = client.redactStructured(
			new RedactStructuredRequest.Builder(data).setFormat("json").build()
		);
		assertTrue(response.isOk());

		RedactStructuredResult result = response.getResult();

		Map<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("Name", "<PERSON>");
		expected.put("Phone", "This is its number: <PHONE_NUMBER>");
		expected.put("IP", "Its ip is 127.0.0.1");

		assertEquals(expected, result.getRedactedData());
		assertNull(result.getReport());
	}

	@Test
	public void testStructuredRequest_3() throws PangeaException, PangeaAPIException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");
		data.put("IP", "Its ip is 127.0.0.1");

		RedactStructuredResponse response = client.redactStructured(
			new RedactStructuredRequest.Builder(data).setDebug(true).build()
		);
		assertTrue(response.isOk());

		RedactStructuredResult result = response.getResult();

		Map<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("Name", "<PERSON>");
		expected.put("Phone", "This is its number: <PHONE_NUMBER>");
		expected.put("IP", "Its ip is 127.0.0.1");

		assertEquals(expected, result.getRedactedData());
		assertNotNull(result.getReport());
	}

	@Test
	public void testStructuredRequest_4() throws PangeaException, PangeaAPIException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");
		data.put("IP", "Its ip is 127.0.0.1");

		RedactStructuredResponse response = client.redactStructured(
			new RedactStructuredRequest.Builder(data).setDebug(false).build()
		);
		assertTrue(response.isOk());

		RedactStructuredResult result = response.getResult();

		Map<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("Name", "<PERSON>");
		expected.put("Phone", "This is its number: <PHONE_NUMBER>");
		expected.put("IP", "Its ip is 127.0.0.1");

		assertEquals(expected, result.getRedactedData());
		assertNull(result.getReport());
	}

	@Test
	public void testStructuredRequest_5() throws PangeaException, PangeaAPIException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");
		data.put("IP", "Its ip is 127.0.0.1");

		RedactStructuredResponse response = client.redactStructured(
			new RedactStructuredRequest.Builder(data).setFormat("json").setDebug(true).build()
		);
		assertTrue(response.isOk());

		RedactStructuredResult result = response.getResult();

		Map<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("Name", "<PERSON>");
		expected.put("Phone", "This is its number: <PHONE_NUMBER>");
		expected.put("IP", "Its ip is 127.0.0.1");

		assertEquals(expected, result.getRedactedData());
		assertNotNull(result.getReport());
	}

	@Test
	public void testStructuredRequest_6() throws PangeaException, PangeaAPIException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");
		data.put("IP", "Its ip is 127.0.0.1");

		RedactStructuredResponse response = client.redactStructured(
			new RedactStructuredRequest.Builder(data).setDebug(true).setJsonp(new String[] { "Phone" }).build()
		);
		assertTrue(response.isOk());

		RedactStructuredResult result = response.getResult();

		Map<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("Name", "Jenny Jenny");
		expected.put("Phone", "This is its number: <PHONE_NUMBER>");
		expected.put("IP", "Its ip is 127.0.0.1");

		assertEquals(expected, result.getRedactedData());
		assertNotNull(result.getReport());
	}

	@Test
	public void testStructuredRequest_7() throws PangeaException, PangeaAPIException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");
		data.put("IP", "Its ip is 127.0.0.1");

		RedactStructuredResponse response = client.redactStructured(
			new RedactStructuredRequest.Builder(data).setRules(new String[] { "IP_ADDRESS" }).build()
		);
		assertTrue(response.isOk());

		RedactStructuredResult result = response.getResult();

		Map<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("Name", "<PERSON>");
		expected.put("Phone", "This is its number: <PHONE_NUMBER>");
		expected.put("IP", "Its ip is <IP_ADDRESS>");

		assertEquals(expected, result.getRedactedData());
		assertNull(result.getReport());
	}

	@Test
	public void testStructuredRequest_8() throws PangeaException, PangeaAPIException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");
		data.put("IP", "Its ip is 127.0.0.1");

		RedactStructuredResponse response = client.redactStructured(
			new RedactStructuredRequest.Builder(data)
				.setDebug(true)
				.setJsonp(new String[] { "Phone", "IP" })
				.setRules(new String[] { "IP_ADDRESS" })
				.build()
		);
		assertTrue(response.isOk());

		RedactStructuredResult result = response.getResult();

		Map<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("Name", "Jenny Jenny");
		expected.put("Phone", "This is its number: <PHONE_NUMBER>");
		expected.put("IP", "Its ip is <IP_ADDRESS>");

		assertEquals(expected, result.getRedactedData());
		assertNotNull(result.getReport());
	}

	@Test
	public void testRedactTextUnauthorized() throws PangeaException, PangeaAPIException, ConfigException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg = new Config.Builder("notarealtoken", cfg.getDomain()).build();
		RedactClient fakeClient = new RedactClient.Builder(cfg).build();
		assertThrows(
			UnauthorizedException.class,
			() -> fakeClient.redactText(new RedactTextRequest.Builder("My name is Jenny Jenny").build())
		);
	}

	@Test
	public void testRedactStructuredUnauthorized() throws PangeaException, PangeaAPIException, ConfigException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg = new Config.Builder("notarealtoken", cfg.getDomain()).build();
		RedactClient fakeClient = new RedactClient.Builder(cfg).build();
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");
		assertThrows(
			UnauthorizedException.class,
			() -> fakeClient.redactStructured(new RedactStructuredRequest.Builder(data).build())
		);
	}

	@Test
	public void testMultiConfig1Redact() throws PangeaException, PangeaAPIException, ConfigException {
		Config cfg = new Config.Builder(Config.getMultiConfigTestToken(environment), Config.getTestDomain(environment))
			.build();

		String configID = Config.getConfigID(environment, "redact", 1);

		RedactClient clientMultiConfig = new RedactClient.Builder(cfg).withConfigID(configID).build();

		RedactTextResponse response = clientMultiConfig.redactText(
			new RedactTextRequest.Builder("Jenny Jenny... 415-867-5309").build()
		);
		assertTrue(response.isOk());

		RedactTextResult result = response.getResult();
		assertEquals("<PERSON>... <PHONE_NUMBER>", result.getRedactedText());
		assertEquals(2, result.getCount());
		assertNull(result.getReport());
	}

	@Test
	public void testMultiConfig2Redact() throws PangeaException, PangeaAPIException, ConfigException {
		Config cfg = new Config.Builder(Config.getMultiConfigTestToken(environment), Config.getTestDomain(environment))
			.build();

		String configID = Config.getConfigID(environment, "redact", 2);

		RedactClient clientMultiConfig = new RedactClient.Builder(cfg).withConfigID(configID).build();

		RedactTextResponse response = clientMultiConfig.redactText(
			new RedactTextRequest.Builder("Jenny Jenny... 415-867-5309").build()
		);
		assertTrue(response.isOk());

		RedactTextResult result = response.getResult();
		assertEquals("<PERSON>... <PHONE_NUMBER>", result.getRedactedText());
		assertEquals(2, result.getCount());
		assertNull(result.getReport());
	}

	@Test
	public void testMultiConfigWithoutConfigID() throws PangeaException, PangeaAPIException, ConfigException {
		Config cfg = new Config.Builder(Config.getMultiConfigTestToken(environment), Config.getTestDomain(environment))
			.build();

		RedactClient clientMultiConfig = new RedactClient.Builder(cfg).build();

		assertThrows(
			PangeaAPIException.class,
			() -> clientMultiConfig.redactText(new RedactTextRequest.Builder("Jenny Jenny... 415-867-5309").build())
		);
	}
}
