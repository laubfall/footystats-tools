package de.footystats.tools.services.prediction.heatmap;

import lombok.Builder;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@BsonDiscriminator(key = StatsBetResultDistribution.DISCRIMINATOR_KEY, value = "DoubleStatsDistribution")
public final class DoubleStatsDistribution extends StatsBetResultDistribution<Double> {

	@Builder
	public DoubleStatsDistribution(String statsName, Long betSucceeded, Long betFailed, Double value, StatsBetResultDistributionKey key) {
		super(statsName, betSucceeded, betFailed, value, key);
	}

	@Builder
	public DoubleStatsDistribution() {
	}
}
