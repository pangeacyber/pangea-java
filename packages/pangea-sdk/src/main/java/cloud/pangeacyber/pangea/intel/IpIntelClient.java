package cloud.pangeacyber.pangea.intel;
import cloud.pangeacyber.pangea.Client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;


final class IpLookupRequest {
    @JsonProperty("ip")
    String Ip;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("provider")
    String provider;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("verbose")
    Boolean verbose;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("raw")
    Boolean raw;

    IpLookupRequest(String ip, String provider, Boolean verbose, Boolean raw){
        this.Ip = ip;
        this.provider = provider;
        this.verbose = verbose;
        this.raw = raw;
    }

}

public class IpIntelClient extends Client{
    public static String serviceName = "ip-intel";

    public IpIntelClient(Config config) {
        super(config, serviceName);
    }

    private IpLookupResponse lookupPost(String ip, String provider, Boolean verbose, Boolean raw) throws PangeaException, PangeaAPIException {
        IpLookupRequest request = new IpLookupRequest(ip, provider, verbose, raw);
        IpLookupResponse resp = doPost("/v1/lookup", request, IpLookupResponse.class);
        return resp;
    }

    /**
     * Look up an IP
     * @pangea.description Retrieve a reputation score for an IP address from a provider, including an optional detailed report.
     * @param ip The IP to be looked up
     * @return IpLookupResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * IpLookupResponse response = client.lookup(
     *     "93.231.182.110");
     * }
     */
    public IpLookupResponse lookup(String ip) throws PangeaException, PangeaAPIException {
        return lookupPost(ip, null, null, null);
    }

    /**
     * Look up an IP - provider
     * @pangea.description Retrieve a reputation score for an IP address from a provider, including an optional detailed report.
     * @param ip The IP to be looked up
     * @param provider Use reputation data from this provider
     * @return IpLookupResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * IpLookupResponse response = client.lookup(
     *     "93.231.182.110",
     *     "crowdstrike");
     * }
     */
    public IpLookupResponse lookup(String ip, String provider) throws PangeaException, PangeaAPIException {
        return lookupPost(ip, provider, null, null);
    }

    /**
     * Look up an IP - verbose, raw
     * @pangea.description Retrieve a reputation score for an IP address from a provider, including an optional detailed report.
     * @param ip The IP to be looked up
     * @param verbose Echo the API parameters in the response
     * @param raw Include raw data from this provider
     * @return IpLookupResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * IpLookupResponse response = client.lookup(
     *     "93.231.182.110",
     *     true,
     *     true);
     * }
     */
    public IpLookupResponse lookup(String ip, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return lookupPost(ip, null, verbose, raw);
    }

    /**
     * Look up an IP - provider, verbose, raw
     * @pangea.description Retrieve a reputation score for an IP address from a provider, including an optional detailed report.
     * @param ip The IP to be looked up
     * @param provider Use reputation data from this provider
     * @param verbose Echo the API parameters in the response
     * @param raw Include raw data from this provider
     * @return IpLookupResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * IpLookupResponse response = client.lookup(
     *     "93.231.182.110",
     *     "crowdstrike",
     *     true,
     *     true);
     * }
     */
    public IpLookupResponse lookup(String ip, String provider, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return lookupPost(ip, provider, verbose, raw);
    }
}
