package cloud.pangeacyber.pangea.intel;

import java.io.IOException;

import cloud.pangeacyber.pangea.Client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;


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

    private DomainLookupResponse intelPost(DomainLookupRequest request) throws IOException, InterruptedException, PangeaAPIException{
        DomainLookupResponse resp = doPost("/v1/lookup", request, DomainLookupResponse.class);
        return resp;
    }

    public DomainLookupResponse lookup(String domain) throws IOException, InterruptedException, PangeaAPIException {
        DomainLookupRequest request = new DomainLookupRequest(domain, null, false, false);
        return intelPost(request);
    }

    public DomainLookupResponse lookup(String domain, String provider) throws IOException, InterruptedException, PangeaAPIException {
        DomainLookupRequest request = new DomainLookupRequest(domain, provider, false, false);
        return intelPost(request);
    }

    public DomainLookupResponse lookup(String domain, boolean verbose, boolean raw) throws IOException, InterruptedException, PangeaAPIException {
        DomainLookupRequest request = new DomainLookupRequest(domain, null, verbose, raw);
        return intelPost(request);
    }

    public DomainLookupResponse lookup(String domain, String provider, boolean verbose, boolean raw) throws IOException, InterruptedException, PangeaAPIException {
        DomainLookupRequest request = new DomainLookupRequest(domain, provider, verbose, raw);
        return intelPost(request);
    }
}
