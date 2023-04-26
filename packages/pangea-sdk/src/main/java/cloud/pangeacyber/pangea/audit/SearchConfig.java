package cloud.pangeacyber.pangea.audit;

public class SearchConfig {

	private final boolean verifyConsistency;
	private final boolean verifyEvents;

	private SearchConfig(Builder builder) {
		this.verifyConsistency = builder.verifyConsistency;
		this.verifyEvents = builder.verifyEvents;
	}

	public boolean getVerifyConsistency() {
		return verifyConsistency;
	}

	public boolean getVerifyEvents() {
		return verifyEvents;
	}

	public static class Builder {

		private boolean verifyConsistency;
		private boolean verifyEvents;

		public Builder() {
			verifyConsistency = false;
			verifyEvents = true;
		}

		public Builder verifyConsistency(boolean verifyConsistency) {
			this.verifyConsistency = verifyConsistency;
			return this;
		}

		public Builder verifyEvents(boolean verifyEvents) {
			this.verifyEvents = verifyEvents;
			return this;
		}

		public SearchConfig build() {
			return new SearchConfig(this);
		}
	}
}
