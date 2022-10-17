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


final class FileLookupRequest {
    @JsonProperty("hash")
    String Hash;

    @JsonProperty("hash_type")
    String HashType;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("provider")
    String provider;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("verbose")
    Boolean verbose;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("raw")
    Boolean raw;

    FileLookupRequest(String hash, String hashType, String provider, Boolean verbose, Boolean raw){
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

    private FileLookupResponse lookupPost(String hash, String hashType, String provider, Boolean verbose, Boolean raw) throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        FileLookupRequest request = new FileLookupRequest(hash, hashType, provider, verbose, raw);
        FileLookupResponse resp = doPost("/v1/lookup", request, FileLookupResponse.class);
        return resp;
    }

    public FileLookupResponse lookup(String hash, String hashType) throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        return lookupPost(hash, hashType, null, null, null);
    }

    public FileLookupResponse lookup(String hash, String hashType, String provider) throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        return lookupPost(hash, hashType, provider, null, null);
    }

    public FileLookupResponse lookup(String hash, String hashType, boolean verbose, boolean raw) throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        return lookupPost(hash, hashType, null, verbose, raw);
    }

    public FileLookupResponse lookup(String hash, String hashType, String provider, boolean verbose, boolean raw) throws IOException, InterruptedException, PangeaException, PangeaAPIException {
        return lookupPost(hash, hashType, provider, verbose, raw);
    }
}
