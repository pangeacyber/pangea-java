package cloud.pangeacyber.pangea.intel;
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

    private FileLookupResponse lookupPost(String hash, String hashType, String provider, Boolean verbose, Boolean raw) throws PangeaException, PangeaAPIException {
        FileLookupRequest request = new FileLookupRequest(hash, hashType, provider, verbose, raw);
        FileLookupResponse resp = doPost("/v1/lookup", request, FileLookupResponse.class);
        return resp;
    }

    /**
     * @summary Lookup
     * @description Retrieve file reputation from a default provider, using the file's hash.
     * @param hash - hash of the file
     * @param hashType - Type of hash, can be "sha256", "sha1" or "md5"
     * @return FileLookupResponse
     * @throws IOException
     * @throws InterruptedException
     * @throws PangeaException
     * @throws PangeaAPIException
     * @example
     * ```java
     * FileLookupResponse response = client.lookup("142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e", "sha256");
     * ```
     */
    public FileLookupResponse lookup(String hash, String hashType) throws PangeaException, PangeaAPIException {
        return lookupPost(hash, hashType, null, null, null);
    }

    /**
     * @summary Lookup
     * @description Retrieve file reputation from a provider, using the file's hash.
     * @param hash - hash of the file
     * @param hashType - Type of hash, can be "sha256", "sha1" or "md5"
     * @param provider - provider to get reputation from
     * @return FileLookupResponse
     * @throws IOException
     * @throws InterruptedException
     * @throws PangeaException
     * @throws PangeaAPIException
     * @example
     * ```java
     * FileLookupResponse response = client.lookup("142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e", "sha256", "reversinglabs");
     * ```
     */
    public FileLookupResponse lookup(String hash, String hashType, String provider) throws PangeaException, PangeaAPIException {
        return lookupPost(hash, hashType, provider, null, null);
    }

    /**
     * @summary Lookup
     * @description Retrieve file reputation from a default provider, using the file's hash.
     * @param hash - hash of the file
     * @param hashType - Type of hash, can be "sha256", "sha1" or "md5"
     * @param verbose - select a more verbose response
     * @param raw - if true response include provider raw response. This should vary from one provider to another one.
     * @return FileLookupResponse
     * @throws IOException
     * @throws InterruptedException
     * @throws PangeaException
     * @throws PangeaAPIException
     */
    public FileLookupResponse lookup(String hash, String hashType, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return lookupPost(hash, hashType, null, verbose, raw);
    }

    /**
     * @summary Lookup
     * @description Retrieve file reputation from a provider, using the file's hash.
     * @param hash - hash of the file
     * @param hashType - Type of hash, can be "sha256", "sha1" or "md5"
     * @param provider - provider to get reputation from
     * @param verbose - select a more verbose response
     * @param raw - if true response include provider raw response. This should vary from one provider to another one.
     * @return FileLookupResponse
     * @throws IOException
     * @throws InterruptedException
     * @throws PangeaException
     * @throws PangeaAPIException
     * @example
     * ```java
     * FileLookupResponse response = client.lookup("142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e", "sha256", "reversinglabs", true, false);
     * ```
     */
    public FileLookupResponse lookup(String hash, String hashType, String provider, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return lookupPost(hash, hashType, provider, verbose, raw);
    }
}
