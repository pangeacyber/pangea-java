package cloud.pangeacyber.pangea.embargo;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.exceptions.PangeaAPIException;

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

    public IsoCheckResponse isoCheck(String isoCode) throws IOException, InterruptedException, PangeaAPIException{
        IsoCheckRequest request = new IsoCheckRequest(isoCode);
        IsoCheckResponse resp = doPost("/v1/iso/check", request, IsoCheckResponse.class);
        return resp;
    }

    public IpCheckResponse ipCheck(String ip) throws IOException, InterruptedException, PangeaAPIException{
        IpCheckRequest request = new IpCheckRequest(ip);
        IpCheckResponse resp = doPost("/v1/ip/check", request, IpCheckResponse.class);
        return resp;
    }

}
