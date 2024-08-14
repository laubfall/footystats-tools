package de.footystats.tools.services.heatmap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.footystats.tools.services.domain.DomainDataService;
import de.footystats.tools.services.domain.Season;
import de.footystats.tools.services.domain.Year;
import de.footystats.tools.services.heatmap.StatsBetResultDistributionKey.StatsBetResultDistributionKeyBuilder;
import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.PredictionAnalyze;
import de.footystats.tools.services.stats.MatchStats;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

	@BeforeEach
	void setUp() {
		statsBetResultDistributionRepository.deleteAll();
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

	@Test
	void accumulate_for_different_keys_with_identical_root() {
		StatsBetResultDistributionKeyBuilder builder = new StatsBetResultDistributionKeyBuilder().bet(Bet.BTTS_YES);
		var germany = builder.country(domainDataService.countryByName("germany")).build();
		var austria = builder.country(domainDataService.countryByName("austria")).build();

		var matchStats = new MatchStats();
		matchStats.setOddsBTTS_Yes(1.5f);

		heatMapService.trackHeatMapValue(germany, PredictionAnalyze.SUCCESS, matchStats);
		heatMapService.trackHeatMapValue(austria, PredictionAnalyze.SUCCESS, matchStats);

		Optional<StatsBetResultDistribution<Float>> oddsBTTSYes = heatMapService.findByKey(germany, "oddsBTTS_Yes", matchStats.getOddsBTTS_Yes());
		assertTrue(oddsBTTSYes.isPresent());
		oddsBTTSYes = heatMapService.findByKey(austria, "oddsBTTS_Yes", matchStats.getOddsBTTS_Yes());
		assertTrue(oddsBTTSYes.isPresent());

		oddsBTTSYes = heatMapService.findByKey(germany.broader(), "oddsBTTS_Yes", matchStats.getOddsBTTS_Yes());
		assertTrue(oddsBTTSYes.isPresent());

		assertEquals(2L, oddsBTTSYes.get().getBetSucceeded());
	}

	@Test
	void heatMap_check_upsert() {
		StatsBetResultDistributionKeyBuilder builder = new StatsBetResultDistributionKeyBuilder().bet(Bet.BTTS_YES);
		var lvl1 = builder.build();
		var matchStats = new MatchStats();
		matchStats.setBTTSAverage(46);

		heatMapService.trackHeatMapValue(lvl1, PredictionAnalyze.SUCCESS, matchStats);
		heatMapService.trackHeatMapValue(lvl1, PredictionAnalyze.SUCCESS, matchStats);

		Optional<IntegerStatsDistribution> heatMap = heatMapService.findByKey(lvl1, "bttsAverage", 46L);
		assertTrue(heatMap.isPresent());
		assertEquals(2, heatMap.get().getBetSucceeded());
		assertEquals(0, heatMap.get().getBetFailed());
	}

	@Test
	void heatMap_annotation_without_field_name() {
		StatsBetResultDistributionKeyBuilder builder = new StatsBetResultDistributionKeyBuilder().bet(Bet.BTTS_YES);
		var lvl1 = builder.build();
		final SomeStats someStats = new SomeStats(3, 0, 0, 0, 0, 0);

		heatMapService.trackHeatMapValue(lvl1, PredictionAnalyze.SUCCESS, someStats);

		Optional<IntegerStatsDistribution> heatMap = heatMapService.findByKey(lvl1, "someValue", 3);
		assertTrue(heatMap.isPresent());
	}

	@Test
	void heatMap_value_fraction() {
		StatsBetResultDistributionKeyBuilder builder = new StatsBetResultDistributionKeyBuilder().bet(Bet.BTTS_YES);
		var lvl1 = builder.build();
		final SomeStats someStats = new SomeStats(0, 0.4323, 1.2324532, 0, 0, 0);

		heatMapService.trackHeatMapValue(lvl1, PredictionAnalyze.SUCCESS, someStats);

		Optional<IntegerStatsDistribution> heatMap = heatMapService.findByKey(lvl1, "someFractionValue", 0.4);
		assertTrue(heatMap.isPresent());

		heatMap = heatMapService.findByKey(lvl1, "someFractionValue2", 1.232);
		assertTrue(heatMap.isPresent());
	}

	@Test
	void heatMap_value_default_fraction() {
		StatsBetResultDistributionKeyBuilder builder = new StatsBetResultDistributionKeyBuilder().bet(Bet.BTTS_YES);
		var lvl1 = builder.build();
		final SomeStats someStats = new SomeStats(0, 0, 0, 0.1234, 0, 0);

		heatMapService.trackHeatMapValue(lvl1, PredictionAnalyze.SUCCESS, someStats);

		Optional<IntegerStatsDistribution> heatMap = heatMapService.findByKey(lvl1, "someValueWithDefaultFraction", 0.12);
		assertTrue(heatMap.isPresent());
	}

	@Test
	void heatMap_ignore_unwanted_values() {
		StatsBetResultDistributionKeyBuilder builder = new StatsBetResultDistributionKeyBuilder().bet(Bet.BTTS_YES);
		var lvl1 = builder.build();
		SomeStats someStats = new SomeStats(-1, 100.32, 0, 0.1234, 0.4, 0.6);
		heatMapService.trackHeatMapValue(lvl1, PredictionAnalyze.SUCCESS, someStats);

		Optional<IntegerStatsDistribution> heatMap = heatMapService.findByKey(lvl1, "ignoreLt", 0.4);
		assertFalse(heatMap.isPresent());

		heatMap = heatMapService.findByKey(lvl1, "ignoreGt", 0.6);
		assertFalse(heatMap.isPresent());

		heatMap = heatMapService.findByKey(lvl1, "someValue", -1);
		assertFalse(heatMap.isPresent());

		heatMap = heatMapService.findByKey(lvl1, "someFractionValue", 100.3);
		assertFalse(heatMap.isPresent());

		someStats = new SomeStats(0, 0, 0, 0.1234, 0.5, 0.5);
		heatMapService.trackHeatMapValue(lvl1, PredictionAnalyze.SUCCESS, someStats);

		heatMap = heatMapService.findByKey(lvl1, "ignoreLt", 0.5);
		assertTrue(heatMap.isPresent());

		heatMap = heatMapService.findByKey(lvl1, "ignoreGt", 0.5);
		assertTrue(heatMap.isPresent());
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	static class SomeStats {

		@HeatMap
		private int someValue;

		@HeatMap(fraction = 1)
		private double someFractionValue;

		@HeatMap(fraction = 3)
		private double someFractionValue2;

		@HeatMap
		private double someValueWithDefaultFraction;

		@HeatMap(ignoreLt = 0.5)
		private double ignoreLt;

		@HeatMap(ignoreGt = 0.5)
		private double ignoreGt;
	}
}
