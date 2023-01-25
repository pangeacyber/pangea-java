package cloud.pangeacyber.pangea.exceptions;

import cloud.pangeacyber.pangea.PangeaErrors;
import cloud.pangeacyber.pangea.Response;

//API usage requires payment
public class NoCreditException extends PangeaAPIException {

	public NoCreditException(String message, Response<PangeaErrors> response) {
		super(message, response);
	}
}
