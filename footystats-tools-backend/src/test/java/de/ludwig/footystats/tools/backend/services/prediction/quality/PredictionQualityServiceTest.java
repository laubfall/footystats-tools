package de.ludwig.footystats.tools.backend.services.prediction.quality;

import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import de.ludwig.footystats.tools.backend.services.prediction.InfluencerPercentDistribution;
import de.ludwig.footystats.tools.backend.services.prediction.PrecheckResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

@ActiveProfiles("test")
@SpringBootTest
public class PredictionQualityServiceTest {
	@Autowired
	private PredictionQualityService predictionQualityService;

	@Autowired
	private BetPredictionQualityRepository betPredictionAggregateRepository;

	@BeforeEach
	public void cleanup() {
		betPredictionAggregateRepository.deleteAll();
	}

	@Test
	public void bet_measurement_counts() {
		BetPredictionQuality bet = new BetPredictionQuality.BetPredictionQualityBuilder().predictionPercent(53).betSucceeded(10L).betFailed(3L).bet(Bet.BTTS_YES).revision(PredictionQualityRevision.NO_REVISION).build();
		betPredictionAggregateRepository.save(bet);

		var docCount = betPredictionAggregateRepository.count();
		Assertions.assertEquals(1, docCount);

		List<BetPredictionQualityAllBetsAggregate> measuredPredictionCntAggregates = predictionQualityService.matchPredictionQualityMeasurementCounts();
		Assertions.assertEquals(Bet.values().length, measuredPredictionCntAggregates.size());
		BetPredictionQualityAllBetsAggregate aggregate = measuredPredictionCntAggregates.stream().filter(m -> m.bet().equals(Bet.BTTS_YES)).findAny().get();
		Assertions.assertEquals(Bet.BTTS_YES, aggregate.bet());
		Assertions.assertEquals(13L, aggregate.assessed());
		Assertions.assertEquals(10L, aggregate.betSuccess());
		Assertions.assertEquals(3L, aggregate.betFailed());
	}

	@Test
	public void bet_multiple_measurements_counts() {
		BetPredictionQuality bet = new BetPredictionQuality.BetPredictionQualityBuilder().predictionPercent(53).betSucceeded(10L).betFailed(3L).bet(Bet.BTTS_YES).revision(PredictionQualityRevision.NO_REVISION).build();
		betPredictionAggregateRepository.save(bet);

		BetPredictionQuality dontbet = new BetPredictionQuality.BetPredictionQualityBuilder().predictionPercent(45).betSucceeded(11L).betFailed(4L).bet(Bet.BTTS_YES).revision(PredictionQualityRevision.NO_REVISION).build();
		betPredictionAggregateRepository.save(dontbet);

		var docCount = betPredictionAggregateRepository.count();
		Assertions.assertEquals(2, docCount);

		List<BetPredictionQualityAllBetsAggregate> measuredPredictionCntAggregates = predictionQualityService.matchPredictionQualityMeasurementCounts();
		BetPredictionQualityAllBetsAggregate aggregate = measuredPredictionCntAggregates.stream().filter(m -> m.bet().equals(Bet.BTTS_YES)).findAny().get();
		Assertions.assertEquals(Bet.BTTS_YES, aggregate.bet());
		Assertions.assertEquals(28L, aggregate.assessed());
		Assertions.assertEquals(10L, aggregate.betSuccess());
		Assertions.assertEquals(3L, aggregate.betFailed());
		Assertions.assertEquals(11L, aggregate.dontBetSuccess());
		Assertions.assertEquals(4L, aggregate.dontBetFailed());
	}

	@Test
	public void bet_multiple_measurements_different_predictions_counts() {
		BetPredictionQuality bet = new BetPredictionQuality.BetPredictionQualityBuilder().predictionPercent(53).betSucceeded(10L).betFailed(3L).bet(Bet.BTTS_YES).revision(PredictionQualityRevision.NO_REVISION).build();
		betPredictionAggregateRepository.save(bet);

		bet = new BetPredictionQuality.BetPredictionQualityBuilder().predictionPercent(68).betSucceeded(11L).betFailed(4L).bet(Bet.BTTS_YES).revision(PredictionQualityRevision.NO_REVISION).build();
		betPredictionAggregateRepository.save(bet);

		BetPredictionQuality dontbet = new BetPredictionQuality.BetPredictionQualityBuilder().predictionPercent(45).betSucceeded(11L).betFailed(4L).bet(Bet.BTTS_YES).revision(PredictionQualityRevision.NO_REVISION).build();
		betPredictionAggregateRepository.save(dontbet);

		dontbet = new BetPredictionQuality.BetPredictionQualityBuilder().predictionPercent(45).betSucceeded(12L).betFailed(5L).bet(Bet.BTTS_YES).revision(PredictionQualityRevision.NO_REVISION).build();
		betPredictionAggregateRepository.save(dontbet);

		var docCount = betPredictionAggregateRepository.count();
		Assertions.assertEquals(4, docCount);

		List<BetPredictionQualityAllBetsAggregate> measuredPredictionCntAggregates = predictionQualityService.matchPredictionQualityMeasurementCounts();
		BetPredictionQualityAllBetsAggregate aggregate = measuredPredictionCntAggregates.stream().filter(m -> m.bet().equals(Bet.BTTS_YES)).findAny().get();
		Assertions.assertEquals(Bet.BTTS_YES, aggregate.bet());
		Assertions.assertEquals(60L, aggregate.assessed());
		Assertions.assertEquals(21L, aggregate.betSuccess());
		Assertions.assertEquals(7L, aggregate.betFailed());
		Assertions.assertEquals(23L, aggregate.dontBetSuccess());
		Assertions.assertEquals(9L, aggregate.dontBetFailed());
	}

