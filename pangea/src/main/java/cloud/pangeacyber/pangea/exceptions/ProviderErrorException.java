package cloud.pangeacyber.pangea.exceptions;

import cloud.pangeacyber.pangea.PangeaErrors;
import cloud.pangeacyber.pangea.Response;

//Downstream provider error
public class ProviderErrorException extends PangeaAPIException{

    public ProviderErrorException(String message, Response<PangeaErrors> response) {
        super(message, response);
    }

}
