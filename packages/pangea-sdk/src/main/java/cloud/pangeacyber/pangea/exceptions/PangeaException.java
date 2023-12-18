package cloud.pangeacyber.pangea.exceptions;

import java.lang.Exception;

public class PangeaException extends Exception {

	Throwable cause;

	public PangeaException(String message, Throwable cause) {
		super(message);
		this.cause = cause;
	}

	public Throwable getCause() {
		return cause;
	}

	public Throwable getCasue() {
		return cause;
	}
}
