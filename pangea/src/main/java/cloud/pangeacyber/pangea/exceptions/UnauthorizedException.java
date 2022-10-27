package cloud.pangeacyber.pangea.exceptions;

import cloud.pangeacyber.pangea.PangeaErrors;
import cloud.pangeacyber.pangea.Response;

// User is not authorized to access a given resource
public class UnauthorizedException extends PangeaAPIException {

    public UnauthorizedException(String serviceName, Response<PangeaErrors> response) {
        super("User is not authorized to access service " + serviceName, response);
    }

}
