package cloud.pangeacyber.pangea.embargo;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;
import cloud.pangeacyber.pangea.exceptions.PangeaException;

final class IsoCheckRequest {
    @JsonProperty("iso_code")
    String isoCode;

    public IsoCheckRequest(String isoCode) {
        this.isoCode = isoCode;
    }
}

final class IpCheckRequest{
    @JsonProperty("ip")
    String ip;

    public IpCheckRequest(String ip) {
        this.ip = ip;
    }
}

final class IsoCheckResponse extends Response<EmbargoSanctions> {}
final class IpCheckResponse extends Response<EmbargoSanctions> {}

public class EmbargoClient extends Client {
    public static String serviceName = "embargo";

    public EmbargoClient(Config config) {
        super(config, serviceName);
    }

    /**
     * @service embargo
     * @summary ISO Code Check
     * @description Check a country code against known sanction and trade embargo lists.
     * @param isoCode - Check the  country against code the enabled embargo lists.
     * @return IsoCheckResponse
     * @throws IOException
     * @throws InterruptedException
     * @throws PangeaException
     * @throws PangeaAPIException
     * @example
     * ```java
     * IsoCheckResponse response = client.isoCheck("CU");
     * ```
     */
    public IsoCheckResponse isoCheck(String isoCode) throws PangeaException, PangeaAPIException{
        IsoCheckRequest request = new IsoCheckRequest(isoCode);
        IsoCheckResponse resp = doPost("/v1/iso/check", request, IsoCheckResponse.class);
        return resp;
    }
    /**
     * @service embargo
     * @summary Check IP
     * @description Check an IP against known sanction and trade embargo lists.
     * @param isoCode - Geolocate this IP and check the corresponding country against the enabled embargo lists.
     * @return IpCheckResponse
     * @throws IOException
     * @throws InterruptedException
     * @throws PangeaException
     * @throws PangeaAPIException
     * @example
     * ```java
     * IpCheckResponse response = client.ipCheck("213.24.238.26");
     * ```
     */
    public IpCheckResponse ipCheck(String ip) throws PangeaException, PangeaAPIException{
        IpCheckRequest request = new IpCheckRequest(ip);
        IpCheckResponse resp = doPost("/v1/ip/check", request, IpCheckResponse.class);
        return resp;
    }

}
