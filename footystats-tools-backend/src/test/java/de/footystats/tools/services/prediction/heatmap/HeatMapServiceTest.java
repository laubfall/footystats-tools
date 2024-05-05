package de.footystats.tools.services.prediction.heatmap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.footystats.tools.services.domain.DomainDataService;
import de.footystats.tools.services.domain.Season;
import de.footystats.tools.services.domain.Year;
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
	private DomainDataService domainDataService;

	@Autowired
	private HeatMapService heatMapService;

	@Autowired
	private StatsBetResultDistributionRepository<IntegerStatsDistribution> statsBetResultDistributionRepository;

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
		assertTrue(bttsAverage.isPresent());
	}

	@MethodSource("trackHeatMapValueArguments")
	@ParameterizedTest
	void trackHeatMapValue(PredictionAnalyze analyzeResult, Bet bet, int succeeded, int failed) {
		var matchStats = new MatchStats();
		matchStats.setBTTSAverage(34);
		final StatsBetResultDistributionKey key = new StatsBetResultDistributionKeyBuilder().bet(bet).build();
		heatMapService.trackHeatMapValue(key, analyzeResult, matchStats);
		List<IntegerStatsDistribution> all = statsBetResultDistributionRepository.findAll().stream()
			.filter(s -> s.getKey().getBet().equals(bet))
			.toList();
		assertEquals(1, all.size());
		Assertions.assertFalse(all.isEmpty());
		StatsBetResultDistribution<?> statsBetResultDistribution = all.getFirst();
		Assertions.assertInstanceOf(IntegerStatsDistribution.class, statsBetResultDistribution);
		assertEquals("bttsAverage", statsBetResultDistribution.getStatsName());
		assertEquals(34, statsBetResultDistribution.getValue());
		assertEquals(succeeded, statsBetResultDistribution.getBetSucceeded());
		assertEquals(failed, statsBetResultDistribution.getBetFailed());
	}

	@Test
	void trackHeatMapValuesForDifferentLevels() {
		StatsBetResultDistributionKeyBuilder builder = new StatsBetResultDistributionKeyBuilder().bet(Bet.BTTS_YES);

		var lvl1 = builder.build();
		var lvl2 = builder.country(domainDataService.countryByName("germany")).build();
		var lvl3 = builder.country(domainDataService.countryByName("germany")).league("Bundesliga").build();
		var lvl4 = builder.country(domainDataService.countryByName("germany")).league("Bundesliga").season(new Season(new Year(2022))).build();
		var matchStats = new MatchStats();
		matchStats.setBTTSAverage(46);

		heatMapService.trackHeatMapValue(lvl1, PredictionAnalyze.SUCCESS, matchStats);
		heatMapService.trackHeatMapValue(lvl2, PredictionAnalyze.SUCCESS, matchStats);
		heatMapService.trackHeatMapValue(lvl3, PredictionAnalyze.SUCCESS, matchStats);
		heatMapService.trackHeatMapValue(lvl4, PredictionAnalyze.SUCCESS, matchStats);

		Optional<IntegerStatsDistribution> heatMap = heatMapService.findByKey(lvl1, "bttsAverage", 46L);
		assertTrue(heatMap.isPresent());
		assertEquals(1, heatMap.get().getBetSucceeded());
		assertEquals(0, heatMap.get().getBetFailed());

		heatMap = heatMapService.findByKey(lvl2, "bttsAverage", 46L);

		assertTrue(heatMap.isPresent());
		assertEquals(1, heatMap.get().getBetSucceeded());
		assertEquals(0, heatMap.get().getBetFailed());

		heatMap = heatMapService.findByKey(lvl3, "bttsAverage", 46L);

		assertTrue(heatMap.isPresent());
		assertEquals(1, heatMap.get().getBetSucceeded());
		assertEquals(0, heatMap.get().getBetFailed());

		heatMap = heatMapService.findByKey(lvl4, "bttsAverage", 46L);
		assertTrue(heatMap.isPresent());
		assertEquals(1, heatMap.get().getBetSucceeded());
		assertEquals(0, heatMap.get().getBetFailed());
	}
}
