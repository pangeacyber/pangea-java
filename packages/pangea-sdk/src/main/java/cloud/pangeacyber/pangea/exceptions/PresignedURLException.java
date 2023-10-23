package cloud.pangeacyber.pangea.exceptions;

public class PresignedURLException extends PangeaException {

	private String body;

	public PresignedURLException(String message, Throwable cause, String body) {
		super(message, cause);
		this.body = body;
	}

	public String getBody() {
		return body;
	}
}
