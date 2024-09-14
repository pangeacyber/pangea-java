package cloud.pangeacyber.pangea.exceptions;

public class PangeaException extends Exception {

	public PangeaException(String message, Throwable cause) {
		super(message, cause);
	}

	/** @deprecated */
	@Deprecated
	public Throwable getCasue() {
		return getCause();
	}
}
