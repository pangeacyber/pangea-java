package cloud.pangeacyber.pangea.redact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cloud.pangeacyber.pangea.Config;
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
import org.junit.Before;
import org.junit.Test;

public class ITRedactTest {

	RedactClient client;
	TestEnvironment environment = TestEnvironment.LIVE;

	@Before
	public void setUp() throws ConfigException {
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

		RedactStructuredResponse response = client.redactStructured(new RedactStructuredRequest.Builder(data).build());
		assertTrue(response.isOk());

		RedactStructuredResult result = response.getResult();

		Map<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("Name", "<PERSON>");
		expected.put("Phone", "This is its number: <PHONE_NUMBER>");
		assertEquals(2, result.getCount());

		assertEquals(expected, result.getRedactedData());
		assertNull(result.getReport());
	}

	@Test
	public void testStructuredRequest_2() throws PangeaException, PangeaAPIException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");

		RedactStructuredResponse response = client.redactStructured(
			new RedactStructuredRequest.Builder(data).setFormat("json").build()
		);
		assertTrue(response.isOk());

		RedactStructuredResult result = response.getResult();

		Map<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("Name", "<PERSON>");
		expected.put("Phone", "This is its number: <PHONE_NUMBER>");

		assertEquals(expected, result.getRedactedData());
		assertNull(result.getReport());
	}

	@Test
	public void testStructuredRequest_3() throws PangeaException, PangeaAPIException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");

		RedactStructuredResponse response = client.redactStructured(
			new RedactStructuredRequest.Builder(data).setDebug(true).build()
		);
		assertTrue(response.isOk());

		RedactStructuredResult result = response.getResult();

		Map<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("Name", "<PERSON>");
		expected.put("Phone", "This is its number: <PHONE_NUMBER>");

		assertEquals(expected, result.getRedactedData());
		assertNotNull(result.getReport());
	}

	@Test
	public void testStructuredRequest_4() throws PangeaException, PangeaAPIException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");

		RedactStructuredResponse response = client.redactStructured(
			new RedactStructuredRequest.Builder(data).setDebug(false).build()
		);
		assertTrue(response.isOk());

		RedactStructuredResult result = response.getResult();

		Map<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("Name", "<PERSON>");
		expected.put("Phone", "This is its number: <PHONE_NUMBER>");

		assertEquals(expected, result.getRedactedData());
		assertNull(result.getReport());
	}

	@Test
	public void testStructuredRequest_5() throws PangeaException, PangeaAPIException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");

		RedactStructuredResponse response = client.redactStructured(
			new RedactStructuredRequest.Builder(data).setFormat("json").setDebug(true).build()
		);
		assertTrue(response.isOk());

		RedactStructuredResult result = response.getResult();

		Map<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("Name", "<PERSON>");
		expected.put("Phone", "This is its number: <PHONE_NUMBER>");

		assertEquals(expected, result.getRedactedData());
		assertNotNull(result.getReport());
	}

	@Test
	public void testStructuredRequest_6() throws PangeaException, PangeaAPIException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");

		RedactStructuredResponse response = client.redactStructured(
			new RedactStructuredRequest.Builder(data).setDebug(true).setJsonp(new String[] { "Phone" }).build()
		);
		assertTrue(response.isOk());

		RedactStructuredResult result = response.getResult();

		Map<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("Name", "Jenny Jenny");
		expected.put("Phone", "This is its number: <PHONE_NUMBER>");

		assertEquals(expected, result.getRedactedData());
		assertNotNull(result.getReport());
	}

	@Test
	public void testStructuredRequest_7() throws PangeaException, PangeaAPIException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");

		RedactStructuredResponse response = client.redactStructured(
			new RedactStructuredRequest.Builder(data).setRules(new String[] { "PHONE_NUMBER" }).build()
		);
		assertTrue(response.isOk());

		RedactStructuredResult result = response.getResult();

		Map<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("Name", "Jenny Jenny");
		expected.put("Phone", "This is its number: <PHONE_NUMBER>");

		assertEquals(expected, result.getRedactedData());
		assertNull(result.getReport());
	}

	@Test
	public void testStructuredRequest_8() throws PangeaException, PangeaAPIException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");

		RedactStructuredResponse response = client.redactStructured(
			new RedactStructuredRequest.Builder(data)
				.setDebug(true)
				.setJsonp(new String[] { "Phone", "Name" })
				.setRules(new String[] { "PHONE_NUMBER" })
				.build()
		);
		assertTrue(response.isOk());

		RedactStructuredResult result = response.getResult();

		Map<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("Name", "Jenny Jenny");
		expected.put("Phone", "This is its number: <PHONE_NUMBER>");

		assertEquals(expected, result.getRedactedData());
		assertNotNull(result.getReport());
	}

	@Test(expected = UnauthorizedException.class)
	public void testRedactTextUnauthorized() throws PangeaException, PangeaAPIException, ConfigException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg = new Config.Builder("notarealtoken", cfg.getDomain()).build();
		RedactClient fakeClient = new RedactClient.Builder(cfg).build();
		RedactTextResponse response = fakeClient.redactText(
			new RedactTextRequest.Builder("My name is Jenny Jenny").build()
		);
	}

	@Test(expected = UnauthorizedException.class)
	public void testRedactStructuredUnauthorized() throws PangeaException, PangeaAPIException, ConfigException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg = new Config.Builder("notarealtoken", cfg.getDomain()).build();
		RedactClient fakeClient = new RedactClient.Builder(cfg).build();
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");
		RedactStructuredResponse response = fakeClient.redactStructured(
			new RedactStructuredRequest.Builder(data).build()
		);
	}
}
