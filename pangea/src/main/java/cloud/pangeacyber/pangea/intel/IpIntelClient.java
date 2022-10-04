package cloud.pangeacyber.pangea.intel;

import java.io.IOException;

import cloud.pangeacyber.pangea.Client;
import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Response;


final class IpLookupRequest {
    @JsonProperty("ip")
    String Ip;

    @JsonProperty("provider")
    String provider;

    @JsonProperty("verbose")
    boolean verbose;

    @JsonProperty("raw")
    boolean raw;

    IpLookupRequest(String ip, String provider, boolean verbose, boolean raw){
        this.Ip = ip;
        this.provider = provider;
        this.verbose = verbose;
        this.raw = raw;
    }

}

final class IpLookupResponse extends Response<IntelLookupOutput> {}

public class IpIntelClient extends Client{
    public static String serviceName = "ip-intel";

    public IpIntelClient(Config config) {
        super(config, serviceName);
    }

    private IpLookupResponse intelPost(IpLookupRequest request) throws IOException, InterruptedException {
        IpLookupResponse resp = doPost("v1/lookup", request, IpLookupResponse.class);
        return resp;
    }

    public IpLookupResponse lookup(String ip) throws IOException, InterruptedException {
        IpLookupRequest request = new IpLookupRequest(ip, null, false, false);
        return intelPost(request);
    }

    public IpLookupResponse lookup(String ip, String provider) throws IOException, InterruptedException {
        IpLookupRequest request = new IpLookupRequest(ip, provider, false, false);
        return intelPost(request);
    }

    public IpLookupResponse lookup(String ip, boolean verbose, boolean raw) throws IOException, InterruptedException {
        IpLookupRequest request = new IpLookupRequest(ip, null, verbose, raw);
        return intelPost(request);
    }

    public IpLookupResponse lookup(String ip, String provider, boolean verbose, boolean raw) throws IOException, InterruptedException {
        IpLookupRequest request = new IpLookupRequest(ip, provider, verbose, raw);
        return intelPost(request);
    }
}
