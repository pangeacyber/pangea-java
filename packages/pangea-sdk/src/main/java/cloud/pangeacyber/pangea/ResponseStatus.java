package cloud.pangeacyber.pangea;

public enum ResponseStatus {
	SUCCESS("Success"),
	FAILED("Failed"),
	VALIDATION_ERR("ValidationError"),
	TOO_MANY_REQUESTS("TooManyRequests"),
	NO_CREDIT("NoCredit"),
	UNAUTHORIZED("Unauthorized"),
	SERVICE_NOT_ENABLED("ServiceNotEnabled"),
	PROVIDER_ERR("ProviderError"),
	MISSING_CONFIG_ID_SCOPE("MissingConfigIDScope"),
	MISSING_CONFIG_ID("MissingConfigID"),
	SERVICE_NOT_AVAILABLE("ServiceNotAvailable"),
	TREE_NOT_FOUND("TreeNotFound"),
	IP_NOT_FOUND("IPNotFound"),
	INTERNAL_ERROR("InternalError");

	private final String text;

	ResponseStatus(String text) {
		this.text = text;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return text;
	}
}
