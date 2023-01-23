package cloud.pangeacyber.pangea.intel;
import cloud.pangeacyber.pangea.Client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;


final class IpCommonRequest {
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

    IpCommonRequest(String ip, String provider, Boolean verbose, Boolean raw){
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
        IpCommonRequest request = new IpCommonRequest(ip, provider, verbose, raw);
        IpLookupResponse resp = doPost("/v1/lookup", request, IpLookupResponse.class);
        return resp;
    }

    public IpLookupResponse lookup(String ip) throws PangeaException, PangeaAPIException {
        return lookupPost(ip, null, null, null);
    }

    public IpLookupResponse lookup(String ip, String provider) throws PangeaException, PangeaAPIException {
        return lookupPost(ip, provider, null, null);
    }

    public IpLookupResponse lookup(String ip, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return lookupPost(ip, null, verbose, raw);
    }

    public IpLookupResponse lookup(String ip, String provider, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return lookupPost(ip, provider, verbose, raw);
    }

    private IpGeolocateResponse geolocatePost(String ip, String provider, Boolean verbose, Boolean raw) throws PangeaException, PangeaAPIException {
        IpCommonRequest request = new IpCommonRequest(ip, provider, verbose, raw);
        IpGeolocateResponse resp = doPost("/v1/geolocate", request, IpGeolocateResponse.class);
        return resp;
    }

    public IpGeolocateResponse geolocate(String ip) throws PangeaException, PangeaAPIException {
        return geolocatePost(ip, null, null, null);
    }

    public IpGeolocateResponse geolocate(String ip, String provider) throws PangeaException, PangeaAPIException {
        return geolocatePost(ip, provider, null, null);
    }

    public IpGeolocateResponse geolocate(String ip, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return geolocatePost(ip, null, verbose, raw);
    }

    public IpGeolocateResponse geolocate(String ip, String provider, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return geolocatePost(ip, provider, verbose, raw);
    }

    private IpDomainResponse domainPost(String ip, String provider, Boolean verbose, Boolean raw) throws PangeaException, PangeaAPIException {
        IpCommonRequest request = new IpCommonRequest(ip, provider, verbose, raw);
        IpDomainResponse resp = doPost("/v1/domain", request, IpDomainResponse.class);
        return resp;
    }

    public IpDomainResponse getDomain(String ip) throws PangeaException, PangeaAPIException {
        return domainPost(ip, null, null, null);
    }

    public IpDomainResponse getDomain(String ip, String provider) throws PangeaException, PangeaAPIException {
        return domainPost(ip, provider, null, null);
    }

    public IpDomainResponse getDomain(String ip, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return domainPost(ip, null, verbose, raw);
    }

    public IpDomainResponse getDomain(String ip, String provider, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return domainPost(ip, provider, verbose, raw);
    }

    private IpVPNResponse vpnPost(String ip, String provider, Boolean verbose, Boolean raw) throws PangeaException, PangeaAPIException {
        IpCommonRequest request = new IpCommonRequest(ip, provider, verbose, raw);
        IpVPNResponse resp = doPost("/v1/vpn", request, IpVPNResponse.class);
        return resp;
    }

    public IpVPNResponse isVPN(String ip) throws PangeaException, PangeaAPIException {
        return vpnPost(ip, null, null, null);
    }

    public IpVPNResponse isVPN(String ip, String provider) throws PangeaException, PangeaAPIException {
        return vpnPost(ip, provider, null, null);
    }

    public IpVPNResponse isVPN(String ip, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return vpnPost(ip, null, verbose, raw);
    }

    public IpVPNResponse isVPN(String ip, String provider, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return vpnPost(ip, provider, verbose, raw);
    }

    private IpProxyResponse proxyPost(String ip, String provider, Boolean verbose, Boolean raw) throws PangeaException, PangeaAPIException {
        IpCommonRequest request = new IpCommonRequest(ip, provider, verbose, raw);
        IpProxyResponse resp = doPost("/v1/proxy", request, IpProxyResponse.class);
        return resp;
    }

    public IpProxyResponse isProxy(String ip) throws PangeaException, PangeaAPIException {
        return proxyPost(ip, null, null, null);
    }

    public IpProxyResponse isProxy(String ip, String provider) throws PangeaException, PangeaAPIException {
        return proxyPost(ip, provider, null, null);
    }

    public IpProxyResponse isProxy(String ip, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return proxyPost(ip, null, verbose, raw);
    }

    public IpProxyResponse isProxy(String ip, String provider, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return proxyPost(ip, provider, verbose, raw);
    }
}
