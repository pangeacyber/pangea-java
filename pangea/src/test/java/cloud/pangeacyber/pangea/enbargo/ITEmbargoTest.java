package cloud.pangeacyber.pangea.enbargo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.embargo.EmbargoClient;
import cloud.pangeacyber.pangea.embargo.EmbargoSanction;
import cloud.pangeacyber.pangea.embargo.EmbargoSanctions;


public class ITEmbargoTest 
{
    EmbargoClient client;

    @Before
    public void setUp() {
        client = new EmbargoClient(Config.fromEnvironment(EmbargoClient.serviceName));
    }

    @Test
    public void testIsoCheckSanctionedCountry() throws IOException, InterruptedException {
        Response<EmbargoSanctions> response;
        response = client.isoCheck("CU");

        assertTrue(response.isOk());

        EmbargoSanctions result = response.getResult();
        assertTrue(result.getCount() > 0);

        EmbargoSanction sanction = result.getSanctions().elementAt(0);
        assertEquals("Cuba", sanction.getEmbargoedCountryName());
    }

    @Test
    public void testIsoCheckNoSanctionedCountry() throws IOException, InterruptedException {
        Response<EmbargoSanctions> response;
        response = client.isoCheck("AR");

        assertTrue(response.isOk());

        EmbargoSanctions result = response.getResult();
        assertTrue(result.getCount() == 0);
    }

}
