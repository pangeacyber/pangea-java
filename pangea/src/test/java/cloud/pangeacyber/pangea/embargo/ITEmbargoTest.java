package cloud.pangeacyber.pangea.embargo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.ValidationException;


public class ITEmbargoTest 
{
    EmbargoClient client;

    @Before
    public void setUp() throws ConfigException{
        client = new EmbargoClient(Config.fromEnvironment(EmbargoClient.serviceName));
    }

    @Test
    public void testIsoCheckSanctionedCountry() throws IOException, InterruptedException, PangeaException, PangeaException, PangeaAPIException {
        Response<EmbargoSanctions> response;
        response = client.isoCheck("CU");

        assertTrue(response.isOk());

        EmbargoSanctions result = response.getResult();
        assertTrue(result.getCount() > 0);

        EmbargoSanction sanction = result.getSanctions().elementAt(0);
        assertEquals("Cuba", sanction.getEmbargoedCountryName());
    }

    @Test
    public void testIsoCheckNoSanctionedCountry() throws IOException, InterruptedException, PangeaException, PangeaAPIException{
        IsoCheckResponse response;
        response = client.isoCheck("AR");

        assertTrue(response.isOk());

        EmbargoSanctions result = response.getResult();
        assertTrue(result.getCount() == 0);
    }

    @Test
    public void testIpCheckSanctionedCountry() throws IOException, InterruptedException, PangeaException, PangeaAPIException{
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

    @Test(expected = ValidationException.class)
    public void testEmptyIP() throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        IpCheckResponse response = client.ipCheck("");
    }

}
