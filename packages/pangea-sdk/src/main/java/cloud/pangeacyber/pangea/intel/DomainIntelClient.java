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

    private DomainLookupResponse lookupPost(String domain, String provider, Boolean verbose, Boolean raw) throws PangeaException, PangeaAPIException{
        DomainLookupRequest request = new DomainLookupRequest(domain, provider, verbose, raw);
        DomainLookupResponse resp = doPost("/v1/lookup", request, DomainLookupResponse.class);
        return resp;
    }

    /**
     * @service domain-intel
     * @summary Look up a domain
     * @description Retrieve domain reputation from a default provider.
     * @param domain - domain address to be looked up
     * @return DomainLookupResponse
     * @throws IOException
     * @throws InterruptedException
     * @throws PangeaException
     * @throws PangeaAPIException
     * @example
     * ```java
     * DomainLookupResponse response = client.lookup("737updatesboeing.com");
     * ```
     */
    public DomainLookupResponse lookup(String domain) throws PangeaException, PangeaAPIException {
        return lookupPost(domain, null, null, null);
    }

    /**
     * @service domain-intel
     * @summary Look up a domain - domain, provider
     * @description Retrieve domain reputation for a particular provider
     * @param domain - domain address to be looked up
     * @param provider - provider to get reputation from
     * @return DomainLookupResponse
     * @throws IOException
     * @throws InterruptedException
     * @throws PangeaException
     * @throws PangeaAPIException
     * @example
     * ```java
     * DomainLookupResponse response = client.lookup("737updatesboeing.com", "domaintools");
     * ```
     */
    public DomainLookupResponse lookup(String domain, String provider) throws PangeaException, PangeaAPIException {
        return lookupPost(domain, provider, null, null);
    }

    /**
     * @service domain-intel
     * @summary Look up a domain - domain, verbose, raw
     * @description Retrieve domain reputation from a default provider.
     * @param domain - domain address to be looked up
     * @param verbose - select a more verbose response
     * @param raw - if true response include provider raw response. This should vary from one provider to another one.
     * @return DomainLookupResponse
     * @throws IOException
     * @throws InterruptedException
     * @throws PangeaException
     * @throws PangeaAPIException
     * @example
     * ```java
     * DomainLookupResponse response = client.lookup("737updatesboeing.com", true, true);
     * ```
     */
    public DomainLookupResponse lookup(String domain, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return lookupPost(domain, null, verbose, raw);
    }

    /**
     * @service domain-intel
     * @summary Look up a domain - domain, provider, verbose, raw
     * @description Retrieve domain reputation for a particular provider
     * @param domain - domain address to be looked up
     * @param provider - provider to get reputation from
     * @param verbose - select a more verbose response
     * @param raw - if true response include provider raw response. This should vary from one provider to another one.
     * @return DomainLookupResponse
     * @throws IOException
     * @throws InterruptedException
     * @throws PangeaException
     * @throws PangeaAPIException
     * @example
     * ```java
     * DomainLookupResponse response = client.lookup("737updatesboeing.com", "domaintools", true, true);
     * ```
     */
    public DomainLookupResponse lookup(String domain, String provider, boolean verbose, boolean raw) throws PangeaException, PangeaAPIException {
        return lookupPost(domain, provider, verbose, raw);
    }
}
