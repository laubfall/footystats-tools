package de.footystats.tools.services.prediction.quality.view;

import static de.footystats.tools.services.prediction.quality.PredictionQualityRevision.NO_REVISION;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.InfluencerPercentDistribution;
import de.footystats.tools.services.prediction.PrecheckResult;
import de.footystats.tools.services.prediction.quality.BetPredictionQuality;
import de.footystats.tools.services.prediction.quality.BetPredictionQualityRepository;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class PredictionQualityViewServiceTest {

	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private PredictionQualityViewService predictionQualityService;
	@Autowired
	private BetPredictionQualityRepository betPredictionAggregateRepository;

	@BeforeEach
	public void cleanup() {
		betPredictionAggregateRepository.deleteAll();
		cacheManager.getCacheNames()
			.forEach(cacheName -> cacheManager.getCache(cacheName).clear());
	}

	@Test
	void bet_measurement_counts() {
		BetPredictionQuality bet = BetPredictionQuality.builder().predictionPercent(53).betSucceeded(10L).betFailed(3L).bet(Bet.BTTS_YES)
			.revision(NO_REVISION).build();
		betPredictionAggregateRepository.save(bet);

		var docCount = betPredictionAggregateRepository.count();
		Assertions.assertEquals(1, docCount);

		List<BetPredictionQualityAllBetsAggregate> measuredPredictionCntAggregates = predictionQualityService.matchPredictionQualityMeasurementCounts(
			NO_REVISION);
		Assertions.assertEquals(3, measuredPredictionCntAggregates.size());
		BetPredictionQualityAllBetsAggregate aggregate = measuredPredictionCntAggregates.stream().filter(m -> m.bet().equals(Bet.BTTS_YES)).findAny()
			.get();
		Assertions.assertEquals(Bet.BTTS_YES, aggregate.bet());
		Assertions.assertEquals(13L, aggregate.assessed());
		Assertions.assertEquals(10L, aggregate.betSuccess());
		Assertions.assertEquals(3L, aggregate.betFailed());
	}

	@Test
	void bet_multiple_measurements_counts() {
		BetPredictionQuality bet = BetPredictionQuality.builder().predictionPercent(53).betSucceeded(10L).betFailed(3L).bet(Bet.BTTS_YES)
			.revision(NO_REVISION).build();
		betPredictionAggregateRepository.save(bet);
		BetPredictionQuality dontbet = BetPredictionQuality.builder().predictionPercent(45).betSucceeded(11L).betFailed(4L).bet(Bet.BTTS_YES)
			.revision(NO_REVISION).build();
		betPredictionAggregateRepository.save(dontbet);

		var docCount = betPredictionAggregateRepository.count();
		Assertions.assertEquals(2, docCount);

		List<BetPredictionQualityAllBetsAggregate> measuredPredictionCntAggregates = predictionQualityService.matchPredictionQualityMeasurementCounts(
			NO_REVISION);
		BetPredictionQualityAllBetsAggregate aggregate = measuredPredictionCntAggregates.stream().filter(m -> m.bet().equals(Bet.BTTS_YES)).findAny()
			.get();
		Assertions.assertEquals(Bet.BTTS_YES, aggregate.bet());
		Assertions.assertEquals(28L, aggregate.assessed());
		Assertions.assertEquals(10L, aggregate.betSuccess());
		Assertions.assertEquals(3L, aggregate.betFailed());
		Assertions.assertEquals(11L, aggregate.dontBetSuccess());
		Assertions.assertEquals(4L, aggregate.dontBetFailed());
	}

	@Test
	void bet_multiple_measurements_different_predictions_counts() {
		BetPredictionQuality bet = BetPredictionQuality.builder().predictionPercent(53).betSucceeded(10L).betFailed(3L).bet(Bet.BTTS_YES)
			.revision(NO_REVISION).build();
		betPredictionAggregateRepository.save(bet);

		bet = BetPredictionQuality.builder().predictionPercent(68).betSucceeded(11L).betFailed(4L).bet(Bet.BTTS_YES).revision(NO_REVISION).build();
		betPredictionAggregateRepository.save(bet);

		BetPredictionQuality dontbet = BetPredictionQuality.builder().predictionPercent(45).betSucceeded(11L).betFailed(4L).bet(Bet.BTTS_YES)
			.revision(NO_REVISION).build();
		betPredictionAggregateRepository.save(dontbet);

		dontbet = BetPredictionQuality.builder().predictionPercent(45).betSucceeded(12L).betFailed(5L).bet(Bet.BTTS_YES).revision(NO_REVISION)
			.build();
		betPredictionAggregateRepository.save(dontbet);

		var docCount = betPredictionAggregateRepository.count();
		Assertions.assertEquals(4, docCount);

		List<BetPredictionQualityAllBetsAggregate> measuredPredictionCntAggregates = predictionQualityService.matchPredictionQualityMeasurementCounts(
			NO_REVISION);
		BetPredictionQualityAllBetsAggregate aggregate = measuredPredictionCntAggregates.stream().filter(m -> m.bet().equals(Bet.BTTS_YES)).findAny()
			.get();
		Assertions.assertEquals(Bet.BTTS_YES, aggregate.bet());
		Assertions.assertEquals(60L, aggregate.assessed());
		Assertions.assertEquals(21L, aggregate.betSuccess());
		Assertions.assertEquals(7L, aggregate.betFailed());
		Assertions.assertEquals(23L, aggregate.dontBetSuccess());
		Assertions.assertEquals(9L, aggregate.dontBetFailed());
	}

	@Test
	void bet_multiple_measurements_and_bettypes_counts() {
		BetPredictionQuality bet = BetPredictionQuality.builder().predictionPercent(53).betSucceeded(10L).betFailed(3L).bet(Bet.BTTS_YES)
			.revision(NO_REVISION).build();
		betPredictionAggregateRepository.save(bet);

		BetPredictionQuality dontbet = BetPredictionQuality.builder().predictionPercent(45).betSucceeded(11L).betFailed(4L).bet(Bet.BTTS_YES)
			.revision(NO_REVISION).build();
		betPredictionAggregateRepository.save(dontbet);

		bet = BetPredictionQuality.builder().predictionPercent(53).betSucceeded(20L).betFailed(5L).bet(Bet.OVER_ZERO_FIVE).revision(NO_REVISION)
			.build();
		betPredictionAggregateRepository.save(bet);

		dontbet = BetPredictionQuality.builder().predictionPercent(45).betSucceeded(21L).betFailed(6L).bet(Bet.OVER_ZERO_FIVE).revision(NO_REVISION)
			.build();
		betPredictionAggregateRepository.save(dontbet);

		var docCount = betPredictionAggregateRepository.count();
		Assertions.assertEquals(4, docCount);

		List<BetPredictionQualityAllBetsAggregate> measuredPredictionCntAggregates = predictionQualityService.matchPredictionQualityMeasurementCounts(
			NO_REVISION);
		BetPredictionQualityAllBetsAggregate aggregate = measuredPredictionCntAggregates.stream().filter(m -> m.bet().equals(Bet.BTTS_YES)).findAny()
			.get();
		Assertions.assertEquals(Bet.BTTS_YES, aggregate.bet());
		Assertions.assertEquals(28L, aggregate.assessed());
		Assertions.assertEquals(10L, aggregate.betSuccess());
		Assertions.assertEquals(3L, aggregate.betFailed());
		Assertions.assertEquals(11L, aggregate.dontBetSuccess());
		Assertions.assertEquals(4L, aggregate.dontBetFailed());

		aggregate = measuredPredictionCntAggregates.stream().filter(m -> m.bet().equals(Bet.OVER_ZERO_FIVE)).findAny().get();
		Assertions.assertEquals(Bet.OVER_ZERO_FIVE, aggregate.bet());
		Assertions.assertEquals(52L, aggregate.assessed());
		Assertions.assertEquals(20L, aggregate.betSuccess());
		Assertions.assertEquals(5L, aggregate.betFailed());
		Assertions.assertEquals(21L, aggregate.dontBetSuccess());
		Assertions.assertEquals(6L, aggregate.dontBetFailed());
	}

	@Test
	void influencer_aggregate_none_with_same_prediction_percent() {
		String testinfluencer = "testinfluencer";
		String testinfluencer2 = "testinfluencer2";
		var ipd = new InfluencerPercentDistribution(34, 20L, 0L, testinfluencer, PrecheckResult.OK);
		var ipd2_1 = new InfluencerPercentDistribution(35, 23L, 0L, testinfluencer2, PrecheckResult.OK);
		BetPredictionQuality bet = BetPredictionQuality.builder().predictionPercent(53).betSucceeded(10L).betFailed(3L).bet(Bet.BTTS_YES)
			.revision(NO_REVISION).influencerDistribution(List.of(ipd, ipd2_1)).build();
		betPredictionAggregateRepository.save(bet);

		ipd = new InfluencerPercentDistribution(38, 23L, 0L, testinfluencer, PrecheckResult.OK);
		bet = BetPredictionQuality.builder().predictionPercent(78).betSucceeded(10L).betFailed(3L).bet(Bet.BTTS_YES).revision(NO_REVISION)
			.influencerDistribution(List.of(ipd)).build();
		betPredictionAggregateRepository.save(bet);

		final Map<String, List<BetPredictionQualityInfluencerAggregate>> influencerPredictionsAggregated = predictionQualityService.influencerPredictionsAggregated(
			Bet.BTTS_YES, NO_REVISION);
		Assertions.assertNotNull(influencerPredictionsAggregated);

		Assertions.assertEquals(2, influencerPredictionsAggregated.keySet().size(),
			"In case of place bet we have two bet prediction qualities and two involved influencer");

		List<BetPredictionQualityInfluencerAggregate> inf1Measurements = influencerPredictionsAggregated.get(testinfluencer);
		Assertions.assertEquals(2, inf1Measurements.size(), "Influencer " + testinfluencer
			+ " was involved in two bet predictions that said bet on this but influencer returned different prediction results.");
		assertBetPredQualInfluencerAggregate(testinfluencer, influencerPredictionsAggregated, 34, 20L, 0L);
		assertBetPredQualInfluencerAggregate(testinfluencer, influencerPredictionsAggregated, 38, 23L, 0L);

		List<BetPredictionQualityInfluencerAggregate> inf2Measurements = influencerPredictionsAggregated.get(testinfluencer2);
		Assertions.assertEquals(1, inf2Measurements.size(),
			"Influencer " + testinfluencer2 + "was involved in one bet prediction that said bet on this");
		assertBetPredQualInfluencerAggregate(testinfluencer2, influencerPredictionsAggregated, 35, 23L, 0L);
	}

	@Test
	void influencer_aggregate_all_with_same_prediction_percent() {
		String testinfluencer = "testinfluencer";
		String testinfluencer2 = "testinfluencer2";
		var ipd = new InfluencerPercentDistribution(34, 20L, 2L, testinfluencer, PrecheckResult.OK);
		var ipd2_1 = new InfluencerPercentDistribution(35, 23L, 0L, testinfluencer2, PrecheckResult.OK);
		var bet = BetPredictionQuality.builder().predictionPercent(16).betSucceeded(34L).betFailed(22L).bet(Bet.BTTS_YES).revision(NO_REVISION)
			.influencerDistribution(List.of(ipd, ipd2_1)).build();
		betPredictionAggregateRepository.save(bet);

		bet = BetPredictionQuality.builder().predictionPercent(49).betSucceeded(13L).betFailed(2L).bet(Bet.BTTS_YES).revision(NO_REVISION)
			.influencerDistribution(List.of(ipd, ipd2_1)).build();
		betPredictionAggregateRepository.save(bet);

		final Map<String, List<BetPredictionQualityInfluencerAggregate>> influencerPredictionsAggregated = predictionQualityService.influencerPredictionsAggregated(
			Bet.BTTS_YES, NO_REVISION);
		Assertions.assertNotNull(influencerPredictionsAggregated);
		Assertions.assertEquals(2, influencerPredictionsAggregated.keySet().size(),
			"In case of don't place bet we have three bet prediction qualities and two involved influencer");

		assertBetPredQualInfluencerAggregate(testinfluencer, influencerPredictionsAggregated, 34, 40L, 4L);
		assertBetPredQualInfluencerAggregate(testinfluencer2, influencerPredictionsAggregated, 35, 46L, 0L);
	}

	private void assertBetPredQualInfluencerAggregate(String influencerName, Map<String, List<BetPredictionQualityInfluencerAggregate>> results,
		int expPredictionPercent, long expCountSucceeded, long expCountFailed) {
		final List<BetPredictionQualityInfluencerAggregate> influencerAggregates = results.get(influencerName);
		Assertions.assertNotNull(influencerAggregates);
		Assertions.assertFalse(influencerAggregates.isEmpty());
		BetPredictionQualityInfluencerAggregate influencerAggregate = influencerAggregates.stream()
			.filter(i -> i.predictionPercent().equals(expPredictionPercent)).findAny().get();
		Assertions.assertEquals(expCountSucceeded, influencerAggregate.betSucceeded());
		Assertions.assertEquals(expCountFailed, influencerAggregate.betFailed());
	}
}
