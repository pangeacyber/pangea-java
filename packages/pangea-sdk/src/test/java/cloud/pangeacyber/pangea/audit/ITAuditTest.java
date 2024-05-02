package cloud.pangeacyber.pangea.audit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cloud.pangeacyber.pangea.AttachedFile;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Helper;
import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.ResponseStatus;
import cloud.pangeacyber.pangea.TestEnvironment;
import cloud.pangeacyber.pangea.audit.models.*;
import cloud.pangeacyber.pangea.audit.requests.*;
import cloud.pangeacyber.pangea.audit.responses.*;
import cloud.pangeacyber.pangea.audit.results.LogBulkResult;
import cloud.pangeacyber.pangea.audit.results.RootResult;
import cloud.pangeacyber.pangea.exceptions.*;
import com.fasterxml.jackson.core.type.TypeReference;
import java.nio.file.Path;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ITAuditTest {

	Config cfgGeneral;
	AuditClient clientGeneral, clientGeneralNoQueue, localSignClient, localSignInfoClient, vaultSignClient, signNtenandIDClient, customSchemaClient, localSignCustomSchemaClient;
	static TestEnvironment environment;
	CustomEvent customEvent;

	private static final String ACTOR = "java-sdk";
	private static final String MSG_NO_SIGNED = "test-message";
	private static final String MSG_SIGNED_LOCAL = "sign-test-local";
	private static final String MSG_SIGNED_VAULT = "sign-test-vault";
	private static final String STATUS_NO_SIGNED = "no-signed";
	private static final String STATUS_SIGNED = "signed";
	private static final String MSG_CUSTOM_SCHEMA_NO_SIGNED = "java-sdk-custom-schema-no-signed";
	private static final String MSG_CUSTOM_SCHEMA_SIGNED_LOCAL = "java-sdk-custom-schema-sign-local";
	private static final String LONG_FIELD =
		"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed lacinia, orci eget commodo commodo non.";

	@BeforeClass
	public static void setUpClass() throws Exception {
		environment = Helper.loadTestEnvironment("audit", TestEnvironment.LIVE);
	}

	@Before
	public void setUp() throws Exception {
		Config vaultCfg = Config.fromVaultIntegrationEnvironment(environment);
		this.cfgGeneral = Config.fromIntegrationEnvironment(environment);
		Config cfgGeneralNoQueue = Config.fromIntegrationEnvironment(environment);
		cfgGeneralNoQueue.setQueuedRetryEnabled(false);
		Config customSchemaCfg = Config.fromCustomSchemaIntegrationEnvironment(environment);
		Map<String, Object> pkInfo = new LinkedHashMap<String, Object>();
		pkInfo.put("ExtraInfo", "LocalKey");

		clientGeneral = new AuditClient.Builder(cfgGeneral).build();
		clientGeneralNoQueue = new AuditClient.Builder(cfgGeneralNoQueue).build();
		vaultSignClient = new AuditClient.Builder(vaultCfg).build();
		customSchemaClient = new AuditClient.Builder(customSchemaCfg).withCustomSchema(CustomEvent.class).build();

		localSignClient =
			new AuditClient.Builder(cfgGeneral)
				.withPrivateKey("./src/test/java/cloud/pangeacyber/pangea/testdata/privkey")
				.build();

		localSignCustomSchemaClient =
			new AuditClient.Builder(customSchemaCfg)
				.withCustomSchema(CustomEvent.class)
				.withPrivateKey("./src/test/java/cloud/pangeacyber/pangea/testdata/privkey")
				.build();

		signNtenandIDClient =
			new AuditClient.Builder(cfgGeneral)
				.withTenantID("mytenantid")
				.withPrivateKey("./src/test/java/cloud/pangeacyber/pangea/testdata/privkey")
				.build();
		localSignInfoClient =
			new AuditClient.Builder(cfgGeneral)
				.withPrivateKey("./src/test/java/cloud/pangeacyber/pangea/testdata/privkey")
				.withPkInfo(pkInfo)
				.build();

		customEvent =
			new CustomEvent.Builder(MSG_CUSTOM_SCHEMA_NO_SIGNED)
				.fieldInt(1)
				.fieldBool(true)
				.fieldStrShort(STATUS_NO_SIGNED)
				.fieldStrLong(LONG_FIELD)
				.build();
	}

	@Test
	public void testLog() throws PangeaException, PangeaAPIException {
		StandardEvent event = new StandardEvent(MSG_NO_SIGNED);
		event.setActor(ACTOR);
		event.setStatus(STATUS_NO_SIGNED);

		LogResponse response = clientGeneral.log(event, new LogConfig.Builder().verbose(false).verify(false).build());
		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		assertNull(result.getConsistencyProof());
		assertNull(result.getMembershipProof());
		assertEquals(result.getConsistencyVerification(), EventVerification.NOT_VERIFIED);
		assertEquals(result.getMembershipVerification(), EventVerification.NOT_VERIFIED);
		assertEquals(result.getSignatureVerification(), EventVerification.NOT_VERIFIED);
	}

	@Test
	public void testLog_customSchema() throws PangeaException, PangeaAPIException {
		LogResponse response = customSchemaClient.log(
			customEvent,
			new LogConfig.Builder().verbose(false).verify(false).build()
		);
		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		assertNull(result.getConsistencyProof());
		assertNull(result.getMembershipProof());
		assertEquals(result.getConsistencyVerification(), EventVerification.NOT_VERIFIED);
		assertEquals(result.getMembershipVerification(), EventVerification.NOT_VERIFIED);
		assertEquals(result.getSignatureVerification(), EventVerification.NOT_VERIFIED);
	}

	@Test
	public void testLogNoVerbose() throws PangeaException, PangeaAPIException {
		StandardEvent event = new StandardEvent(MSG_NO_SIGNED);
		event.setActor(ACTOR);
		event.setStatus(STATUS_NO_SIGNED);

		LogResponse response = clientGeneral.log(
			event,
			new LogConfig.Builder().verbose(false).signLocal(false).verify(false).build()
		);
		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		assertNull(result.getConsistencyProof());
		assertNull(result.getMembershipProof());
		assertEquals(EventVerification.NOT_VERIFIED, result.getConsistencyVerification());
		assertEquals(EventVerification.NOT_VERIFIED, result.getMembershipVerification());
		assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());
	}

	@Test
	public void testLogNoVerbose_customSchema() throws PangeaException, PangeaAPIException {
		LogResponse response = customSchemaClient.log(
			customEvent,
			new LogConfig.Builder().verbose(false).signLocal(false).verify(false).build()
		);

		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		assertNull(result.getConsistencyProof());
		assertNull(result.getMembershipProof());
		assertEquals(EventVerification.NOT_VERIFIED, result.getConsistencyVerification());
		assertEquals(EventVerification.NOT_VERIFIED, result.getMembershipVerification());
		assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());
	}

	@Test
	public void testLogVerbose() throws PangeaAPIException, PangeaException {
		StandardEvent event = new StandardEvent(MSG_NO_SIGNED);
		event.setActor(ACTOR);
		event.setStatus(STATUS_NO_SIGNED);

		try {
			LogResponse response = clientGeneral.log(
				event,
				new LogConfig.Builder().verbose(true).signLocal(false).verify(false).build()
			);

			assertTrue(response.isOk());

			LogResult result = response.getResult();
			assertNotNull(result.getEventEnvelope());
			assertNotNull(result.getHash());
			StandardEvent eventResult = (StandardEvent) result.getEventEnvelope().getEvent();
			assertEquals(MSG_NO_SIGNED, eventResult.getMessage());
			assertNull(result.getConsistencyProof());
			assertNotNull(result.getMembershipProof());
			assertEquals(EventVerification.NOT_VERIFIED, result.getConsistencyVerification());
			assertEquals(EventVerification.NOT_VERIFIED, result.getMembershipVerification());
			assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
		}
	}

	@Test
	public void testLogVerbose_customSchema() throws PangeaAPIException, PangeaException {
		try {
			LogResponse response = customSchemaClient.log(
				customEvent,
				new LogConfig.Builder().verbose(true).signLocal(false).verify(false).build()
			);

			assertTrue(response.isOk());

			LogResult result = response.getResult();
			assertNotNull(result.getEventEnvelope());
			assertNotNull(result.getHash());
			CustomEvent eventResult = (CustomEvent) result.getEventEnvelope().getEvent();
			assertEquals(MSG_CUSTOM_SCHEMA_NO_SIGNED, eventResult.getMessage());
			assertEquals(Integer.valueOf(1), eventResult.getFieldInt());
			assertEquals(true, eventResult.getFieldBool());
			assertEquals(STATUS_NO_SIGNED, eventResult.getFieldStrShort());
			assertEquals(LONG_FIELD, eventResult.getFieldStrLong());
			assertNull(result.getConsistencyProof());
			assertNotNull(result.getMembershipProof());
			assertEquals(EventVerification.NOT_VERIFIED, result.getConsistencyVerification());
			assertEquals(EventVerification.NOT_VERIFIED, result.getMembershipVerification());
			assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
		}
	}

	@Test
	public void testLogTenantID() throws PangeaAPIException, PangeaException, ConfigException {
		StandardEvent event = new StandardEvent(MSG_NO_SIGNED);
		event.setActor(ACTOR);
		event.setStatus(STATUS_NO_SIGNED);

		LogResponse response = signNtenandIDClient.log(
			event,
			new LogConfig.Builder().verbose(true).signLocal(false).verify(false).build()
		);
		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNotNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		StandardEvent eventResult = (StandardEvent) result.getEventEnvelope().getEvent();
		assertEquals(MSG_NO_SIGNED, eventResult.getMessage());
		assertNull(result.getConsistencyProof());
		assertNotNull(result.getMembershipProof());
		assertEquals(EventVerification.NOT_VERIFIED, result.getConsistencyVerification());
		assertEquals(EventVerification.NOT_VERIFIED, result.getMembershipVerification());
		assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());
		assertEquals("mytenantid", eventResult.getTenantID());
	}

	@Test
	public void testLogVerify() throws PangeaAPIException, PangeaException {
		StandardEvent event = new StandardEvent(MSG_NO_SIGNED);
		event.setActor(ACTOR);
		event.setStatus(STATUS_NO_SIGNED);

		LogResponse response = clientGeneral.log(
			event,
			new LogConfig.Builder().verbose(true).signLocal(false).verify(true).build()
		);

		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNotNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		StandardEvent eventResult = (StandardEvent) result.getEventEnvelope().getEvent();
		assertEquals(MSG_NO_SIGNED, eventResult.getMessage());
		assertEquals(EventVerification.NOT_VERIFIED, result.getConsistencyVerification());
		assertEquals(EventVerification.SUCCESS, result.getMembershipVerification());
		assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());

		// Second log
		event = new StandardEvent(MSG_NO_SIGNED);
		event.setActor(ACTOR);
		event.setStatus(STATUS_NO_SIGNED);
		response =
			clientGeneral.log(event, new LogConfig.Builder().verbose(true).signLocal(false).verify(true).build());

		assertTrue(response.isOk());

		result = response.getResult();
		assertNotNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		eventResult = (StandardEvent) result.getEventEnvelope().getEvent();
		assertEquals(MSG_NO_SIGNED, eventResult.getMessage());
		assertEquals(EventVerification.SUCCESS, result.getConsistencyVerification());
		assertEquals(EventVerification.SUCCESS, result.getMembershipVerification());
		assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());
	}

	@Test
	public void testLogVerify_customSchema() throws PangeaAPIException, PangeaException {
		LogResponse response = customSchemaClient.log(
			customEvent,
			new LogConfig.Builder().verbose(true).signLocal(false).verify(true).build()
		);

		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNotNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		CustomEvent eventResult = (CustomEvent) result.getEventEnvelope().getEvent();
		assertEquals(MSG_CUSTOM_SCHEMA_NO_SIGNED, eventResult.getMessage());
		assertEquals(Integer.valueOf(1), eventResult.getFieldInt());
		assertEquals(true, eventResult.getFieldBool());
		assertEquals(STATUS_NO_SIGNED, eventResult.getFieldStrShort());
		assertEquals(LONG_FIELD, eventResult.getFieldStrLong());
		assertEquals(EventVerification.NOT_VERIFIED, result.getConsistencyVerification());
		assertEquals(EventVerification.SUCCESS, result.getMembershipVerification());
		assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());

		// Second log
		response =
			customSchemaClient.log(
				customEvent,
				new LogConfig.Builder().verbose(true).signLocal(false).verify(true).build()
			);

		assertTrue(response.isOk());

		result = response.getResult();
		assertNotNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		eventResult = (CustomEvent) result.getEventEnvelope().getEvent();
		assertEquals(MSG_CUSTOM_SCHEMA_NO_SIGNED, eventResult.getMessage());
		assertEquals(Integer.valueOf(1), eventResult.getFieldInt());
		assertEquals(true, eventResult.getFieldBool());
		assertEquals(STATUS_NO_SIGNED, eventResult.getFieldStrShort());
		assertEquals(LONG_FIELD, eventResult.getFieldStrLong());
		assertEquals(EventVerification.SUCCESS, result.getConsistencyVerification());
		assertEquals(EventVerification.SUCCESS, result.getMembershipVerification());
		assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());
	}

	@Test
	public void testLogLocalSignature() throws PangeaException, PangeaAPIException, ConfigException {
		StandardEvent event = new StandardEvent(MSG_SIGNED_LOCAL);
		event.setActor(ACTOR);
		event.setAction("Action");
		event.setSource("Source");
		event.setStatus(STATUS_SIGNED);
		event.setTarget("Target");
		event.setNewField("New");
		event.setOld("Old");

		LogResponse response = localSignClient.log(
			event,
			new LogConfig.Builder().verbose(true).signLocal(true).verify(true).build()
		);

		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNotNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		StandardEvent eventResult = (StandardEvent) result.getEventEnvelope().getEvent();
		assertEquals(MSG_SIGNED_LOCAL, eventResult.getMessage());
		assertEquals(
			"""
{"algorithm":"ED25519","key":"-----BEGIN PUBLIC KEY-----\\nMCowBQYDK2VwAyEAlvOyDMpK2DQ16NI8G41yINl01wMHzINBahtDPoh4+mE=\\n-----END PUBLIC KEY-----\\n"}""",
			result.getEventEnvelope().getPublicKey()
		);
		assertEquals(EventVerification.SUCCESS, result.getSignatureVerification());
	}

	@Test
	public void testLogLocalSignature_customSchema() throws PangeaException, PangeaAPIException, ConfigException {
		CustomEvent event = new CustomEvent.Builder(MSG_CUSTOM_SCHEMA_SIGNED_LOCAL)
			.fieldInt(1)
			.fieldBool(true)
			.fieldStrShort(STATUS_SIGNED)
			.fieldStrLong(LONG_FIELD)
			.build();

		LogResponse response = localSignCustomSchemaClient.log(
			event,
			new LogConfig.Builder().verbose(true).signLocal(true).verify(true).build()
		);

		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNotNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		CustomEvent eventResult = (CustomEvent) result.getEventEnvelope().getEvent();
		assertEquals(MSG_CUSTOM_SCHEMA_SIGNED_LOCAL, eventResult.getMessage());
		assertEquals(
			"""
{"algorithm":"ED25519","key":"-----BEGIN PUBLIC KEY-----\\nMCowBQYDK2VwAyEAlvOyDMpK2DQ16NI8G41yINl01wMHzINBahtDPoh4+mE=\\n-----END PUBLIC KEY-----\\n"}""",
			result.getEventEnvelope().getPublicKey()
		);
		assertEquals(EventVerification.SUCCESS, result.getSignatureVerification());
	}

	@Test
	public void testLogLocalSignatureWithPublicKeyInfo() throws PangeaException, PangeaAPIException, ConfigException {
		StandardEvent event = new StandardEvent(MSG_SIGNED_LOCAL);
		event.setActor(ACTOR);
		event.setAction("Action");
		event.setSource("Source");
		event.setStatus(STATUS_SIGNED);
		event.setTarget("Target");
		event.setNewField("New");
		event.setOld("Old");

		LogResponse response = localSignInfoClient.log(
			event,
			new LogConfig.Builder().verbose(true).signLocal(true).verify(true).build()
		);

		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNotNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		StandardEvent eventResult = (StandardEvent) result.getEventEnvelope().getEvent();
		assertEquals(MSG_SIGNED_LOCAL, eventResult.getMessage());
		assertEquals(
			"""
{"ExtraInfo":"LocalKey","algorithm":"ED25519","key":"-----BEGIN PUBLIC KEY-----\\nMCowBQYDK2VwAyEAlvOyDMpK2DQ16NI8G41yINl01wMHzINBahtDPoh4+mE=\\n-----END PUBLIC KEY-----\\n"}""",
			result.getEventEnvelope().getPublicKey()
		);
		assertEquals(EventVerification.SUCCESS, result.getSignatureVerification());
	}

	@Test
	public void testLogVaultSignature() throws PangeaException, PangeaAPIException, ConfigException {
		StandardEvent event = new StandardEvent(MSG_SIGNED_VAULT);
		event.setActor(ACTOR);
		event.setAction("Action");
		event.setSource("Source");
		event.setStatus(STATUS_SIGNED);
		event.setTarget("Target");
		event.setNewField("New");
		event.setOld("Old");

		LogResponse response = vaultSignClient.log(
			event,
			new LogConfig.Builder().verbose(true).signLocal(false).verify(true).build()
		);

		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNotNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		StandardEvent eventResult = (StandardEvent) result.getEventEnvelope().getEvent();
		assertEquals(MSG_SIGNED_VAULT, eventResult.getMessage());
		assertNotNull(result.getEventEnvelope().getPublicKey());
		assertNotNull(result.getEventEnvelope().getSignature());
		assertEquals(EventVerification.SUCCESS, result.getSignatureVerification());
	}

	@Test
	public void testLogLocalSignatureAndTenantID() throws PangeaException, PangeaAPIException, ConfigException {
		StandardEvent event = new StandardEvent(MSG_SIGNED_LOCAL);
		event.setActor(ACTOR);
		event.setAction("Action");
		event.setSource("Source");
		event.setStatus(STATUS_SIGNED);
		event.setTarget("Target");
		event.setNewField("New");
		event.setOld("Old");

		LogResponse response = signNtenandIDClient.log(
			event,
			new LogConfig.Builder().verbose(true).signLocal(true).verify(true).build()
		);

		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNotNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		StandardEvent eventResult = (StandardEvent) result.getEventEnvelope().getEvent();
		assertEquals(MSG_SIGNED_LOCAL, eventResult.getMessage());
		assertEquals(
			"""
{"algorithm":"ED25519","key":"-----BEGIN PUBLIC KEY-----\\nMCowBQYDK2VwAyEAlvOyDMpK2DQ16NI8G41yINl01wMHzINBahtDPoh4+mE=\\n-----END PUBLIC KEY-----\\n"}""",
			result.getEventEnvelope().getPublicKey()
		);
		assertEquals(EventVerification.SUCCESS, result.getSignatureVerification());
		assertEquals("mytenantid", eventResult.getTenantID());
	}

	@Test
	public void testSearchDefault() throws PangeaException, PangeaAPIException {
		int limit = 4;
		int maxResults = 6;
		SearchRequest request = new SearchRequest.Builder("message:\"\"")
			.maxResults(maxResults)
			.limit(limit)
			.order("asc")
			.build();

		SearchConfig config = new SearchConfig.Builder().build();

		SearchResponse response = clientGeneral.search(request, config);
		assertTrue(response.isOk());
		assertTrue(response.getResult().getCount() <= maxResults);

		for (SearchEvent event : response.getResult().getEvents()) {
			assertEquals(EventVerification.NOT_VERIFIED, event.getConsistencyVerification()); // This could be NOT_VERIFIED or SUCCESS is they have data or not
			assertEquals(EventVerification.NOT_VERIFIED, event.getMembershipVerification());
			assertNotNull(event.getEventEnvelope());
			assertNotNull(event.getHash());
		}
	}

	@Test
	public void testSearchDefault_customSchema() throws PangeaException, PangeaAPIException {
		int limit = 4;
		int maxResults = 6;
		SearchRequest request = new SearchRequest.Builder("message:\"\"")
			.maxResults(maxResults)
			.limit(limit)
			.order("asc")
			.build();

		SearchConfig config = new SearchConfig.Builder().build();

		SearchResponse response = customSchemaClient.search(request, config);
		assertTrue(response.isOk());
		assertTrue(response.getResult().getCount() <= maxResults);

		for (SearchEvent event : response.getResult().getEvents()) {
			assertEquals(EventVerification.NOT_VERIFIED, event.getConsistencyVerification()); // This could be NOT_VERIFIED or SUCCESS is they have data or not
			assertEquals(EventVerification.NOT_VERIFIED, event.getMembershipVerification());
			assertNotNull(event.getEventEnvelope());
			assertNotNull(event.getHash());
		}
	}

	@Test
	public void testSearchNoVerify() throws PangeaAPIException, PangeaException {
		int maxResults = 10;
		SearchRequest request = new SearchRequest.Builder("message:Integration test msg")
			.maxResults(maxResults)
			.order("desc")
			.build();

		SearchConfig config = new SearchConfig.Builder().build();

		SearchResponse response = clientGeneral.search(request, config);
		assertTrue(response.isOk());
		assertTrue(response.getResult().getCount() <= maxResults);

		for (SearchEvent event : response.getResult().getEvents()) {
			assertEquals(EventVerification.NOT_VERIFIED, event.getConsistencyVerification());
			assertEquals(EventVerification.NOT_VERIFIED, event.getMembershipVerification());
			assertEquals(EventVerification.NOT_VERIFIED, event.getSignatureVerification());
		}
	}

	@Test
	public void testSearchNoVerify_customEvent() throws PangeaAPIException, PangeaException {
		int maxResults = 10;
		SearchRequest request = new SearchRequest.Builder("message:" + MSG_CUSTOM_SCHEMA_NO_SIGNED)
			.maxResults(maxResults)
			.order("desc")
			.build();

		SearchConfig config = new SearchConfig.Builder().build();

		SearchResponse response = customSchemaClient.search(request, config);
		assertTrue(response.isOk());
		assertTrue(response.getResult().getCount() <= maxResults);

		for (SearchEvent event : response.getResult().getEvents()) {
			assertEquals(EventVerification.NOT_VERIFIED, event.getConsistencyVerification());
			assertEquals(EventVerification.NOT_VERIFIED, event.getMembershipVerification());
			assertEquals(EventVerification.NOT_VERIFIED, event.getSignatureVerification());
		}
	}

	@Test
	public void testSearchVerifyConsistency() throws PangeaAPIException, PangeaException {
		int maxResults = 10;
		SearchRequest request = new SearchRequest.Builder("message:\"\"").maxResults(maxResults).order("asc").build();

		SearchConfig config = new SearchConfig.Builder().verifyConsistency(true).build();

		SearchResponse response = clientGeneral.search(request, config);
		assertTrue(response.isOk());
		assertTrue(response.getResult().getCount() <= maxResults);

		for (SearchEvent event : response.getResult().getEvents()) {
			assertEquals(EventVerification.SUCCESS, event.getConsistencyVerification());
			assertEquals(EventVerification.SUCCESS, event.getMembershipVerification());
		}
	}

	@Test
	public void testSearchVerifyConsistency_customEvent() throws PangeaAPIException, PangeaException {
		int maxResults = 10;
		SearchRequest request = new SearchRequest.Builder("message:\"\"").maxResults(maxResults).order("asc").build();

		SearchConfig config = new SearchConfig.Builder().verifyConsistency(true).build();

		SearchResponse response = customSchemaClient.search(request, config);
		assertTrue(response.isOk());
		assertTrue(response.getResult().getCount() <= maxResults);

		for (SearchEvent event : response.getResult().getEvents()) {
			assertEquals(EventVerification.SUCCESS, event.getConsistencyVerification());
			assertEquals(EventVerification.SUCCESS, event.getMembershipVerification());
		}
	}

	@Test
	public void testSearchVerifySignature() throws PangeaAPIException, PangeaException {
		int maxResults = 10;
		SearchRequest request = new SearchRequest.Builder("message:" + MSG_SIGNED_LOCAL + " status:" + STATUS_SIGNED)
			.maxResults(maxResults)
			.order("asc")
			.build();

		SearchConfig config = new SearchConfig.Builder().build();

		SearchResponse response = clientGeneral.search(request, config);
		assertTrue(response.isOk());
		assertTrue(response.getResult().getCount() <= maxResults);

		for (SearchEvent event : response.getResult().getEvents()) {
			assertEquals(EventVerification.SUCCESS, event.getSignatureVerification());
		}
	}

	@Test
	public void testSearchVerifySignature_customEvent() throws PangeaAPIException, PangeaException {
		int maxResults = 10;
		SearchRequest request = new SearchRequest.Builder("message:" + MSG_CUSTOM_SCHEMA_SIGNED_LOCAL)
			.maxResults(maxResults)
			.order("asc")
			.build();

		SearchConfig config = new SearchConfig.Builder().build();

		SearchResponse response = customSchemaClient.search(request, config);
		assertTrue(response.isOk());
		assertTrue(response.getResult().getCount() <= maxResults);

		for (SearchEvent event : response.getResult().getEvents()) {
			assertEquals(EventVerification.SUCCESS, event.getSignatureVerification());
		}
	}

	@Test
	public void testResultsDefault() throws PangeaAPIException, PangeaException {
		int searchMaxResults = 10;

		SearchRequest request = new SearchRequest.Builder("message:\"\"")
			.maxResults(searchMaxResults)
			.order("asc")
			.build();

		SearchConfig config = new SearchConfig.Builder().build();

		SearchResponse searchResponse = clientGeneral.search(request, config);
		assertTrue(searchResponse.isOk());
		assertTrue(searchResponse.getResult().getCount() <= searchMaxResults);
		assertTrue(searchResponse.getResult().getCount() > 0);

		int resultsLimit = 3;
		ResultRequest resultRequest = new ResultRequest.Builder(searchResponse.getResult().getId())
			.limit(resultsLimit)
			.offset(0)
			.build();
		ResultsResponse resultsResponse = clientGeneral.results(resultRequest, config);
		assertEquals(resultsResponse.getResult().getCount(), resultsLimit);
		for (SearchEvent event : resultsResponse.getResult().getEvents()) {
			assertEquals(EventVerification.NOT_VERIFIED, event.getConsistencyVerification());
			assertEquals(EventVerification.NOT_VERIFIED, event.getMembershipVerification());
		}
	}

	public void testResultsVerify() throws PangeaAPIException, PangeaException {
		int searchMaxResults = 10;
		SearchRequest request = new SearchRequest.Builder("message:\"\"")
			.maxResults(searchMaxResults)
			.order("asc")
			.build();

		SearchConfig config = new SearchConfig.Builder().verifyConsistency(true).build();

		SearchResponse searchResponse = clientGeneral.search(request, config);
		assertTrue(searchResponse.isOk());
		assertTrue(searchResponse.getResult().getCount() <= searchMaxResults);
		assertTrue(searchResponse.getResult().getCount() > 0);

		int resultsLimit = 3;

		ResultRequest resultRequest = new ResultRequest.Builder(searchResponse.getResult().getId())
			.limit(resultsLimit)
			.offset(0)
			.build();

		ResultsResponse resultsResponse = clientGeneral.results(resultRequest, config);

		assertEquals(resultsResponse.getResult().getCount(), resultsLimit);
		for (SearchEvent event : resultsResponse.getResult().getEvents()) {
			assertEquals(EventVerification.SUCCESS, event.getConsistencyVerification());
			assertEquals(EventVerification.SUCCESS, event.getMembershipVerification());
		}
	}

	@Test
	public void testResultsNoVerify() throws PangeaAPIException, PangeaException {
		int searchMaxResults = 10;
		SearchRequest request = new SearchRequest.Builder("message:\"\"")
			.maxResults(searchMaxResults)
			.order("asc")
			.build();

		SearchConfig config = new SearchConfig.Builder().verifyConsistency(true).verifyEvents(true).build();

		SearchResponse searchResponse = clientGeneral.search(request, config);
		assertTrue(searchResponse.isOk());
		assertTrue(searchResponse.getResult().getCount() <= searchMaxResults);
		assertTrue(searchResponse.getResult().getCount() > 0);

		int resultsLimit = 3;
		// Skip verifications
		ResultRequest resultRequest = new ResultRequest.Builder(searchResponse.getResult().getId())
			.limit(resultsLimit)
			.offset(0)
			.build();

		config = new SearchConfig.Builder().verifyConsistency(false).build();
		ResultsResponse resultsResponse = clientGeneral.results(resultRequest, config);

		assertEquals(resultsResponse.getResult().getCount(), resultsLimit);
		for (SearchEvent event : resultsResponse.getResult().getEvents()) {
			// This should be NOT_VERIFIED
			assertEquals(EventVerification.NOT_VERIFIED, event.getConsistencyVerification());
			assertEquals(EventVerification.NOT_VERIFIED, event.getMembershipVerification());
		}
	}

	@Test
	public void testResultsDefault_customEvent() throws PangeaAPIException, PangeaException {
		int searchMaxResults = 10;

		SearchRequest request = new SearchRequest.Builder("message:\"\"")
			.maxResults(searchMaxResults)
			.order("asc")
			.build();

		SearchConfig config = new SearchConfig.Builder().build();

		SearchResponse searchResponse = customSchemaClient.search(request, config);
		assertTrue(searchResponse.isOk());
		assertTrue(searchResponse.getResult().getCount() <= searchMaxResults);
		assertTrue(searchResponse.getResult().getCount() > 0);

		int resultsLimit = 3;
		ResultRequest resultRequest = new ResultRequest.Builder(searchResponse.getResult().getId())
			.limit(resultsLimit)
			.offset(0)
			.build();
		ResultsResponse resultsResponse = customSchemaClient.results(resultRequest, config);
		assertEquals(resultsResponse.getResult().getCount(), resultsLimit);
		for (SearchEvent event : resultsResponse.getResult().getEvents()) {
			assertEquals(EventVerification.NOT_VERIFIED, event.getConsistencyVerification());
			assertEquals(EventVerification.NOT_VERIFIED, event.getMembershipVerification());
		}
	}

	public void testResultsVerify_customEvent() throws PangeaAPIException, PangeaException {
		int searchMaxResults = 10;
		SearchRequest request = new SearchRequest.Builder("message:\"\"")
			.maxResults(searchMaxResults)
			.order("asc")
			.build();

		SearchConfig config = new SearchConfig.Builder().verifyConsistency(true).build();

		SearchResponse searchResponse = customSchemaClient.search(request, config);
		assertTrue(searchResponse.isOk());
		assertTrue(searchResponse.getResult().getCount() <= searchMaxResults);
		assertTrue(searchResponse.getResult().getCount() > 0);

		int resultsLimit = 3;

		ResultRequest resultRequest = new ResultRequest.Builder(searchResponse.getResult().getId())
			.limit(resultsLimit)
			.offset(0)
			.build();

		ResultsResponse resultsResponse = customSchemaClient.results(resultRequest, config);

		assertEquals(resultsResponse.getResult().getCount(), resultsLimit);
		for (SearchEvent event : resultsResponse.getResult().getEvents()) {
			assertEquals(EventVerification.SUCCESS, event.getConsistencyVerification());
			assertEquals(EventVerification.SUCCESS, event.getMembershipVerification());
		}
	}

	@Test
	public void testResultsNoVerify_customEvent() throws PangeaAPIException, PangeaException {
		int searchMaxResults = 10;
		SearchRequest request = new SearchRequest.Builder("message:\"\"")
			.maxResults(searchMaxResults)
			.order("asc")
			.build();

		SearchConfig config = new SearchConfig.Builder().verifyConsistency(true).verifyEvents(true).build();

		SearchResponse searchResponse = customSchemaClient.search(request, config);
		assertTrue(searchResponse.isOk());
		assertTrue(searchResponse.getResult().getCount() <= searchMaxResults);
		assertTrue(searchResponse.getResult().getCount() > 0);

		int resultsLimit = 3;
		// Skip verifications
		ResultRequest resultRequest = new ResultRequest.Builder(searchResponse.getResult().getId())
			.limit(resultsLimit)
			.offset(0)
			.build();

		config = new SearchConfig.Builder().verifyConsistency(false).build();
		ResultsResponse resultsResponse = customSchemaClient.results(resultRequest, config);

		assertEquals(resultsResponse.getResult().getCount(), resultsLimit);
		for (SearchEvent event : resultsResponse.getResult().getEvents()) {
			// This should be NOT_VERIFIED
			assertEquals(EventVerification.NOT_VERIFIED, event.getConsistencyVerification());
			assertEquals(EventVerification.NOT_VERIFIED, event.getMembershipVerification());
		}
	}

	@Test
	public void testRoot() throws PangeaException, PangeaAPIException {
		RootResponse response = clientGeneral.getRoot();
		assertTrue(response.isOk());

		RootResult result = response.getResult();
		Root root = result.getRoot();
		assertNotNull(root);
		assertNotNull(root.getSize());
		assertNotNull(root.getTreeName());
		assertNotNull(root.getRootHash());
	}

	@Test
	public void testRootWithSize() throws PangeaException, PangeaAPIException {
		int treeSize = 2;
		RootResponse response = clientGeneral.getRoot(treeSize);
		assertTrue(response.isOk());

		RootResult result = response.getResult();
		Root root = result.getRoot();
		assertNotNull(root);
		assertNotNull(root.getSize());
		assertNotNull(root.getTreeName());
		assertNotNull(root.getRootHash());
		assertEquals(treeSize, root.getSize());
	}

	@Test(expected = PangeaAPIException.class)
	public void testRootTreeNotFound() throws PangeaException, PangeaAPIException {
		int treeSize = 1000000;
		RootResponse response = clientGeneral.getRoot(treeSize);
	}

	@Test(expected = UnauthorizedException.class)
	public void testRootUnauthorized() throws PangeaException, PangeaAPIException, ConfigException {
		int treeSize = 1;
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg = new Config.Builder("notarealtoken", cfg.getDomain()).build();
		AuditClient fakeClient = new AuditClient.Builder(cfg).build();
		RootResponse response = fakeClient.getRoot(treeSize);
	}

	@Test(expected = UnauthorizedException.class)
	public void testLogUnathorized() throws PangeaException, PangeaAPIException, ConfigException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg = new Config.Builder("notarealtoken", cfg.getDomain()).build();
		AuditClient fakeClient = new AuditClient.Builder(cfg).build();
		StandardEvent event = new StandardEvent("Test msg");
		LogResponse response = fakeClient.log(
			event,
			new LogConfig.Builder().verbose(false).signLocal(false).verify(false).build()
		);
	}

	// @Test(expected = ValidationException.class)
	// public void testLogEmptyMessage() throws PangeaException, PangeaAPIException {
	// 	Event event = new Event("");
	// 	LogResponse response = client.log(event);
	// }

	@Test(expected = ValidationException.class)
	public void testSearchValidationException() throws PangeaAPIException, PangeaException {
		SearchRequest request = new SearchRequest.Builder("message:\"\"").order("notavalidorder").build();

		SearchConfig config = new SearchConfig.Builder().build();
		SearchResponse searchResponse = clientGeneral.search(request, config);
	}

	@Test(expected = UnauthorizedException.class)
	public void testSearchValidationException2() throws PangeaAPIException, PangeaException, ConfigException {
		Config cfg = Config.fromIntegrationEnvironment(environment);
		cfg = new Config.Builder("notarealtoken", cfg.getDomain()).build();
		AuditClient fakeClient = new AuditClient.Builder(cfg).build();

		SearchRequest request = new SearchRequest.Builder("message:\"\"").build();
		SearchConfig config = new SearchConfig.Builder().build();
		SearchResponse searchResponse = fakeClient.search(request, config);
	}

	@Test(expected = SignerException.class)
	public void testLogSignerNotSet() throws PangeaException, PangeaAPIException, ConfigException {
		StandardEvent event = new StandardEvent(MSG_NO_SIGNED);
		LogResponse response = clientGeneral.log(
			event,
			new LogConfig.Builder().verbose(true).signLocal(true).verify(true).build()
		);
	}

	@Test
	public void testMultiConfig1Log() throws PangeaException, PangeaAPIException, ConfigException {
		StandardEvent event = new StandardEvent.Builder(MSG_NO_SIGNED).actor(ACTOR).status(STATUS_NO_SIGNED).build();

		Config cfg = new Config.Builder(Config.getMultiConfigTestToken(environment), Config.getTestDomain(environment))
			.build();

		String configID = Config.getConfigID(environment, "audit", 1);

		AuditClient client = new AuditClient.Builder(cfg).withConfigID(configID).build();

		LogResponse response = client.log(
			event,
			new LogConfig.Builder().verbose(false).signLocal(false).verify(false).build()
		);
		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		assertNull(result.getConsistencyProof());
		assertNull(result.getMembershipProof());
		assertEquals(result.getConsistencyVerification(), EventVerification.NOT_VERIFIED);
		assertEquals(result.getMembershipVerification(), EventVerification.NOT_VERIFIED);
		assertEquals(result.getSignatureVerification(), EventVerification.NOT_VERIFIED);
	}

	@Test
	public void testMultiConfig2Log() throws PangeaException, PangeaAPIException, ConfigException {
		StandardEvent event = new StandardEvent.Builder(MSG_NO_SIGNED).actor(ACTOR).status(STATUS_NO_SIGNED).build();

		Config cfg = new Config.Builder(Config.getMultiConfigTestToken(environment), Config.getTestDomain(environment))
			.build();

		String configID = Config.getConfigID(environment, "audit", 2);

		AuditClient client = new AuditClient.Builder(cfg).withConfigID(configID).build();

		LogResponse response = client.log(
			event,
			new LogConfig.Builder().verbose(false).signLocal(false).verify(false).build()
		);
		assertTrue(response.isOk());

		LogResult result = response.getResult();
		assertNull(result.getEventEnvelope());
		assertNotNull(result.getHash());
		assertNull(result.getConsistencyProof());
		assertNull(result.getMembershipProof());
		assertEquals(result.getConsistencyVerification(), EventVerification.NOT_VERIFIED);
		assertEquals(result.getMembershipVerification(), EventVerification.NOT_VERIFIED);
		assertEquals(result.getSignatureVerification(), EventVerification.NOT_VERIFIED);
	}

	@Test(expected = PangeaAPIException.class)
	public void testMultiConfigWithoutConfigID() throws PangeaException, PangeaAPIException, ConfigException {
		StandardEvent event = new StandardEvent.Builder(MSG_NO_SIGNED).actor(ACTOR).status(STATUS_NO_SIGNED).build();

		Config cfg = new Config.Builder(Config.getMultiConfigTestToken(environment), Config.getTestDomain(environment))
			.build();
		AuditClient client = new AuditClient.Builder(cfg).build();

		LogResponse response = client.log(
			event,
			new LogConfig.Builder().verbose(true).signLocal(false).verify(true).build()
		);
	}

	@Test
	public void testLogBulk() throws PangeaAPIException, PangeaException {
		StandardEvent event = new StandardEvent(MSG_NO_SIGNED);
		event.setActor(ACTOR);
		event.setStatus(STATUS_NO_SIGNED);

		IEvent[] events = { event, event };

		try {
			LogBulkResponse response = clientGeneral.logBulk(
				events,
				new LogConfig.Builder().verbose(true).signLocal(false).verify(false).build()
			);
			assertTrue(response.isOk());

			LogBulkResult bulkResult = response.getResult();
			for (LogResult result : bulkResult.getResults()) {
				assertNotNull(result.getEventEnvelope());
				assertNotNull(result.getHash());
				StandardEvent eventResult = (StandardEvent) result.getEventEnvelope().getEvent();
				assertEquals(MSG_NO_SIGNED, eventResult.getMessage());
				assertNull(result.getConsistencyProof());
				assertEquals(EventVerification.NOT_VERIFIED, result.getConsistencyVerification());
				assertEquals(EventVerification.NOT_VERIFIED, result.getMembershipVerification());
				assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());
			}
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
		}
	}

	@Test
	public void testLogBulkAndSign() throws PangeaAPIException, PangeaException {
		StandardEvent event = new StandardEvent(MSG_NO_SIGNED);
		event.setActor(ACTOR);
		event.setStatus(STATUS_NO_SIGNED);

		IEvent[] events = { event, event };

		try {
			LogBulkResponse response = localSignClient.logBulk(
				events,
				new LogConfig.Builder().verbose(true).signLocal(true).build()
			);
			assertTrue(response.isOk());

			LogBulkResult bulkResult = response.getResult();
			for (LogResult result : bulkResult.getResults()) {
				assertNotNull(result.getEventEnvelope());
				assertNotNull(result.getHash());
				StandardEvent eventResult = (StandardEvent) result.getEventEnvelope().getEvent();
				assertEquals(MSG_NO_SIGNED, eventResult.getMessage());
				assertNull(result.getConsistencyProof());
				assertEquals(EventVerification.NOT_VERIFIED, result.getConsistencyVerification());
				assertEquals(EventVerification.NOT_VERIFIED, result.getMembershipVerification());
				assertEquals(EventVerification.SUCCESS, result.getSignatureVerification());
			}
		} catch (PangeaAPIException e) {
			System.out.println(e.toString());
		}
	}

	public void testLogBulkAsync() throws PangeaAPIException, PangeaException {
		StandardEvent event = new StandardEvent(MSG_NO_SIGNED);
		event.setActor(ACTOR);
		event.setStatus(STATUS_NO_SIGNED);

		IEvent[] events = { event, event };

		LogBulkResponse response = clientGeneral.logBulkAsync(
			events,
			new LogConfig.Builder().verbose(true).signLocal(false).verify(false).build()
		);

		assertTrue(response.isOk());
		assertNull(response.getResult());
		assertNotNull(response.getAcceptedResult());
		assertNotNull(response.getRequestId());
		assertNotNull(response.getSummary());
		assertNotNull(response.getStatus());
		assertNotNull(response.getResponseTime());
		assertNotNull(response.getRequestTime());
	}

	@Test
	public void testDownloadSearch() throws PangeaAPIException, PangeaException {
		int maxResults = 10;
		SearchRequest request = new SearchRequest.Builder("message:sign-test-local")
			.maxResults(maxResults)
			.start("21d")
			.build();

		SearchConfig config = new SearchConfig.Builder().build();

		SearchResponse response = clientGeneral.search(request, config);
		assertTrue(response.isOk());
		assertNotNull(response.getResult().getId());
		assertTrue(response.getResult().getCount() <= maxResults);

		var downloadResp = clientGeneral.downloadResults(
			new DownloadRequest.Builder(response.getResult().getId()).format(DownloadFormat.CSV).build()
		);
		assertTrue(downloadResp.isOk());
		assertNotNull(downloadResp.getResult().getDestURL());

		AttachedFile file = clientGeneral.downloadFile(downloadResp.getResult().getDestURL());
		file.save(Path.of("./", file.getFilename()));
	}

	@Test
	public void testLogStream() throws ConfigException, PangeaAPIException, PangeaException {
		// Sample data.
		final var logStreamEventData = new LogStreamEventData();
		logStreamEventData.setClientID("test client ID");
		logStreamEventData.setDate(Instant.now());
		logStreamEventData.setDescription("Create a log stream");
		logStreamEventData.setIP("127.0.0.1");
		logStreamEventData.setType("some_type");
		logStreamEventData.setUserAgent("AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0");
		logStreamEventData.setUserID("test user ID");
		logStreamEventData.setConnection("Username-Password-Authentication");
		logStreamEventData.setConnectionID("some ID");
		logStreamEventData.setStrategy("auth0");
		logStreamEventData.setStrategyType("database");

		final var logStreamEvent = new LogStreamEvent();
		logStreamEvent.setLogID("some log ID");
		logStreamEvent.setData(logStreamEventData);

		final var input = new LogStreamRequest();
		input.setLogs(List.of(logStreamEvent));

		// Client setup.
		final var config = new Config.Builder(
			Config.getMultiConfigTestToken(environment),
			Config.getTestDomain(environment)
		)
			.build();
		final var configId = Config.getConfigID(environment, "audit", 3);
		final var client = new AuditClient.Builder(config).withConfigID(configId).build();

		// Test log stream.
		final var response = client.logStream(input);
		assertEquals(ResponseStatus.SUCCESS.toString(), response.getStatus());
	}

	@Test
	public void testExportDownload() throws PangeaAPIException, PangeaException {
		var exportResponse = clientGeneral.export(ExportRequest.builder().end(Instant.now()).verbose(true).build());
		assertEquals(ResponseStatus.ACCEPTED.toString(), exportResponse.getStatus());

		// Note that the export can easily take dozens of minutes, if not
		// longer, so we don't actually wait for the results on CI. Instead we
		// just poll it once and then attempt the download, even when we know it
		// isn't ready yet, just to verify that the core of the functions are
		// working.
		try {
			clientGeneral.pollResult(exportResponse.getRequestId(), new TypeReference<Response<Void>>() {});
		} catch (PangeaAPIException error) {
			assertEquals(ResponseStatus.ACCEPTED.toString(), error.getResponse().getStatus());
		}

		try {
			var downloadRequest = new DownloadRequest.Builder().requestId(exportResponse.getRequestId()).build();
			clientGeneral.downloadResults(downloadRequest);
		} catch (PangeaAPIException error) {
			var status = error.getResponse().getStatus();

			// NOT_FOUND is possible if this test runs while an export is
			// already in progress, since the request ID from the current run
			// does not match the original request ID that started the export in
			// a previous run.
			if (
				status.equalsIgnoreCase(ResponseStatus.ACCEPTED.toString()) &&
				status.equalsIgnoreCase(ResponseStatus.NOT_FOUND.toString())
			) {
				throw error;
			}
		} catch (Exception error) {
			throw error;
		}
	}
}
