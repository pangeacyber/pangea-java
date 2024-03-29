package cloud.pangeacyber.pangea.audit.responses;

import cloud.pangeacyber.pangea.Response;
import cloud.pangeacyber.pangea.audit.results.LogBulkResult;

public final class LogBulkResponse extends Response<LogBulkResult> {

	public LogBulkResponse(Response<LogBulkResult> response) {
		super(response, response.getAcceptedResult());
	}

	public LogBulkResponse() {}
}
