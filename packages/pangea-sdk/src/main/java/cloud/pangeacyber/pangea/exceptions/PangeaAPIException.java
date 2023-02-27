package cloud.pangeacyber.pangea.exceptions;

import cloud.pangeacyber.pangea.ErrorField;
import cloud.pangeacyber.pangea.PangeaErrors;
import cloud.pangeacyber.pangea.Response;

public class PangeaAPIException extends Exception {

	Response<PangeaErrors> response;

    public String toString(){
        String ret = "";
        ret += "Summary: " + this.response.getSummary() + "\n";
        if(this.response.getResult() != null && this.response.getResult().getErrors().length > 0){
            ret += "Errors: \n";
            for (ErrorField errorField : this.response.getResult().getErrors()) {
                ret += "\t " + errorField.getDetail() + "\n";
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
