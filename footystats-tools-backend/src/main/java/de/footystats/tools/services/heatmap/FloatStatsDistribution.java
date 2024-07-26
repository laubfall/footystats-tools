package de.footystats.tools.services.heatmap;

import lombok.Builder;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@BsonDiscriminator(key = StatsBetResultDistribution.DISCRIMINATOR_KEY, value = "FloatStatsDistribution")
public final class FloatStatsDistribution extends StatsBetResultDistribution<Float> {

	@Builder
	public FloatStatsDistribution(String statsName, Long betSucceeded, Long betFailed, Float value, StatsBetResultDistributionKey key) {
		super(statsName, betSucceeded, betFailed, value, key);
	}

	@Builder
	public FloatStatsDistribution() {
	}
}
