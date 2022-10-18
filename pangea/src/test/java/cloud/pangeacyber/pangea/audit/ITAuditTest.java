package cloud.pangeacyber.pangea.audit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.bouncycastle.crypto.CryptoException;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

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
        client = new AuditClient(Config.fromEnvironment(AuditClient.serviceName));
    }

    @Test
    public void testLog() throws IOException, InterruptedException, PangeaAPIException, CryptoException, JsonProcessingException, Exception {
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
    public void testLogNoVerbose() throws IOException, InterruptedException, PangeaAPIException, CryptoException, JsonProcessingException, Exception {
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
    public void testLogVerbose() throws IOException, InterruptedException, PangeaAPIException, CryptoException, JsonProcessingException, Exception {
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
    public void testLogSignature() throws IOException, InterruptedException, PangeaAPIException, CryptoException, JsonProcessingException, ConfigException, Exception{
        AuditClient signClient = new AuditClient(Config.fromEnvironment(AuditClient.serviceName), "./src/test/java/cloud/pangeacyber/pangea/testdata/privkey");

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
        // FIXME: Signature is not working yet
        // assertEquals("dg7Wg+E8QzZzhECzQoH3v3pbjWObR8ve7SHREAyA9JlFOusKPHVb16t5D3rbscnv80ry/aWzfMTscRNSYJFzDA==", result.getEventEnvelope().getSignature());
    }

    @Test(expected = ValidationException.class)
    public void testEmptyMessage() throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        Event event = new Event("");
        LogResponse response = client.log(event);
    }

    @Test
    public void testSearchDefault() throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        SearchInput input = new SearchInput("message:Integration test msg");
        int limit = 10;
        input.setMaxResults(limit);
        input.setOrder("asc");

        SearchResponse response = client.search(input);
        assertTrue(response.isOk());
        assertTrue(response.getResult().getCount() <= limit);

        for(SearchEvent event: response.getResult().getEvents()){
            assertEquals(event.getConsistencyVerification(), EventVerification.SUCCESS);
            assertEquals(event.getMembershipVerification(), EventVerification.SUCCESS);
        }
    }

    @Test
    public void testSearchNoVerify() throws IOException, InterruptedException, PangeaAPIException, PangeaException {
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
        }
    }

    @Test
    public void testSearchVerify() throws IOException, InterruptedException, PangeaAPIException, PangeaException {
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
    public void testRoot() throws IOException, InterruptedException, PangeaException, PangeaAPIException {
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
    public void testRootWithSize() throws IOException, InterruptedException, PangeaException, PangeaAPIException {
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
