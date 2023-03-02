package cloud.pangeacyber.pangea.exceptions;

import cloud.pangeacyber.pangea.PangeaErrors;
import cloud.pangeacyber.pangea.Response;

// User is not authorized to access a given resource
public class NotFound extends PangeaAPIException {

	public NotFound(String url, Response<PangeaErrors> response) {
		super("Resource not found: " + url, response);
	}
}
