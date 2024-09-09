package cloud.pangeacyber.pangea.exceptions;

import com.networknt.schema.ValidationMessage;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Thrown when a Secure Audit Log event does not validate against the expected
 * event schema.
 */
public class SchemaValidationException extends RuntimeException {

	private final transient Collection<ValidationMessage> validationMessages;

	public SchemaValidationException(Collection<ValidationMessage> validationMessages) {
		this.validationMessages = validationMessages;
	}

	public SchemaValidationException(String message) {
		super(message);
		this.validationMessages = null;
	}

	public SchemaValidationException(Throwable throwable) {
		super(throwable);
		this.validationMessages = null;
	}

	public SchemaValidationException(String message, Throwable throwable) {
		super(message, throwable);
		this.validationMessages = null;
	}

	@Override
	public String getMessage() {
		return this.validationMessages != null
			? this.validationMessages.stream().map(ValidationMessage::getMessage).collect(Collectors.joining("\n"))
			: super.getMessage();
	}
}
