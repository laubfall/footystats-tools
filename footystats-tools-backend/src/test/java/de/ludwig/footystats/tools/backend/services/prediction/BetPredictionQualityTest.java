package de.ludwig.footystats.tools.backend.services.prediction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BetPredictionQualityTest {
	@Test
	public void all_influencer_distributions() {
		var bpq = new BetPredictionQuality();
		bpq.getDistributionBetOnThis().add(new BetPredictionDistribution(10, 2L, List.of(new InfluencerPercentDistribution(20, 1L, "test_influencer", PrecheckResult.OK), new InfluencerPercentDistribution(41, 1L, "another_testinfluencer", PrecheckResult.OK))));
		bpq.getDistributionBetOnThis().add(new BetPredictionDistribution(11, 2L, List.of(new InfluencerPercentDistribution(22, 1L, "test_influencer", PrecheckResult.OK), new InfluencerPercentDistribution(44, 1L, "another_testinfluencer", PrecheckResult.OK))));

		var result = bpq.allDistributions();
		Assertions.assertNotNull(result);
		Assertions.assertEquals(4, result.size());
		Assertions.assertEquals(1, result.stream().filter(ipd -> ipd.getPredictionPercent() == 20).count());
		Assertions.assertEquals(1, result.stream().filter(ipd -> ipd.getPredictionPercent() == 41).count());
		Assertions.assertEquals(1, result.stream().filter(ipd -> ipd.getPredictionPercent() == 22).count());
		Assertions.assertEquals(1, result.stream().filter(ipd -> ipd.getPredictionPercent() == 44).count());
	}
}
