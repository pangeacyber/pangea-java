package cloud.pangeacyber.pangea.exceptions;

import cloud.pangeacyber.pangea.PangeaErrors;
import cloud.pangeacyber.pangea.Response;

// Service is not currently available
public class ServiceNotAvailableException extends PangeaAPIException{

    public ServiceNotAvailableException(String message, Response<PangeaErrors> response) {
        super(message, response);
    }
    
}
