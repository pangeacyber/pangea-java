package cloud.pangeacyber.pangea.exceptions;

import cloud.pangeacyber.pangea.PangeaErrors;
import cloud.pangeacyber.pangea.Response;

//Too many requests were made
public class InternalServerError extends PangeaAPIException {

	public InternalServerError(String message, Response<PangeaErrors> response) {
		super(message, response);
	}
}
