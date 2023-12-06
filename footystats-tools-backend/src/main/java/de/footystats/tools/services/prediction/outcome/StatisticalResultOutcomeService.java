package de.footystats.tools.services.prediction.outcome;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.InfluencerResult;
import de.footystats.tools.services.prediction.PrecheckResult;
import de.footystats.tools.services.prediction.PredictionResult;
import de.footystats.tools.services.prediction.PredictionService;
import de.footystats.tools.services.prediction.quality.BetPredictionQuality;
import de.footystats.tools.services.prediction.quality.BetPredictionQualityRepository;
import de.footystats.tools.services.prediction.quality.PredictionQualityRevision;
import de.footystats.tools.services.prediction.quality.PredictionQualityService;
import de.footystats.tools.services.prediction.quality.view.BetPredictionQualityBetAggregate;
import de.footystats.tools.services.prediction.quality.view.BetPredictionQualityInfluencerAggregate;
import de.footystats.tools.services.prediction.quality.view.PredictionQualityViewService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Scope("prototype")
// Service holds a cache that depends on data that can change during runtime. So create it every time the service is injected.
public class StatisticalResultOutcomeService {

	private final BetPredictionQualityRepository betPredictionAggregateRepository;

	private final PredictionQualityService predictionQualityService;

	private final PredictionQualityViewService predictionQualityViewService;

	// Replace with Spring cache?
	private final Map<InfluencerPredictionCacheKey, Map<String, List<BetPredictionQualityInfluencerAggregate>>> influencerPredictionCache = new HashMap<>();

	/**
	 * Constructor.
	 *
	 * @param betPredictionAggregateRepository The repository for the bet prediction aggregate.
	 * @param predictionQualityService         The prediction quality service.
	 * @param predictionQualityViewService     The prediction quality view service.
	 */
	public StatisticalResultOutcomeService(BetPredictionQualityRepository betPredictionAggregateRepository,
		PredictionQualityService predictionQualityService, PredictionQualityViewService predictionQualityViewService) {
		this.betPredictionAggregateRepository = betPredictionAggregateRepository;
		this.predictionQualityService = predictionQualityService;
		this.predictionQualityViewService = predictionQualityViewService;
	}

	/**
	 * Calculates the statistical outcome of the match prediction. That means the statistical probability of the prediction.
	 *
	 * @param result The prediction result. Maybe null if the match was not played yet.
	 * @param bet    The bet the prediction was made for.
	 * @return The statistical outcome of the match prediction. Maybe null if the match was not played yet.
	 */
	public StatisticalResultOutcome compute(final PredictionResult result, Bet bet) {
		// If result is null that means that the match was not played yet. So we could not compute the prediction and the statistical outcome.
		if (result == null) {
			return null;
		}

		final Double matchStatisticalOutcome = calcMatchPredictionStatisticalOutcome(result, bet);
		if (matchStatisticalOutcome == null) {
			return null;
		}

		final PredictionQualityRevision predictionQualityRevision = predictionQualityService.latestRevision();
		return new StatisticalResultOutcome(bet, matchStatisticalOutcome, calcBetRanking(bet, result, predictionQualityRevision),
			calcInfluencerStatisticalOutcome(result, bet));
	}

	private List<InfluencerStatisticalResultOutcome> calcInfluencerStatisticalOutcome(final PredictionResult result, final Bet bet) {
		var latestRevision = predictionQualityService.latestRevision();
		List<InfluencerStatisticalResultOutcome> influencerOutcomes = new ArrayList<>();
		for (InfluencerResult influencerResult : result.influencerDetailedResult()) {
			if (!PrecheckResult.OK.equals(influencerResult.precheckResult())) {
				continue;
			}
			final List<BetPredictionQualityInfluencerAggregate> aggregates = influencerDistributionByCache(bet, result,
				influencerResult.influencerName());
			final Optional<BetPredictionQualityInfluencerAggregate> measured = aggregates.stream()
				.filter(a -> a.predictionPercent().equals(influencerResult.influencerPredictionValue())).findFirst();
			if (measured.isPresent()) {
				BetPredictionQualityInfluencerAggregate influencerAggregate = measured.get();
				var succeeded = influencerAggregate.betSucceeded();
				var failed = influencerAggregate.betFailed();
				influencerOutcomes.add(
					new InfluencerStatisticalResultOutcome(influencerResult.influencerName(), calcStatisticalMatchOutcome(succeeded, failed),
						calcInfluencerRanking(bet, influencerResult, latestRevision)));
			}
		}

		return influencerOutcomes;
	}

