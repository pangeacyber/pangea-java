package cloud.pangeacyber.pangea;

import cloud.pangeacyber.pangea.exceptions.AcceptedRequestException;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.opentest4j.TestAbortedException;

public class SkipAcceptedExtension implements TestExecutionExceptionHandler {

	@Override
	public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
		if (throwable instanceof AcceptedRequestException) {
			throw new TestAbortedException(
				String.format(
					"Result of request '%s' took too long.",
					((AcceptedRequestException) throwable).getRequestId()
				),
				throwable
			);
		}
		throw throwable;
	}
}
