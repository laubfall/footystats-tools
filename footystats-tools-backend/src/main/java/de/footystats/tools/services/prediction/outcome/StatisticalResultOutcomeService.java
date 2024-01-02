package de.footystats.tools.services.prediction.outcome;

import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.InfluencerResult;
import de.footystats.tools.services.prediction.PrecheckResult;
import de.footystats.tools.services.prediction.PredictionResult;
import de.footystats.tools.services.prediction.quality.BetPredictionQuality;
import de.footystats.tools.services.prediction.quality.BetPredictionQualityRepository;
import de.footystats.tools.services.prediction.quality.PredictionQualityRevision;
import de.footystats.tools.services.prediction.quality.PredictionQualityService;
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
			revision);

		List<IntermediateInfluencerBetAggregate> betIntermediate = new ArrayList<>(0);
		if (influencerStatisticalOutcomeBet.containsKey(result.influencerName())) {
			betIntermediate = influencerStatisticalOutcomeBet.get(result.influencerName()).stream()
				.map(a -> new IntermediateInfluencerBetAggregate(a,
					a.predictionPercent(), true)).toList();
		}

		List<IRanked> aggregated = new ArrayList<>();

		betIntermediate.stream().map(IntermediateInfluencerBetAggregate::aggregate).forEach(aggregated::add);

		return calcRanking(aggregated, result.influencerPredictionValue());
	}

	Ranking calcBetRanking(Bet bet, PredictionResult result, PredictionQualityRevision revision) {
		final var betAggregates = predictionQualityViewService.betPredictionQuality(bet, revision);
		return calcRanking(new ArrayList<>(betAggregates), result.betSuccessInPercent());
	}

	private Ranking calcRanking(Collection<IRanked> betAggs, Integer calculatedPredictionPercent) {
		final List<IntermediateRankingInfo> rankings = betAggs.stream()
			.map(ranked -> new IntermediateRankingInfo(calcStatisticalMatchOutcome(ranked.betSucceeded(), ranked.betFailed()),
				ranked.predictionPercent()))
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
			matching = predictionQualityViewService.influencerPredictionsAggregated(bet,
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
		return calcStatisticalMatchOutcome(aggregate);
	}

	private double calcStatisticalMatchOutcome(BetPredictionQuality aggregate) {
		return calcStatisticalMatchOutcome(aggregate.getBetSucceeded(), aggregate.getBetFailed());
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
