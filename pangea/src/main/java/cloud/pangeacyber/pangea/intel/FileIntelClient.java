package cloud.pangeacyber.pangea.intel;

import java.io.IOException;

import cloud.pangeacyber.pangea.Client;
import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Response;


final class FileLookupRequest {
    @JsonProperty("hash")
    String Hash;

    @JsonProperty("hash_type")
    String HashType;

    @JsonProperty("provider")
    String provider;

    @JsonProperty("verbose")
    boolean verbose;

    @JsonProperty("raw")
    boolean raw;

    FileLookupRequest(String hash, String hashType, String provider, boolean verbose, boolean raw){
        this.Hash = hash;
        this.HashType = hashType;
        this.provider = provider;
        this.verbose = verbose;
        this.raw = raw;
    }

}

final class FileLookupResponse extends Response<IntelLookupOutput> {}

public class FileIntelClient extends Client{
    public static String serviceName = "file-intel";

    public FileIntelClient(Config config) {
        super(config, serviceName);
    }

    private FileLookupResponse intelPost(FileLookupRequest request) throws IOException, InterruptedException {
        FileLookupResponse resp = doPost("/v1/lookup", request, FileLookupResponse.class);
        return resp;
    }

    public FileLookupResponse lookup(String hash, String hashType) throws IOException, InterruptedException {
        FileLookupRequest request = new FileLookupRequest(hash, hashType, null, false, false);
        return intelPost(request);
    }

    public FileLookupResponse lookup(String hash, String hashType, String provider) throws IOException, InterruptedException {
        FileLookupRequest request = new FileLookupRequest(hash, hashType, provider, false, false);
        return intelPost(request);
    }

    public FileLookupResponse lookup(String hash, String hashType, boolean verbose, boolean raw) throws IOException, InterruptedException {
        FileLookupRequest request = new FileLookupRequest(hash, hashType, null, verbose, raw);
        return intelPost(request);
    }

    public FileLookupResponse lookup(String hash, String hashType, String provider, boolean verbose, boolean raw) throws IOException, InterruptedException {
        FileLookupRequest request = new FileLookupRequest(hash, hashType, provider, verbose, raw);
        return intelPost(request);
    }
}
