package cloud.pangeacyber.pangea.intel;
import cloud.pangeacyber.pangea.Client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;


final class UrlLookupRequest {
    @JsonProperty("url")
    String Url;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("provider")
    String provider;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("verbose")
    Boolean verbose;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("raw")
    Boolean raw;

    UrlLookupRequest(String url, String provider, Boolean verbose, Boolean raw){
        this.Url = url;
        this.provider = provider;
        this.verbose = verbose;
        this.raw = raw;
    }

}

final class UrlLookupResponse extends Response<IntelLookupResult> {}

public class UrlIntelClient extends Client{
    public static String serviceName = "url-intel";

    public UrlIntelClient(Config config) {
        super(config, serviceName);
    }

    private UrlLookupResponse lookupPost(String url, String provider, Boolean verbose, Boolean raw) throws PangeaException, PangeaAPIException {
        UrlLookupRequest request = new UrlLookupRequest(url, provider, verbose, raw);
        UrlLookupResponse resp = doPost("/v1/lookup", request, UrlLookupResponse.class);
        return resp;
    }

    public UrlLookupResponse lookup(String url) throws PangeaException, PangeaAPIException {
        return lookupPost(url, null, null, null);
    }

    public UrlLookupResponse lookup(String url, String provider) throws PangeaException, PangeaAPIException {
        return lookupPost(url, provider, null, null);
    }

    public UrlLookupResponse lookup(String url, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return lookupPost(url, null, verbose, raw);
    }

    public UrlLookupResponse lookup(String url, String provider, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return lookupPost(url, provider, verbose, raw);
    }
}
