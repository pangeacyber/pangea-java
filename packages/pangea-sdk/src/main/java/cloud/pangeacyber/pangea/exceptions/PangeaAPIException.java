package cloud.pangeacyber.pangea.exceptions;

import cloud.pangeacyber.pangea.ErrorField;
import cloud.pangeacyber.pangea.PangeaErrors;
import cloud.pangeacyber.pangea.Response;

public class PangeaAPIException extends Exception {

	Response<PangeaErrors> response;

	public String toString() {
		String ret = "";
		ret += "message: " + this.getMessage() + "\n";
		ret += "summary: " + this.response.getSummary() + "\n";
		ret += "status: " + this.response.getStatus() + "\n";
		ret += "request_id: " + this.response.getRequestId() + "\n";
		ret += "request_time: " + this.response.getRequestTime() + "\n";
		ret += "response_time: " + this.response.getResponseTime() + "\n";
		if (
			this.response.getResult() != null &&
			this.response.getResult().getErrors() != null &&
			this.response.getResult().getErrors().length > 0
		) {
			ret += "Errors: \n";
			for (ErrorField errorField : this.response.getResult().getErrors()) {
				ret += String.format("\t%s\n", errorField.toString());
			}
		}
		return ret;
	}

	public PangeaAPIException(String message, Response<PangeaErrors> response) {
		super(message);
		this.response = response;
	}

	public Response<PangeaErrors> getResponse() {
		return response;
	}
}
