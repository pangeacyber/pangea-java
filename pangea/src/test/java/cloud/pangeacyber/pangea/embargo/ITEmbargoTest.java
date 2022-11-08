package cloud.pangeacyber.pangea.embargo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.ConfigException;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.exceptions.UnauthorizedException;
import cloud.pangeacyber.pangea.exceptions.ValidationException;


public class ITEmbargoTest
{
    EmbargoClient client;

    @Before
    public void setUp() throws ConfigException{
        client = new EmbargoClient(Config.fromIntegrationEnvironment(EmbargoClient.serviceName));
    }

    @Test
    public void testIsoCheckSanctionedCountry() throws PangeaException, PangeaException, PangeaAPIException {
        IsoCheckResponse response = client.isoCheck("CU");
        assertTrue(response.isOk());

        EmbargoSanctions result = response.getResult();
        assertTrue(result.getCount() > 0);

        EmbargoSanction sanction = result.getSanctions().elementAt(0);
        assertEquals("Cuba", sanction.getEmbargoedCountryName());
    }

    @Test
    public void testIsoCheckNoSanctionedCountry() throws PangeaException, PangeaAPIException{
        IsoCheckResponse response = client.isoCheck("AR");
        assertTrue(response.isOk());

        EmbargoSanctions result = response.getResult();
        assertTrue(result.getCount() == 0);
    }

    @Test
    public void testIpCheckSanctionedCountry() throws PangeaException, PangeaAPIException{
        IpCheckResponse response = client.ipCheck("213.24.238.26");
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
    public void testEmptyIP() throws PangeaException, PangeaAPIException {
        IpCheckResponse response = client.ipCheck("");
    }

    @Test(expected = UnauthorizedException.class)
    public void testUnauthorized() throws PangeaException, PangeaAPIException, ConfigException{
        Config cfg = Config.fromIntegrationEnvironment(EmbargoClient.serviceName);
        cfg.setToken("notarealtoken");
        EmbargoClient fakeClient = new EmbargoClient(cfg);
        IpCheckResponse response = fakeClient.ipCheck("1.1.1.1");
    }

}
