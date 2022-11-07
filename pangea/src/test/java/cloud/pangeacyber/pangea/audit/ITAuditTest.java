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
import cloud.pangeacyber.pangea.exceptions.ValidationException;


public class ITAuditTest
{
    AuditClient client;

    @Before
    public void setUp() throws ConfigException{
        client = new AuditClient(Config.fromIntegrationEnvironment(AuditClient.serviceName));
    }

    @Test
    public void testLog() throws PangeaException, PangeaAPIException{
        String msg = "Integration test msg";
        Event event = new Event(msg);

        LogResponse response = client.log(event);
        assertTrue(response.isOk());

        LogOutput result = response.getResult();
        assertNull(result.getEventEnvelope());
        assertNull(result.getCanonicalEnvelopeBase64());
        assertNotNull(result.getHash());
    }

    @Test
    public void testLogNoVerbose() throws PangeaException, PangeaAPIException{
        String msg = "Integration test msg";
        Event event = new Event(msg);

        LogResponse response = client.log(event, false, false);
        assertTrue(response.isOk());

        LogOutput result = response.getResult();
        assertNull(result.getEventEnvelope());
        assertNull(result.getCanonicalEnvelopeBase64());
        assertNotNull(result.getHash());
    }

    @Test
    public void testLogVerbose() throws PangeaAPIException, PangeaException{
        String msg = "Integration test msg";
        Event event = new Event(msg);

        LogResponse response = client.log(event, false, true);
        assertTrue(response.isOk());

        LogOutput result = response.getResult();
        assertNotNull(result.getEventEnvelope());
        assertNotNull(result.getCanonicalEnvelopeBase64());
        assertNotNull(result.getHash());
        assertEquals(msg, result.getEventEnvelope().getEvent().getMessage());
    }

    @Test
    public void testLogSignature() throws PangeaException, PangeaAPIException, ConfigException{
        AuditClient signClient = new AuditClient(Config.fromIntegrationEnvironment(AuditClient.serviceName), "./src/test/java/cloud/pangeacyber/pangea/testdata/privkey");

        String msg = "sigtest100";
        Event event = new Event(msg);
        event.setActor("Actor");
        event.setAction("Action");
        event.setSource("Source");
        event.setStatus("Status");
        event.setTarget("Target");
        event.setNewField("New");
        event.setOld("Old");

        LogResponse response = signClient.log(event, true, true);
        assertTrue(response.isOk());

        LogOutput result = response.getResult();
        assertNotNull(result.getEventEnvelope());
        assertNotNull(result.getCanonicalEnvelopeBase64());
        assertNotNull(result.getHash());
        assertEquals(msg, result.getEventEnvelope().getEvent().getMessage());
        assertEquals("lvOyDMpK2DQ16NI8G41yINl01wMHzINBahtDPoh4+mE=", result.getEventEnvelope().getPublicKey());
        assertEquals("dg7Wg+E8QzZzhECzQoH3v3pbjWObR8ve7SHREAyA9JlFOusKPHVb16t5D3rbscnv80ry/aWzfMTscRNSYJFzDA==", result.getEventEnvelope().getSignature());

    }

    @Test(expected = ValidationException.class)
    public void testEmptyMessage() throws PangeaException, PangeaAPIException {
        Event event = new Event("");
        LogResponse response = client.log(event);
    }

    @Test
    public void testSearchDefault() throws PangeaException, PangeaAPIException {
        SearchInput input = new SearchInput("message:sigtest100");
        int limit = 4;
        int maxResults = 6;
        input.setMaxResults(limit);
        input.setMaxResults(maxResults);
        input.setOrder("desc");

        SearchResponse response = client.search(input);
        assertTrue(response.isOk());
        assertTrue(response.getResult().getCount() <= maxResults);

        for(SearchEvent event: response.getResult().getEvents()){
            assertTrue(event.getConsistencyVerification() != EventVerification.FAILED);     // This could be NOT_VERIFIED or SUCCESS is they have data or not
            assertTrue(event.getMembershipVerification() != EventVerification.FAILED);
            assertTrue(event.getSignatureVerification() != EventVerification.FAILED);
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
    public void testSearchVerify() throws PangeaAPIException, PangeaException {
        SearchInput input = new SearchInput("message:Integration test msg");
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
    public void testResultsDefault() throws PangeaAPIException, PangeaException {
        SearchInput input = new SearchInput("message:");
        int searchLimit = 100;
        input.setMaxResults(searchLimit);
        input.setOrder("desc");

        SearchResponse searchResponse = client.search(input, true, true);
        assertTrue(searchResponse.isOk());
        assertTrue(searchResponse.getResult().getCount() <= searchLimit);

        int resultsLimit = 10;
        ResultsResponse resultsResponse = client.results(searchResponse.getRequestId(), resultsLimit, 0);
        assertTrue(resultsResponse.getResult().getCount() <= resultsLimit);
        for(SearchEvent event: resultsResponse.getResult().getEvents()){
            assertEquals(EventVerification.NOT_VERIFIED, event.getConsistencyVerification());
            assertTrue(event.getMembershipVerification() != EventVerification.FAILED);
            assertTrue(event.getSignatureVerification() != EventVerification.FAILED);       //By default verify signatures. If does not have signature is NOT_VERIFIED
        }
    }

    @Test
    public void testResultsNoVerify() throws PangeaAPIException, PangeaException {
        SearchInput input = new SearchInput("message:");
        int searchLimit = 100;
        input.setMaxResults(searchLimit);
        input.setOrder("desc");

        SearchResponse searchResponse = client.search(input, true, true);
        assertTrue(searchResponse.isOk());
        assertTrue(searchResponse.getResult().getCount() <= searchLimit);

        int resultsLimit = 10;
        // Skip verifications
        ResultsResponse resultsResponse = client.results(searchResponse.getRequestId(), resultsLimit, 0, false, false);
        assertTrue(resultsResponse.getResult().getCount() <= resultsLimit);
        for(SearchEvent event: resultsResponse.getResult().getEvents()){
            // This should be NOT_VERIFIED
            assertEquals(EventVerification.NOT_VERIFIED, event.getConsistencyVerification());
            assertEquals(EventVerification.NOT_VERIFIED, event.getMembershipVerification());
            assertEquals(EventVerification.NOT_VERIFIED, event.getSignatureVerification());
        }
    }


    @Test
    public void testRoot() throws PangeaException, PangeaAPIException {
        RootResponse response = client.getRoot();
        assertTrue(response.isOk());

        RootOutput result = response.getResult();
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

        RootOutput result = response.getResult();
        Root root = result.getRoot();
        assertNotNull(root);
        assertNotNull(root.getSize());
        assertNotNull(root.getTreeName());
        assertNotNull(root.getRootHash());
        assertEquals(treeSize, root.getSize());
    }

}
