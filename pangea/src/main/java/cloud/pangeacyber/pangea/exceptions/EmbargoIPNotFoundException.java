package cloud.pangeacyber.pangea.exceptions;

import cloud.pangeacyber.pangea.PangeaErrors;
import cloud.pangeacyber.pangea.Response;

//IP address was not found
public class EmbargoIPNotFoundException extends PangeaAPIException{

    public EmbargoIPNotFoundException(String message, Response<PangeaErrors> response) {
        super(message, response);
    }

}
