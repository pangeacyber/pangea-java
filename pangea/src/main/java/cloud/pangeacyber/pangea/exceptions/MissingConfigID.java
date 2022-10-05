package cloud.pangeacyber.pangea.exceptions;

import cloud.pangeacyber.pangea.PangeaErrors;
import cloud.pangeacyber.pangea.Response;

//No config ID was provided in either token scopes or explicitly
public class MissingConfigID extends PangeaAPIException{

    public MissingConfigID(String serviceName, Response<PangeaErrors> response) {
        super(String.format("Token did not contain a config scope for service %s. Create a new token or provide a config ID explicitly in the service base", serviceName), response);
    }

}
