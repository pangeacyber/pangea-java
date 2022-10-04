package cloud.pangeacyber.pangea.intel;

import java.io.IOException;

import cloud.pangeacyber.pangea.Client;
import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Response;


final class DomainLookupRequest {
    @JsonProperty("domain")
    String Domain;

    @JsonProperty("provider")
    String provider;

    @JsonProperty("verbose")
    boolean verbose;

    @JsonProperty("raw")
    boolean raw;

    DomainLookupRequest(String domain, String provider, boolean verbose, boolean raw){
        this.Domain = domain;
        this.provider = provider;
        this.verbose = verbose;
        this.raw = raw;
    }

}

final class DomainLookupResponse extends Response<IntelLookupOutput> {}

public class DomainIntelClient extends Client{
    public static String serviceName = "file-intel";

    public DomainIntelClient(Config config) {
        super(config, serviceName);
    }

    private DomainLookupResponse intelPost(DomainLookupRequest request) throws IOException, InterruptedException {
        DomainLookupResponse resp = doPost("v1/lookup", request, DomainLookupResponse.class);
        return resp;
    }

    public DomainLookupResponse lookup(String domain) throws IOException, InterruptedException {
        DomainLookupRequest request = new DomainLookupRequest(domain, null, false, false);
        return intelPost(request);
    }

    public DomainLookupResponse lookup(String domain, String provider) throws IOException, InterruptedException {
        DomainLookupRequest request = new DomainLookupRequest(domain, provider, false, false);
        return intelPost(request);
    }

    public DomainLookupResponse lookup(String domain, boolean verbose, boolean raw) throws IOException, InterruptedException {
        DomainLookupRequest request = new DomainLookupRequest(domain, null, verbose, raw);
        return intelPost(request);
    }

    public DomainLookupResponse lookup(String domain, String provider, boolean verbose, boolean raw) throws IOException, InterruptedException {
        DomainLookupRequest request = new DomainLookupRequest(domain, provider, verbose, raw);
        return intelPost(request);
    }
}
