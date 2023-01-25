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
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class ITRedactTest {

	RedactClient client;
	TestEnvironment environment = TestEnvironment.LIVE;

	@Before
	public void setUp() throws ConfigException {
		client = new RedactClient(Config.fromIntegrationEnvironment(environment));
	}

	@Test
	public void testRedact_1() throws PangeaException, PangeaAPIException {
		RedactTextResponse response = client.redactText("Jenny Jenny... 415-867-5309");
		assertTrue(response.isOk());

		RedactTextResult result = response.getResult();
		assertEquals("<PERSON>... <PHONE_NUMBER>", result.getRedactedText());
		assertNull(result.getReport());
	}

	@Test
	public void testRedact_2() throws PangeaException, PangeaAPIException {
		RedactTextResponse response = client.redactText("Jenny Jenny... 415-867-5309", true);
		assertTrue(response.isOk());

		RedactTextResult result = response.getResult();
		assertEquals("<PERSON>... <PHONE_NUMBER>", result.getRedactedText());
		assertNotNull(result.getReport());
	}

	@Test
	public void testRedact_3() throws PangeaException, PangeaAPIException {
		RedactTextResponse response = client.redactText("Jenny Jenny... 415-867-5309", false);
		assertTrue(response.isOk());

		RedactTextResult result = response.getResult();
		assertEquals("<PERSON>... <PHONE_NUMBER>", result.getRedactedText());
		assertNull(result.getReport());
	}

	@Test
	public void testStructured_1() throws PangeaException, PangeaAPIException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");

		RedactStructuredResponse response = client.redactStructured(data);
		assertTrue(response.isOk());

		RedactStructuredResult result = response.getResult();

		Map<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("Name", "<PERSON>");
		expected.put("Phone", "This is its number: <PHONE_NUMBER>");

		assertEquals(expected, result.getRedactedData());
		assertNull(result.getReport());
	}

	@Test
	public void testStructured_2() throws PangeaException, PangeaAPIException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");

		RedactStructuredResponse response = client.redactStructured(data, "json");
		assertTrue(response.isOk());

		RedactStructuredResult result = response.getResult();

		Map<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("Name", "<PERSON>");
		expected.put("Phone", "This is its number: <PHONE_NUMBER>");

		assertEquals(expected, result.getRedactedData());
		assertNull(result.getReport());
	}

	@Test
	public void testStructured_3() throws PangeaException, PangeaAPIException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");

		RedactStructuredResponse response = client.redactStructured(data, true);
		assertTrue(response.isOk());

		RedactStructuredResult result = response.getResult();

		Map<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("Name", "<PERSON>");
		expected.put("Phone", "This is its number: <PHONE_NUMBER>");

		assertEquals(expected, result.getRedactedData());
		assertNotNull(result.getReport());
	}

	@Test
	public void testStructured_4() throws PangeaException, PangeaAPIException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");

		RedactStructuredResponse response = client.redactStructured(data, false);
		assertTrue(response.isOk());

		RedactStructuredResult result = response.getResult();

		Map<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("Name", "<PERSON>");
		expected.put("Phone", "This is its number: <PHONE_NUMBER>");

		assertEquals(expected, result.getRedactedData());
		assertNull(result.getReport());
	}

	@Test
	public void testStructured_5() throws PangeaException, PangeaAPIException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");

		RedactStructuredResponse response = client.redactStructured(data, "json", true);
		assertTrue(response.isOk());

		RedactStructuredResult result = response.getResult();

		Map<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("Name", "<PERSON>");
		expected.put("Phone", "This is its number: <PHONE_NUMBER>");

		assertEquals(expected, result.getRedactedData());
		assertNotNull(result.getReport());
	}

	@Test
	public void testStructured_6() throws PangeaException, PangeaAPIException {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");

		RedactStructuredResponse response = client.redactStructured(data, true, new String[] { "Phone" });
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
		cfg.setToken("notarealtoken");
		RedactClient fakeClient = new RedactClient(cfg);
		RedactTextResponse response = fakeClient.redactText("Jenny Jenny... 415-867-5309", false);
	}

	@Test(expected = UnauthorizedException.class)
	public void testRedactStructuredUnauthorized() throws PangeaException, PangeaAPIException, ConfigException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg.setToken("notarealtoken");
		RedactClient fakeClient = new RedactClient(cfg);
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("Name", "Jenny Jenny");
		data.put("Phone", "This is its number: 415-867-5309");
		RedactStructuredResponse response = fakeClient.redactStructured(data, true, new String[] { "Phone" });
	}
}
