package cloud.pangeacyber.pangea.intel;

import java.io.IOException;

import cloud.pangeacyber.pangea.Client;
import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Response;


final class UrlLookupRequest {
    @JsonProperty("url")
    String Url;

    @JsonProperty("provider")
    String provider;

    @JsonProperty("verbose")
    boolean verbose;

    @JsonProperty("raw")
    boolean raw;

    UrlLookupRequest(String url, String provider, boolean verbose, boolean raw){
        this.Url = url;
        this.provider = provider;
        this.verbose = verbose;
        this.raw = raw;
    }

}

final class UrlLookupResponse extends Response<IntelLookupOutput> {}

public class UrlIntelClient extends Client{
    public static String serviceName = "url-intel";

    public UrlIntelClient(Config config) {
        super(config, serviceName);
    }

    private UrlLookupResponse intelPost(UrlLookupRequest request) throws IOException, InterruptedException {
        UrlLookupResponse resp = doPost("v1/lookup", request, UrlLookupResponse.class);
        return resp;
    }

    public UrlLookupResponse lookup(String url) throws IOException, InterruptedException {
        UrlLookupRequest request = new UrlLookupRequest(url, null, false, false);
        return intelPost(request);
    }

    public UrlLookupResponse lookup(String url, String provider) throws IOException, InterruptedException {
        UrlLookupRequest request = new UrlLookupRequest(url, provider, false, false);
        return intelPost(request);
    }

    public UrlLookupResponse lookup(String url, boolean verbose, boolean raw) throws IOException, InterruptedException {
        UrlLookupRequest request = new UrlLookupRequest(url, null, verbose, raw);
        return intelPost(request);
    }

    public UrlLookupResponse lookup(String url, String provider, boolean verbose, boolean raw) throws IOException, InterruptedException {
        UrlLookupRequest request = new UrlLookupRequest(url, provider, verbose, raw);
        return intelPost(request);
    }
}
