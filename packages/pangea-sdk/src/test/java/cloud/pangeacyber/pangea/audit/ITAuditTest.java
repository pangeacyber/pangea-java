package cloud.pangeacyber.pangea.audit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.SignerException;
import cloud.pangeacyber.pangea.exceptions.UnauthorizedException;
import cloud.pangeacyber.pangea.exceptions.ValidationException;


public class ITAuditTest{
    AuditClient client, signClient;

    private static final String ACTOR = "java-sdk";
    private static final String MSG_NO_SIGNED = "test-message";
    private static final String MSG_SIGNED_LOCAL = "sign-test-local";
    private static final String MSG_SIGNED_VAULT = "sign-test-vault";
    private static final String STATUS_NO_SIGNED = "no-signed";
    private static final String STATUS_SIGNED = "signed";

    @Before
    public void setUp() throws ConfigException{
        Config cfg = Config.fromIntegrationEnvironment();
        cfg.setEnviroment("local");
        cfg.setDomain("audit-gea-1771-audit-vault.dev.aws.pangea.cloud");
        cfg.setToken("pts_orck2dofow5xmxxvlk4q3dbjnvvoc5nm");
        client = new AuditClient(cfg);
        signClient = new AuditClient(cfg, "./src/test/java/cloud/pangeacyber/pangea/testdata/privkey");
    }

