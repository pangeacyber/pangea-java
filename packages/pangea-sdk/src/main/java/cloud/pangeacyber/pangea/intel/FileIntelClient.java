package cloud.pangeacyber.pangea.intel;
import cloud.pangeacyber.pangea.Client;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.commons.codec.binary.Hex;

import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;
import cloud.pangeacyber.pangea.intel.models.FileLookupResponse;
import cloud.pangeacyber.pangea.intel.models.FileReputationResponse;


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

final class FileReputationRequest {
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

    FileReputationRequest(String hash, String hashType, String provider, Boolean verbose, Boolean raw){
        this.Hash = hash;
        this.HashType = hashType;
        this.provider = provider;
        this.verbose = verbose;
        this.raw = raw;
    }
}

public class FileIntelClient extends Client{
    public static String serviceName = "file-intel";

    public FileIntelClient(Config config) {
        super(config, serviceName);
    }

    private FileLookupResponse lookupPost(String hash, String hashType, String provider, Boolean verbose, Boolean raw) throws PangeaException, PangeaAPIException {
        FileLookupRequest request = new FileLookupRequest(hash, hashType, provider, verbose, raw);
        FileLookupResponse resp = doPost("/v1/reputation", request, FileLookupResponse.class);
        return resp;
    }

    /**
     * File reputation
     * @pangea.description Retrieve file reputation from a default provider, using the file's hash.
     * @deprecated use reputation instead.
     * @param hash hash of the file
     * @param hashType Type of hash, can be "sha256", "sha1" or "md5"
     * @return FileLookupResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * FileLookupResponse response = client.lookup(
     *     "142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e",
     *     "sha256");
     * }
     */
    public FileLookupResponse lookup(String hash, String hashType) throws PangeaException, PangeaAPIException {
        return lookupPost(hash, hashType, null, null, null);
    }

    /**
     * File reputation - hash, hashType, provider
     * @pangea.description Retrieve file reputation from a provider, using the file's hash.
     * @deprecated use reputation instead.
     * @param hash hash of the file
     * @param hashType Type of hash, can be "sha256", "sha1" or "md5"
     * @param provider provider to get reputation from
     * @return FileLookupResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * FileLookupResponse response = client.lookup(
     *     "142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e",
     *     "sha256",
     *     "reversinglabs");
     * }
     */
    public FileLookupResponse lookup(String hash, String hashType, String provider) throws PangeaException, PangeaAPIException {
        return lookupPost(hash, hashType, provider, null, null);
    }

    /**
     * File reputation - hash, hashType, verbose, raw
     * @pangea.description Retrieve file reputation from a default provider, using the file's hash.
     * @deprecated use reputation instead.
     * @param hash hash of the file
     * @param hashType Type of hash, can be "sha256", "sha1" or "md5"
     * @param verbose select a more verbose response
     * @param raw if true response include provider raw response. This should vary from one provider to another one.
     * @return FileLookupResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * FileLookupResponse response = client.lookup(
     *     "142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e",
     *     "sha256",
     *     true,
     *     false);
     * }
     */
    public FileLookupResponse lookup(String hash, String hashType, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return lookupPost(hash, hashType, null, verbose, raw);
    }

    /**
     * File reputation - hash, hashType, provider, verbose, raw
     * @pangea.description Retrieve file reputation from a provider, using the file's hash.
     * @deprecated use reputation instead.
     * @param hash hash of the file
     * @param hashType Type of hash, can be "sha256", "sha1" or "md5"
     * @param provider provider to get reputation from
     * @param verbose select a more verbose response
     * @param raw if true response include provider raw response. This should vary from one provider to another one.
     * @return FileLookupResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * FileLookupResponse response = client.lookup(
     *     "142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e",
     *     "sha256",
     *     "reversinglabs",
     *     true,
     *     false);
     * }
     */
    public FileLookupResponse lookup(String hash, String hashType, String provider, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return lookupPost(hash, hashType, provider, verbose, raw);
    }

