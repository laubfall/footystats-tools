package de.footystats.tools.services.prediction.outcome;

import de.footystats.tools.services.prediction.quality.BetPredictionQuality;
import de.footystats.tools.services.prediction.quality.BetPredictionQualityRepository;
import de.footystats.tools.services.prediction.quality.PredictionQualityRevision;
import de.footystats.tools.services.prediction.quality.PredictionQualityService;
import de.footystats.tools.services.prediction.quality.view.BetPredictionQualityInfluencerAggregate;
import de.footystats.tools.services.prediction.quality.view.PredictionQualityViewService;
import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.InfluencerResult;
import de.footystats.tools.services.prediction.PredictionResult;
import de.footystats.tools.services.prediction.PredictionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@Scope("prototype")
// Service holds a cache that depends on data that can change during runtime. So create it every time the service is injected.
public class StatisticalResultOutcomeService {

	private final BetPredictionQualityRepository betPredictionAggregateRepository;

	private final PredictionQualityService predictionQualityService;

	private final PredictionQualityViewService predictionQualityViewService;

	private final Map<InfluencerPredictionCacheKey, Map<String, List<BetPredictionQualityInfluencerAggregate>>> influencerPredictionCache = new HashMap<>();

	public StatisticalResultOutcomeService(BetPredictionQualityRepository betPredictionAggregateRepository, PredictionQualityService predictionQualityService, PredictionQualityViewService predictionQualityViewService) {
		this.betPredictionAggregateRepository = betPredictionAggregateRepository;
		this.predictionQualityService = predictionQualityService;
		this.predictionQualityViewService = predictionQualityViewService;
	}

	public StatisticalResultOutcome compute(final PredictionResult result, Bet bet) {
		final Double matchStatisticalOutcome = calcMatchPredictionStatisticalOutcome(result, bet);
		if (matchStatisticalOutcome == null) return null;

		return new StatisticalResultOutcome(bet, matchStatisticalOutcome, calcInfluencerStatisticalOutcome(result, bet));
	}

	private List<InfluencerStatisticalResultOutcome> calcInfluencerStatisticalOutcome(final PredictionResult result, final Bet bet) {
		List<InfluencerStatisticalResultOutcome> influencerOutcomes = new ArrayList<>();
		for (InfluencerResult influencerResult : result.influencerDetailedResult()) {
			final List<BetPredictionQualityInfluencerAggregate> aggregates = influencerDistributionByCache(bet, result, influencerResult.influencerName());
			final Optional<BetPredictionQualityInfluencerAggregate> measured = aggregates.stream().filter(a -> a.predictionPercent().equals(influencerResult.influencerPredictionValue())).findFirst();
			if (measured.isPresent()) {
				BetPredictionQualityInfluencerAggregate influencerAggregate = measured.get();
				var succeeded = influencerAggregate.betSucceeded().doubleValue();
				var failed = influencerAggregate.betFailed().doubleValue();
				influencerOutcomes.add(new InfluencerStatisticalResultOutcome(influencerResult.influencerName(), succeeded / (succeeded + failed)));
			}
		}

		return influencerOutcomes;
	}

	/**
	 * Checks the cache for influencer calculations for one specific prediction result and one type of bet.
	 * @param bet Mandatory. The type of bet we are looking for a specific prediction result.
	 * @param result Mandatory. The result we are looking for.
	 * @param influencerName Mandatory. The name of the influencer we are looking for.
	 * @return List of prediction values the influencer calculated. Empty list if for the chosen bet and prediction
	 * result the influencer did not made any predictions.
	 */
	private List<BetPredictionQualityInfluencerAggregate> influencerDistributionByCache(Bet bet, PredictionResult result, String influencerName) {
		var cacheKey = new InfluencerPredictionCacheKey(bet, result.betOnThis());
		Map<String, List<BetPredictionQualityInfluencerAggregate>> matching;
		if (influencerPredictionCache.containsKey(cacheKey)) {
			matching = influencerPredictionCache.get(cacheKey);
		} else {
			matching = predictionQualityViewService.influencerPredictionsAggregated(bet, result.betOnThis(), predictionQualityService.latestRevision());
			influencerPredictionCache.put(cacheKey, matching);
		}

		var influencerPredicitons = matching.get(influencerName);
		if(influencerPredicitons == null){
			return Collections.emptyList();
		}

		return influencerPredicitons;
	}

	private Double calcMatchPredictionStatisticalOutcome(PredictionResult result, Bet bet) {
		PredictionQualityRevision latest = predictionQualityService.latestRevision();
		BetPredictionQuality aggregate = betPredictionAggregateRepository.findByBetAndPredictionPercentAndRevision(bet, result.betSuccessInPercent(), latest);
		if (aggregate == null) {
			// may happen if the predicted result does not exist cause there was no completed match with the same prediction value before.
			return null;
		}

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
}
