package de.footystats.tools.services.prediction.heatmap;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PredictionAnalyze;
import de.footystats.tools.services.prediction.heatmap.StatsBetResultDistributionKey.StatsBetResultDistributionKeyBuilder;
import de.footystats.tools.services.stats.MatchStats;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureDataMongo
class HeatMapServiceTest {

	@Autowired
	private HeatMapService heatMapService;

	@Autowired
	private StatsBetResultDistributionRepository statsBetResultDistributionRepository;

	static Stream<Arguments> trackHeatMapValueArguments() {
		return Stream.of(
			Arguments.of(PredictionAnalyze.SUCCESS, Bet.BTTS_YES, 1, 0),
			Arguments.of(PredictionAnalyze.FAILED, Bet.OVER_ZERO_FIVE, 0, 1)
		);
	}

	@Test
	void heatMappedMatchStatsProperties() {
		var matchStats = new MatchStats();
		matchStats.setBTTSAverage(34);
		Collection<StatsBetResultDistribution<?>> statsBetResultDistributions = heatMapService.heatMapRelevant(matchStats);
		Assertions.assertNotNull(statsBetResultDistributions);
		Assertions.assertFalse(statsBetResultDistributions.isEmpty());

		Optional<StatsBetResultDistribution<?>> bttsAverage = statsBetResultDistributions.stream().filter(s -> s.getStatsName().equals("bttsAverage"))
			.findFirst();
		Assertions.assertTrue(bttsAverage.isPresent());
	}

	@MethodSource("trackHeatMapValueArguments")
	@ParameterizedTest
	void trackHeatMapValue(PredictionAnalyze analyzeResult, Bet bet, int succeeded, int failed) {
		var matchStats = new MatchStats();
		matchStats.setBTTSAverage(34);
		final StatsBetResultDistributionKey key = new StatsBetResultDistributionKeyBuilder().bet(bet).build();
		heatMapService.trackHeatMapValue(key, analyzeResult, matchStats);
		List<StatsBetResultDistribution<?>> all = statsBetResultDistributionRepository.findAll().stream().filter(s -> s.getKey().getBet().equals(bet))
			.toList();
		Assertions.assertEquals(1, all.size());
		Assertions.assertFalse(all.isEmpty());
		StatsBetResultDistribution<?> statsBetResultDistribution = all.getFirst();
		Assertions.assertInstanceOf(IntegerStatsDistribution.class, statsBetResultDistribution);
		Assertions.assertEquals("bttsAverage", statsBetResultDistribution.getStatsName());
		Assertions.assertEquals(34, statsBetResultDistribution.getValue());
		Assertions.assertEquals(succeeded, statsBetResultDistribution.getBetSucceeded());
		Assertions.assertEquals(failed, statsBetResultDistribution.getBetFailed());
	}
}
