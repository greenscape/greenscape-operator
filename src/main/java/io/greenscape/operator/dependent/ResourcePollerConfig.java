package io.greenscape.operator.dependent;

public class ResourcePollerConfig {
	private final int pollPeriod;

	public ResourcePollerConfig(int pollPeriod) {
		this.pollPeriod = pollPeriod;
	}

	public int getPollPeriod() {
		return pollPeriod;
	}
}
