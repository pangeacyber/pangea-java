package cloud.pangeacyber.pangea.exceptions;

public class ServiceTemporarilyUnavailable extends PangeaException {

	String body;

	public ServiceTemporarilyUnavailable(String body) {
		super("ServiceTemporarilyUnavailable", null);
	}

	public String getBody() {
		return body;
	}
}
