package cloud.pangeacyber.pangea.exceptions;

import java.lang.Exception;

public class PangeaException extends Exception {

	Throwable casue;

	public PangeaException(String message, Throwable cause) {
		super(message);
		this.casue = cause;
	}

	public Throwable getCasue() {
		return casue;
	}
}
