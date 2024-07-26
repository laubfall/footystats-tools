package de.footystats.tools.services.heatmap;

import lombok.Builder;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@BsonDiscriminator(key = StatsBetResultDistribution.DISCRIMINATOR_KEY, value = "IntegerStatsDistribution")
public final class IntegerStatsDistribution extends StatsBetResultDistribution<Integer> {

	@Builder
	public IntegerStatsDistribution(String statsName, Long betSucceeded, Long betFailed, Integer value, StatsBetResultDistributionKey key) {
		super(statsName, betSucceeded, betFailed, value, key);
	}

	@Builder
	public IntegerStatsDistribution() {
	}
}
