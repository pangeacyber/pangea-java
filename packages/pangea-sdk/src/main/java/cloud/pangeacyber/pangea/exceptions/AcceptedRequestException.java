package cloud.pangeacyber.pangea.exceptions;

import cloud.pangeacyber.pangea.PangeaErrors;
import cloud.pangeacyber.pangea.Response;

//Too many requests were made
public class AcceptedRequestException extends PangeaAPIException {

	String requestId;
	AcceptedResult acceptedResult;

	public AcceptedRequestException(String message, Response<PangeaErrors> response, AcceptedResult acceptedResult) {
		super(message, response);
		requestId = response.getRequestId();
		this.acceptedResult = acceptedResult;
	}

	public String getRequestId() {
		return requestId;
	}

	public AcceptedResult getAcceptedResult() {
		return acceptedResult;
	}
}
