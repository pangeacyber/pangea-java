package cloud.pangeacyber.pangea.intel;

import java.io.IOException;

import cloud.pangeacyber.pangea.Client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;


final class DomainLookupRequest {
    @JsonProperty("domain")
    String Domain;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("provider")
    String provider;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("verbose")
    Boolean verbose;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("raw")
    Boolean raw;

    DomainLookupRequest(String domain, String provider, Boolean verbose, Boolean raw){
        this.Domain = domain;
        this.provider = provider;
        this.verbose = verbose;
        this.raw = raw;
    }

}

final class DomainLookupResponse extends Response<IntelLookupOutput> {}

public class DomainIntelClient extends Client{
    public static String serviceName = "domain-intel";

    public DomainIntelClient(Config config) {
        super(config, serviceName);
    }

    private DomainLookupResponse lookupPost(String domain, String provider, Boolean verbose, Boolean raw) throws IOException, InterruptedException, PangeaException, PangeaAPIException{
        DomainLookupRequest request = new DomainLookupRequest(domain, provider, verbose, raw);
        DomainLookupResponse resp = doPost("/v1/lookup", request, DomainLookupResponse.class);
        return resp;
    }

    public DomainLookupResponse lookup(String domain) throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        return lookupPost(domain, null, null, null);
    }

    public DomainLookupResponse lookup(String domain, String provider) throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        return lookupPost(domain, provider, null, null);
    }

    public DomainLookupResponse lookup(String domain, boolean verbose, boolean raw) throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        return lookupPost(domain, null, verbose, raw);
    }

    public DomainLookupResponse lookup(String domain, String provider, boolean verbose, boolean raw) throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        return lookupPost(domain, provider, verbose, raw);
    }
}