	Ranking calcInfluencerRanking(Bet bet, InfluencerResult result, PredictionQualityRevision revision) {
		final var influencerStatisticalOutcomeBet = predictionQualityViewService.influencerPredictionsAggregated(bet,
			true, revision);
		final var influencerStatisticalOutcomeDonBet = predictionQualityViewService.influencerPredictionsAggregated(bet,
			false, revision);

		List<IntermediateInfluencerBetAggregate> betIntermediate = new ArrayList<>(0);
		if (influencerStatisticalOutcomeBet.containsKey(result.influencerName())) {
			betIntermediate = influencerStatisticalOutcomeBet.get(result.influencerName()).stream()
				.map(a -> new IntermediateInfluencerBetAggregate(a,
					a.predictionPercent(), true)).toList();
		}

		List<IntermediateInfluencerBetAggregate> dontBetIntermediate = new ArrayList<>(0);
		if (influencerStatisticalOutcomeDonBet.containsKey(result.influencerName())) {
			dontBetIntermediate = influencerStatisticalOutcomeDonBet.get(result.influencerName()).stream()
				.map(a -> new IntermediateInfluencerBetAggregate(a, a.predictionPercent(), false)).toList();
		}

		List<IntermediateInfluencerBetAggregate> combined = new ArrayList<>(betIntermediate);
		combined.addAll(dontBetIntermediate);
		Map<Integer, List<IntermediateInfluencerBetAggregate>> groupPerPredictionPercent = combined.stream()
			.collect(Collectors.groupingBy(IntermediateInfluencerBetAggregate::keyPredictionPercent));

		List<IRanked> aggregated = new ArrayList<>();
		groupPerPredictionPercent.values().forEach(value -> {
			if (value.size() == 2) {
				var one = value.get(0);
				var two = value.get(1);
				long aggCntOne = one.aggregate.betSucceeded() + two.aggregate.betFailed();
				long aggCntTwo = one.aggregate.betFailed() + two.aggregate.betSucceeded();
				aggregated.add(new BetPredictionQualityInfluencerAggregate(one.aggregate.influencerName(),
					one.aggregate.predictionPercent(),
					one.betOnThis ? aggCntOne : aggCntTwo,
					one.betOnThis ? aggCntTwo : aggCntOne)
				);
			} else {
				aggregated.add(value.get(0).aggregate);
			}
		});

		return calcRanking(aggregated, result.influencerPredictionValue());
	}

	Ranking calcBetRanking(Bet bet, PredictionResult result, PredictionQualityRevision revision) {

		final var betAggregates = predictionQualityViewService.betPredictionQuality(bet, revision);
		// Invert the bet aggregates for the don't bet prediction without this swap the calculation of
		// the statistical outcome would be wrong.
		final var dontBetAggregates = predictionQualityViewService.dontBetPredictionQuality(bet, revision);

		final var all = new ArrayList<BetPredictionQualityBetAggregate>();
		all.addAll(betAggregates);
		all.addAll(dontBetAggregates);

		if (all.isEmpty()) {
			return null;
		}

		return calcRanking(new ArrayList<>(all), result.betSuccessInPercent());
	}

