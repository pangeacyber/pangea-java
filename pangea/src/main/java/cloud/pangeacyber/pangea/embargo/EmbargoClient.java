package cloud.pangeacyber.pangea.embargo;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.pangeacyber.pangea.Client;
import cloud.pangeacyber.pangea.Config;
import cloud.pangeacyber.pangea.Response;

final class IsoCheckRequest {
    @JsonProperty("iso_code")
    String isoCode;

    public IsoCheckRequest(String isoCode) {
        this.isoCode = isoCode;
    }
}

final class IsoCheckResponse extends Response<EmbargoSanctions> {}

public class EmbargoClient extends Client {
    public static String serviceName = "embargo";

    public EmbargoClient(Config config) {
        super(config, serviceName);
    }

    public IsoCheckResponse isoCheck(String isoCode) throws IOException, InterruptedException {
        IsoCheckRequest request = new IsoCheckRequest(isoCode);
        IsoCheckResponse resp = doPost("/v1/iso/check", request, IsoCheckResponse.class);
        return resp;
    }
}
