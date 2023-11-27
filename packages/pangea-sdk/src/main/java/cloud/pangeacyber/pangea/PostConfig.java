package cloud.pangeacyber.pangea;

public final class PostConfig {

	boolean pollResult;

	protected PostConfig(Builder builder) {
		this.pollResult = builder.pollResult;
	}

	public boolean getPollResult() {
		return pollResult;
	}

	public static class Builder {

		boolean pollResult = true; // by default try to poll result

		public Builder() {}

		public Builder pollResult(boolean pollResult) {
			this.pollResult = pollResult;
			return this;
		}

		public PostConfig build() {
			return new PostConfig(this);
		}
	}
}
