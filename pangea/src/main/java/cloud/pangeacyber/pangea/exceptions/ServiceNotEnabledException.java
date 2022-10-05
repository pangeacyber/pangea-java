package cloud.pangeacyber.pangea.exceptions;

import cloud.pangeacyber.pangea.PangeaErrors;
import cloud.pangeacyber.pangea.Response;

public class ServiceNotEnabledException extends PangeaAPIException{

    public ServiceNotEnabledException(String serviceName, Response<PangeaErrors> response) {
        super(String.format("%s is not enabled. Go to console.pangea.cloud/service/%s to enable", serviceName, serviceName), response);
    }
    
}
