package cloud.pangeacyber.pangea.exceptions;

import cloud.pangeacyber.pangea.PangeaErrors;
import cloud.pangeacyber.pangea.Response;

public class PangeaAPIException extends Exception {

	Response<PangeaErrors> response;

	public PangeaAPIException(String message, Response<PangeaErrors> response) {
		super(message);
		this.response = response;
	}

	public Response<PangeaErrors> getResponse() {
		return response;
	}
}