    @Test
    public void testLog() throws PangeaException, PangeaAPIException{
        Event event = new Event(MSG_NO_SIGNED);
        event.setActor(ACTOR);
        event.setStatus(STATUS_NO_SIGNED);

        LogResponse response = client.log(event);
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
    public void testLogNoVerbose() throws PangeaException, PangeaAPIException{
        Event event = new Event(MSG_NO_SIGNED);
        event.setActor(ACTOR);
        event.setStatus(STATUS_NO_SIGNED);

        LogResponse response = client.log(event, SignMode.UNSIGNED, false, false);
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
    public void testLogVerbose() throws PangeaAPIException, PangeaException{
        Event event = new Event(MSG_NO_SIGNED);
        event.setActor(ACTOR);
        event.setStatus(STATUS_NO_SIGNED);

        LogResponse response = client.log(event, SignMode.UNSIGNED, true, false);
        assertTrue(response.isOk());

        LogResult result = response.getResult();
        assertNotNull(result.getEventEnvelope());
        assertNotNull(result.getHash());
        assertEquals(MSG_NO_SIGNED, result.getEventEnvelope().getEvent().getMessage());
        assertNull(result.getConsistencyProof());
        assertNotNull(result.getMembershipProof());
        assertEquals(EventVerification.NOT_VERIFIED, result.getConsistencyVerification());
        assertEquals(EventVerification.NOT_VERIFIED, result.getMembershipVerification());
        assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());
    }

    @Test
    public void testLogVerify() throws PangeaAPIException, PangeaException{
        Event event = new Event(MSG_NO_SIGNED);
        event.setActor(ACTOR);
        event.setStatus(STATUS_NO_SIGNED);

        LogResponse response = client.log(event, SignMode.UNSIGNED, true, true);
        assertTrue(response.isOk());

        LogResult result = response.getResult();
        assertNotNull(result.getEventEnvelope());
        assertNotNull(result.getHash());
        assertEquals(MSG_NO_SIGNED, result.getEventEnvelope().getEvent().getMessage());
        assertEquals(EventVerification.NOT_VERIFIED, result.getConsistencyVerification());
        assertEquals(EventVerification.SUCCESS, result.getMembershipVerification());
        assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());

        // Second log
        event = new Event(MSG_NO_SIGNED);
        event.setActor(ACTOR);
        event.setStatus(STATUS_NO_SIGNED);
        response = client.log(event, SignMode.UNSIGNED, true, true);
        assertTrue(response.isOk());

        result = response.getResult();
        assertNotNull(result.getEventEnvelope());
        assertNotNull(result.getHash());
        assertEquals(MSG_NO_SIGNED, result.getEventEnvelope().getEvent().getMessage());
        assertEquals(EventVerification.SUCCESS, result.getConsistencyVerification());
        assertEquals(EventVerification.SUCCESS, result.getMembershipVerification());
        assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());
    }

    @Test
    public void testLogLocalSignature() throws PangeaException, PangeaAPIException, ConfigException{
        Event event = new Event(MSG_SIGNED_LOCAL);
        event.setActor(ACTOR);
        event.setAction("Action");
        event.setSource("Source");
        event.setStatus(STATUS_SIGNED);
        event.setTarget("Target");
        event.setNewField("New");
        event.setOld("Old");

        LogResponse response = signClient.log(event, SignMode.LOCAL, true, true);
        assertTrue(response.isOk());

        LogResult result = response.getResult();
        assertNotNull(result.getEventEnvelope());
        assertNotNull(result.getHash());
        assertEquals(MSG_SIGNED_LOCAL, result.getEventEnvelope().getEvent().getMessage());
        assertEquals("lvOyDMpK2DQ16NI8G41yINl01wMHzINBahtDPoh4+mE=", result.getEventEnvelope().getPublicKey());
        assertEquals(EventVerification.SUCCESS, result.getSignatureVerification());
    }

    @Test
    public void testLogVaultSignature() throws PangeaException, PangeaAPIException, ConfigException{
        Event event = new Event(MSG_SIGNED_VAULT);
        event.setActor(ACTOR);
        event.setAction("Action");
        event.setSource("Source");
        event.setStatus(STATUS_SIGNED);
        event.setTarget("Target");
        event.setNewField("New");
        event.setOld("Old");

        LogResponse response = signClient.log(event, SignMode.VAULT, true, true);
        assertTrue(response.isOk());

        LogResult result = response.getResult();
        assertNotNull(result.getEventEnvelope());
        assertNotNull(result.getHash());
        assertEquals(MSG_SIGNED_VAULT, result.getEventEnvelope().getEvent().getMessage());
        assertNotNull(result.getEventEnvelope().getPublicKey());
        assertNotNull(result.getEventEnvelope().getSignature());
        assertNotNull(result.getEventEnvelope().getSignatureKeyID());
        assertNotNull(result.getEventEnvelope().getSignatureKeyVersion());
        assertEquals(EventVerification.NOT_VERIFIED, result.getSignatureVerification());
    }


    @Test(expected = ValidationException.class)
    public void testEmptyMessage() throws PangeaException, PangeaAPIException {
        Event event = new Event("");
        LogResponse response = client.log(event);
    }

    @Test
    public void testSearchDefault() throws PangeaException, PangeaAPIException {
        SearchInput input = new SearchInput("message:");
        int limit = 4;
        int maxResults = 6;
        input.setMaxResults(limit);
        input.setMaxResults(maxResults);
        input.setOrder("asc");

        SearchResponse response = client.search(input);
        assertTrue(response.isOk());
        assertTrue(response.getResult().getCount() <= maxResults);

        for(SearchEvent event: response.getResult().getEvents()){
            assertEquals(EventVerification.SUCCESS, event.getConsistencyVerification());     // This could be NOT_VERIFIED or SUCCESS is they have data or not
            assertEquals(EventVerification.SUCCESS, event.getMembershipVerification());
            assertNotNull(event.getEventEnvelope());
            assertNotNull(event.getHash());
        }
    }

    @Test
    public void testSearchNoVerify() throws PangeaAPIException, PangeaException {
        SearchInput input = new SearchInput("message:Integration test msg");
        int limit = 10;
        input.setMaxResults(limit);
        input.setOrder("desc");

        SearchResponse response = client.search(input, false, false);
        assertTrue(response.isOk());
        assertTrue(response.getResult().getCount() <= limit);

        for(SearchEvent event: response.getResult().getEvents()){
            assertEquals(EventVerification.NOT_VERIFIED, event.getConsistencyVerification());
            assertEquals(EventVerification.NOT_VERIFIED, event.getMembershipVerification());
            assertEquals(EventVerification.NOT_VERIFIED, event.getSignatureVerification());
        }
    }

    @Test
    public void testSearchVerifyConsistency() throws PangeaAPIException, PangeaException {
        SearchInput input = new SearchInput("message:");
        int limit = 10;
        input.setMaxResults(limit);
        input.setOrder("asc");

        SearchResponse response = client.search(input, true, true);
        assertTrue(response.isOk());
        assertTrue(response.getResult().getCount() <= limit);

        for(SearchEvent event: response.getResult().getEvents()){
            assertEquals(EventVerification.SUCCESS, event.getConsistencyVerification());
            assertEquals(EventVerification.SUCCESS, event.getMembershipVerification());
        }
    }

    @Test
    public void testSearchVerifySignature() throws PangeaAPIException, PangeaException {
        SearchInput input = new SearchInput("message:" + MSG_SIGNED_LOCAL + " status:" + STATUS_SIGNED);
        int limit = 10;
        input.setMaxResults(limit);
        input.setOrder("asc");

        SearchResponse response = client.search(input, true, true);
        assertTrue(response.isOk());
        assertTrue(response.getResult().getCount() <= limit);

        for(SearchEvent event: response.getResult().getEvents()){
            assertEquals(EventVerification.SUCCESS, event.getSignatureVerification());
        }
    }

    @Test
    public void testResultsDefault() throws PangeaAPIException, PangeaException {
        SearchInput input = new SearchInput("message:");
        int searchLimit = 10;
        input.setMaxResults(searchLimit);
        input.setOrder("asc");

        SearchResponse searchResponse = client.search(input, true, true);
        assertTrue(searchResponse.isOk());
        assertTrue(searchResponse.getResult().getCount() <= searchLimit);
        assertTrue(searchResponse.getResult().getCount() > 0);

        int resultsLimit = 3;
        ResultsResponse resultsResponse = client.results(searchResponse.getResult().getId(), resultsLimit, 0);
        assertEquals(resultsResponse.getResult().getCount(), resultsLimit);
        for(SearchEvent event: resultsResponse.getResult().getEvents()){
            assertEquals(EventVerification.NOT_VERIFIED, event.getConsistencyVerification());
            assertEquals(EventVerification.NOT_VERIFIED, event.getMembershipVerification());
        }
    }

    public void testResultsVerify() throws PangeaAPIException, PangeaException {
        SearchInput input = new SearchInput("message:");
        int searchLimit = 10;
        input.setMaxResults(searchLimit);
        input.setOrder("asc");

        SearchResponse searchResponse = client.search(input, true, true);
        assertTrue(searchResponse.isOk());
        assertTrue(searchResponse.getResult().getCount() <= searchLimit);
        assertTrue(searchResponse.getResult().getCount() > 0);

        int resultsLimit = 3;
        ResultsResponse resultsResponse = client.results(searchResponse.getResult().getId(), resultsLimit, 0, true, true);
        assertEquals(resultsResponse.getResult().getCount(), resultsLimit);
        for(SearchEvent event: resultsResponse.getResult().getEvents()){
            assertEquals(EventVerification.SUCCESS, event.getConsistencyVerification());
            assertEquals(EventVerification.SUCCESS, event.getMembershipVerification());
        }
    }

    @Test
    public void testResultsNoVerify() throws PangeaAPIException, PangeaException{
        SearchInput input = new SearchInput("message:");
        int searchLimit = 10;
        input.setMaxResults(searchLimit);
        input.setOrder("asc");

        SearchResponse searchResponse = client.search(input, true, true);
        assertTrue(searchResponse.isOk());
        assertTrue(searchResponse.getResult().getCount() <= searchLimit);

        int resultsLimit = 3;
        // Skip verifications
        ResultsResponse resultsResponse = client.results(searchResponse.getResult().getId(), resultsLimit, 0, false, false);
        assertEquals(resultsResponse.getResult().getCount(), resultsLimit);
        for(SearchEvent event: resultsResponse.getResult().getEvents()){
            // This should be NOT_VERIFIED
            assertEquals(EventVerification.NOT_VERIFIED, event.getConsistencyVerification());
            assertEquals(EventVerification.NOT_VERIFIED, event.getMembershipVerification());
        }
    }

    @Test
    public void testRoot() throws PangeaException, PangeaAPIException {
        RootResponse response = client.getRoot();
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
        RootResponse response = client.getRoot(treeSize);
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
        RootResponse response = client.getRoot(treeSize);
    }

    @Test(expected = UnauthorizedException.class)
    public void testRootUnauthorized() throws PangeaException, PangeaAPIException, ConfigException{
        int treeSize = 1;
        Config cfg = Config.fromIntegrationEnvironment();
        cfg.setToken("notarealtoken");
        AuditClient fakeClient = new AuditClient(cfg);
        RootResponse response = fakeClient.getRoot(treeSize);
    }

    @Test(expected = UnauthorizedException.class)
    public void testLogUnathorized() throws PangeaException, PangeaAPIException, ConfigException{
        Config cfg = Config.fromIntegrationEnvironment();
        cfg.setToken("notarealtoken");
        AuditClient fakeClient = new AuditClient(cfg);
        Event event = new Event("Test msg");
        LogResponse response = fakeClient.log(event);
    }

    @Test(expected = ValidationException.class)
    public void testLogEmptyMessage() throws PangeaException, PangeaAPIException{
        Event event = new Event("");
        LogResponse response = client.log(event);
    }

    @Test(expected = ValidationException.class)
    public void testSearchValidationException() throws PangeaAPIException, PangeaException {
        SearchInput input = new SearchInput("message:");
        int searchLimit = 100;
        input.setMaxResults(searchLimit);
        input.setOrder("notavalidorder");
        SearchResponse searchResponse = client.search(input, true, true);

    }

    @Test(expected = UnauthorizedException.class)
    public void testSearchValidationException2() throws PangeaAPIException, PangeaException, ConfigException {
        Config cfg = Config.fromIntegrationEnvironment();
        cfg.setToken("notarealtoken");
        AuditClient fakeClient = new AuditClient(cfg);
        SearchInput input = new SearchInput("message:");
        int searchLimit = 100;
        input.setMaxResults(searchLimit);
        input.setOrder("notavalidorder");
        SearchResponse searchResponse = fakeClient.search(input, true, true);
    }

    @Test(expected = SignerException.class)
    public void testLogSignerNotSet() throws PangeaException, PangeaAPIException, ConfigException{
        Event event = new Event(MSG_NO_SIGNED);
        LogResponse response = client.log(event, SignMode.LOCAL, true, true);
    }

}