    private FileReputationResponse reputationPost(String hash, String hashType, String provider, Boolean verbose, Boolean raw) throws PangeaException, PangeaAPIException {
        FileReputationRequest request = new FileReputationRequest(hash, hashType, provider, verbose, raw);
        FileReputationResponse resp = doPost("/v1/reputation", request, FileReputationResponse.class);
        return resp;
    }

    /**
     * File reputation
     * @pangea.description Retrieve file reputation from a default provider, using the file's hash.
     * @param hash hash of the file
     * @param hashType Type of hash, can be "sha256", "sha1" or "md5"
     * @return FileReputationResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * FileReputationResponse response = client.reputation(
     *     "142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e",
     *     "sha256");
     * }
     */
    public FileReputationResponse reputation(String hash, String hashType) throws PangeaException, PangeaAPIException {
        return reputationPost(hash, hashType, null, null, null);
    }

    /**
     * File reputation - hash, hashType, provider
     * @pangea.description Retrieve file reputation from a provider, using the file's hash.
     * @param hash hash of the file
     * @param hashType Type of hash, can be "sha256", "sha1" or "md5"
     * @param provider provider to get reputation from
     * @return FileReputationResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * FileReputationResponse response = client.reputation(
     *     "142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e",
     *     "sha256",
     *     "reversinglabs");
     * }
     */
    public FileReputationResponse reputation(String hash, String hashType, String provider) throws PangeaException, PangeaAPIException {
        return reputationPost(hash, hashType, provider, null, null);
    }

    /**
     * File reputation - hash, hashType, verbose, raw
     * @pangea.description Retrieve file reputation from a default provider, using the file's hash.
     * @param hash hash of the file
     * @param hashType Type of hash, can be "sha256", "sha1" or "md5"
     * @param verbose select a more verbose response
     * @param raw if true response include provider raw response. This should vary from one provider to another one.
     * @return FileReputationResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * FileReputationResponse response = client.reputation(
     *     "142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e",
     *     "sha256",
     *     true,
     *     false);
     * }
     */
    public FileReputationResponse reputation(String hash, String hashType, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return reputationPost(hash, hashType, null, verbose, raw);
    }

    /**
     * File reputation - hash, hashType, provider, verbose, raw
     * @pangea.description Retrieve file reputation from a provider, using the file's hash.
     * @param hash hash of the file
     * @param hashType Type of hash, can be "sha256", "sha1" or "md5"
     * @param provider provider to get reputation from
     * @param verbose select a more verbose response
     * @param raw if true response include provider raw response. This should vary from one provider to another one.
     * @return FileReputationResponse
     * @throws PangeaException
     * @throws PangeaAPIException
     * @pangea.code
     * {@code
     * FileReputationResponse response = client.reputation(
     *     "142b638c6a60b60c7f9928da4fb85a5a8e1422a9ffdc9ee49e17e56ccca9cf6e",
     *     "sha256",
     *     "reversinglabs",
     *     true,
     *     false);
     * }
     */
    public FileReputationResponse reputation(String hash, String hashType, String provider, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return reputationPost(hash, hashType, provider, verbose, raw);
    }

    public static String calculateSHA256fromFile(String filepath) throws PangeaException{
        byte[] buffer= new byte[8192];
        int count;
        MessageDigest digest = null;
        try{
            digest = MessageDigest.getInstance("SHA-256");
        } catch(NoSuchAlgorithmException e){
            throw new PangeaException("NoSuchAlgorithm", e);
        }

        FileInputStream file = null;
        try{
            file = new FileInputStream(filepath);
        } catch(FileNotFoundException e){
            throw new PangeaException("FileNotFoundException", e);
        }

        BufferedInputStream bis = new BufferedInputStream(file);

        try{
            while ((count = bis.read(buffer)) > 0) {
                digest.update(buffer, 0, count);
            }
            bis.close();
        } catch(IOException e){
            throw new PangeaException("Failed to update digest", e);
        }

        byte[] hash = digest.digest();
        return Hex.encodeHexString(hash);
    }

}
