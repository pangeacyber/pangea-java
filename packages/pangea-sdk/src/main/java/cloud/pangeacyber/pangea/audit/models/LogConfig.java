package cloud.pangeacyber.pangea.audit.models;

public class LogConfig {

	private final boolean signLocal;
	private final boolean verify;
	private final Boolean verbose;

	private LogConfig(Builder builder) {
		this.signLocal = builder.signLocal;
		this.verify = builder.verify;
		this.verbose = builder.verbose;
	}

	public static class Builder {

		private boolean signLocal;
		private boolean verify;
		private Boolean verbose;

		public Builder() {
			signLocal = false;
			verify = true;
			verbose = null;
		}

		public Builder signLocal(boolean signLocal) {
			this.signLocal = signLocal;
			return this;
		}

		public Builder verify(boolean verify) {
			this.verify = verify;
			return this;
		}

		public Builder verbose(boolean verbose) {
			this.verbose = verbose;
			return this;
		}

		public LogConfig build() {
			return new LogConfig(this);
		}
	}

	public boolean getSignLocal() {
		return signLocal;
	}

	public boolean getVerify() {
		return verify;
	}

	public Boolean getVerbose() {
		return verbose;
	}
}
