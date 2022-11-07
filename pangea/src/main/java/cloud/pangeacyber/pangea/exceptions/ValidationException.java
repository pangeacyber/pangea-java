package cloud.pangeacyber.pangea.exceptions;

import cloud.pangeacyber.pangea.PangeaErrors;
import cloud.pangeacyber.pangea.Response;

// Pangea Validation Errors denoting issues with an API request
public class ValidationException extends PangeaAPIException{

    public ValidationException(String message, Response<PangeaErrors> response) {
        super(message, response);
    }

}
