package cloud.pangeacyber.pangea.embargo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.exceptions.ConfigException;


public class ITEmbargoTest 
{
    EmbargoClient client;

    @Before
    public void setUp() {
        Config config = null;
        try{
            config = Config.fromEnvironment(EmbargoClient.serviceName);    
        } catch(ConfigException e){
            System.out.println("Exception: " + e.toString());
            assertTrue(false);
        }
        client = new EmbargoClient(config);
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
        IsoCheckResponse response;
        response = client.isoCheck("AR");

        assertTrue(response.isOk());

        EmbargoSanctions result = response.getResult();
        assertTrue(result.getCount() == 0);
    }

    @Test
    public void testIpCheckSanctionedCountry() throws IOException, InterruptedException {
        IpCheckResponse response;
        response = client.ipCheck("213.24.238.26");

        assertTrue(response.isOk());

        EmbargoSanctions result = response.getResult();
        assertTrue(result.getCount() > 0);

        EmbargoSanction sanction = result.getSanctions().elementAt(0);
        assertEquals("Russia", sanction.getEmbargoedCountryName());
        assertEquals("RU", sanction.getEmbargoedCountryISOCode());
        assertEquals("US", sanction.getIssuingCountry());
        assertEquals("ITAR", sanction.getListName());
    }

}