	private Ranking calcRanking(Collection<IRanked> betAggs, Integer calculatedPredictionPercent) {
		final List<IntermediateRankingInfo> rankings = betAggs.stream()
			.map(a -> new IntermediateRankingInfo(calcStatisticalMatchOutcome(a.betSucceeded(), a.betFailed()), a.predictionPercent()))
			.toList()
			.reversed();

		final Optional<IntermediateRankingInfo> optIntermediateRankingInfo = rankings.stream()
			.filter(i -> i.predictionPercent == calculatedPredictionPercent).findFirst();

		if (optIntermediateRankingInfo.isEmpty()) {
			return null;
		}

		List<Double> sortedStatisticalOutcome = rankings.stream().collect(Collectors.groupingBy(IntermediateRankingInfo::statisticalOutcome)).keySet()
			.stream().sorted()
			.toList().reversed();

		IntermediateRankingInfo intermediateRankingInfo = optIntermediateRankingInfo.get();
		int pos = sortedStatisticalOutcome.indexOf(intermediateRankingInfo.statisticalOutcome) + 1;

		float b10 = ((float) pos / sortedStatisticalOutcome.size()) * 100;
		return new Ranking(pos, sortedStatisticalOutcome.size(), b10 <= 10, b10 <= 20);
	}

	/**
	 * Checks the cache for influencer calculations for one specific prediction result and one type of bet.
	 *
	 * @param bet            Mandatory. The type of bet we are looking for a specific prediction result.
	 * @param result         Mandatory. The result we are looking for.
	 * @param influencerName Mandatory. The name of the influencer we are looking for.
	 * @return List of prediction values the influencer calculated. Empty list if for the chosen bet and prediction result the influencer did not made
	 * any predictions.
	 */
	private List<BetPredictionQualityInfluencerAggregate> influencerDistributionByCache(Bet bet, PredictionResult result, String influencerName) {
		var cacheKey = new InfluencerPredictionCacheKey(bet, result.betOnThis());
		Map<String, List<BetPredictionQualityInfluencerAggregate>> matching;
		if (influencerPredictionCache.containsKey(cacheKey)) {
			matching = influencerPredictionCache.get(cacheKey);
		} else {
			matching = predictionQualityViewService.influencerPredictionsAggregated(bet, result.betOnThis(),
				predictionQualityService.latestRevision());
			influencerPredictionCache.put(cacheKey, matching);
		}

		var influencerPredictions = matching.get(influencerName);
		if (influencerPredictions == null) {
			return Collections.emptyList();
		}

		return influencerPredictions;
	}

	private Double calcMatchPredictionStatisticalOutcome(PredictionResult result, Bet bet) {
		PredictionQualityRevision latest = predictionQualityService.latestRevision();
		BetPredictionQuality aggregate = betPredictionAggregateRepository.findByBetAndPredictionPercentAndRevision(bet, result.betSuccessInPercent(),
			latest);
		if (aggregate == null) {
			// may happen if the predicted result does not exist cause there was no completed match with the same prediction value before.
			return null;
		}
		return calcStatisticalMatchOutcome(result, aggregate);
	}

	/**
	 * This method takes care about the fact that in case of a don't bet prediction the statistical outcome is inverted, means that successful don't
	 * bet predictions are actually failed bet predictions.
	 *
	 * @param result    The prediction result.
	 * @param aggregate The aggregate of the prediction result.
	 * @return The statistical outcome of the match prediction.
	 */
	private double calcStatisticalMatchOutcome(PredictionResult result, BetPredictionQuality aggregate) {
		double matchStatisticalOutcome;
		if (result.betSuccessInPercent() >= PredictionService.LOWER_EXCLUSIVE_BORDER_BET_ON_THIS) {
			matchStatisticalOutcome = calcStatisticalMatchOutcome(aggregate.getBetSucceeded(), aggregate.getBetFailed());
		} else {
			matchStatisticalOutcome = calcStatisticalMatchOutcome(aggregate.getBetFailed(), aggregate.getBetSucceeded());
		}
		return matchStatisticalOutcome;
	}

	private double calcStatisticalMatchOutcome(Long success, Long failed) {
		return (double) success / (double) (success + failed);
	}

	record InfluencerPredictionCacheKey(Bet bet, boolean betOnThis) {

	}

	record IntermediateRankingInfo(double statisticalOutcome, int predictionPercent) {

	}

	record IntermediateInfluencerBetAggregate(BetPredictionQualityInfluencerAggregate aggregate, int keyPredictionPercent, boolean betOnThis) {

	}
}
