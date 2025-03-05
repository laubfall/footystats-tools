package de.footystats.tools.services.bet;


import java.util.List;

public class AttributeSeries {
	private final BaseBetAttribute<?>[] attributes;

	public AttributeSeries(BaseBetAttribute<?>[] attributes) {
		this.attributes = attributes;
	}

	public List<BetSeries> possibleBetSeries() {
		return null;
	}
}
