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

final class IpGeolocateRequest {
    @JsonProperty("ip")
    String ip;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("provider")
    String provider;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("verbose")
    Boolean verbose;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("raw")
    Boolean raw;

    IpGeolocateRequest(String ip, String provider, Boolean verbose, Boolean raw){
        this.ip = ip;
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
        IpGeolocateRequest request = new IpGeolocateRequest(ip, provider, verbose, raw);
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
}
