package cloud.pangeacyber.pangea.exceptions;

import cloud.pangeacyber.pangea.PangeaErrors;
import cloud.pangeacyber.pangea.Response;

//Too many requests were made
public class AcceptedRequestException extends PangeaAPIException {

	String requestId;

	public AcceptedRequestException(String message, Response<PangeaErrors> response) {
		super(message, response);
		requestId = response.getRequestId();
	}

	public String getRequestId() {
		return requestId;
	}
}