	@Test
	public void bet_multiple_measurements_and_bettypes_counts() {
		BetPredictionQuality bet = new BetPredictionQuality.BetPredictionQualityBuilder().predictionPercent(53).betSucceeded(10L).betFailed(3L).bet(Bet.BTTS_YES).revision(PredictionQualityRevision.NO_REVISION).build();
		betPredictionAggregateRepository.save(bet);

		BetPredictionQuality dontbet = new BetPredictionQuality.BetPredictionQualityBuilder().predictionPercent(45).betSucceeded(11L).betFailed(4L).bet(Bet.BTTS_YES).revision(PredictionQualityRevision.NO_REVISION).build();
		betPredictionAggregateRepository.save(dontbet);

		bet = new BetPredictionQuality.BetPredictionQualityBuilder().predictionPercent(53).betSucceeded(20L).betFailed(5L).bet(Bet.OVER_ZERO_FIVE).revision(PredictionQualityRevision.NO_REVISION).build();
		betPredictionAggregateRepository.save(bet);

		dontbet = new BetPredictionQuality.BetPredictionQualityBuilder().predictionPercent(45).betSucceeded(21L).betFailed(6L).bet(Bet.OVER_ZERO_FIVE).revision(PredictionQualityRevision.NO_REVISION).build();
		betPredictionAggregateRepository.save(dontbet);

		var docCount = betPredictionAggregateRepository.count();
		Assertions.assertEquals(4, docCount);

		List<BetPredictionQualityAllBetsAggregate> measuredPredictionCntAggregates = predictionQualityService.matchPredictionQualityMeasurementCounts();
		BetPredictionQualityAllBetsAggregate aggregate = measuredPredictionCntAggregates.stream().filter(m -> m.bet().equals(Bet.BTTS_YES)).findAny().get();
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
	public void influencer_aggregate_one_influencer() {

		String testinfluencer = "testinfluencer";
		String testinfluencer2 = "testinfluencer2";
		var ipd = new InfluencerPercentDistribution(34, 20L, testinfluencer, PrecheckResult.OK);
		var ipd2_1 = new InfluencerPercentDistribution(35, 23L, testinfluencer2, PrecheckResult.OK);
		BetPredictionQuality bet = new BetPredictionQuality.BetPredictionQualityBuilder().predictionPercent(53).betSucceeded(10L).betFailed(3L).bet(Bet.BTTS_YES).revision(PredictionQualityRevision.NO_REVISION).influencerDistribution(List.of(ipd, ipd2_1)).build();
		betPredictionAggregateRepository.save(bet);

		var ipd2_2 = new InfluencerPercentDistribution(81, 12L, testinfluencer2, PrecheckResult.OK);
		bet = new BetPredictionQuality.BetPredictionQualityBuilder().predictionPercent(66).betSucceeded(34L).betFailed(22L).bet(Bet.BTTS_YES).revision(PredictionQualityRevision.NO_REVISION).influencerDistribution(List.of(ipd, ipd2_1, ipd2_2)).build();
		betPredictionAggregateRepository.save(bet);

		Map<String, List<BetPredictionQualityInfluencerAggregate>> influencerPredictionsAggregated = predictionQualityService.influencerPredictionsAggregated(Bet.BTTS_YES);
		Assertions.assertNotNull(influencerPredictionsAggregated);

		var influencerNameKeys = influencerPredictionsAggregated.keySet();
		Assertions.assertEquals(2, influencerNameKeys.size());

		List<BetPredictionQualityInfluencerAggregate> inf1Measurements = influencerPredictionsAggregated.get(testinfluencer);
		Assertions.assertEquals(1, inf1Measurements.size());

		List<BetPredictionQualityInfluencerAggregate> inf2Measurements = influencerPredictionsAggregated.get(testinfluencer2);
		Assertions.assertEquals(2, inf2Measurements.size());

		BetPredictionQualityInfluencerAggregate prediction_35 = inf2Measurements.stream().filter(i -> i.predictionPercent().equals(35)).findAny().get();
		Assertions.assertEquals(46, prediction_35.count());
		Assertions.assertEquals(44L, prediction_35.betSucceeded());
		Assertions.assertEquals(25L, prediction_35.betFailed());
	}
}
