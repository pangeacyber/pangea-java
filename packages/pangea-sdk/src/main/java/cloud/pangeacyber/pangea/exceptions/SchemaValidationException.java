package cloud.pangeacyber.pangea.exceptions;

import com.networknt.schema.Error;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Thrown when a Secure Audit Log event does not validate against the expected
 * event schema.
 */
public class SchemaValidationException extends RuntimeException {

	private final transient Collection<Error> errors;

	public SchemaValidationException(Collection<Error> errors) {
		this.errors = errors;
	}

	public SchemaValidationException(String message) {
		super(message);
		this.errors = null;
	}

	public SchemaValidationException(String message, Collection<Error> errors) {
		super(message);
		this.errors = errors;
	}

	public SchemaValidationException(Throwable throwable) {
		super(throwable);
		this.errors = null;
	}

	public SchemaValidationException(String message, Throwable throwable) {
		super(message, throwable);
		this.errors = null;
	}

	@Override
	public String getMessage() {
		return this.errors != null
			? super.getMessage() +
			System.lineSeparator() +
			this.errors.stream().map(Error::getMessage).collect(Collectors.joining(System.lineSeparator()))
			: super.getMessage();
	}
}
