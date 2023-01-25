package cloud.pangeacyber.pangea.exceptions;

import cloud.pangeacyber.pangea.PangeaErrors;
import cloud.pangeacyber.pangea.Response;

//A pangea service error
public class InternalServiceErrorException extends PangeaAPIException {

	public InternalServiceErrorException(String message, Response<PangeaErrors> response) {
		super(message, response);
	}
}
