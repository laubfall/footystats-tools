package de.ludwig.footystats.tools.backend.services.prediction.outcome;

import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import de.ludwig.footystats.tools.backend.services.prediction.InfluencerResult;
import de.ludwig.footystats.tools.backend.services.prediction.PredictionResult;
import de.ludwig.footystats.tools.backend.services.prediction.PredictionService;
import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQuality;
import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQualityRepository;
import de.ludwig.footystats.tools.backend.services.prediction.quality.view.BetPredictionQualityInfluencerAggregate;
import de.ludwig.footystats.tools.backend.services.prediction.quality.view.PredictionQualityViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class StatisticalResultOutcomeService {

	private final BetPredictionQualityRepository betPredictionAggregateRepository;

	private final PredictionQualityViewService predictionQualityViewService;

	public StatisticalResultOutcomeService(BetPredictionQualityRepository betPredictionAggregateRepository, PredictionQualityViewService predictionQualityViewService) {
		this.betPredictionAggregateRepository = betPredictionAggregateRepository;
		this.predictionQualityViewService = predictionQualityViewService;
	}

	public StatisticalResultOutcome compute(final PredictionResult result, Bet bet) {

		final Double matchStatisticalOutcome = calcMatchPredictionStatisticalOutcome(result, bet);
		if (matchStatisticalOutcome == null) return null;

		return new StatisticalResultOutcome(bet, matchStatisticalOutcome, calcInfluencerStatisticalOutcome(result, bet));
	}

	private List<InfluencerStatisticalResultOutcome> calcInfluencerStatisticalOutcome(final PredictionResult result, final Bet bet){
		// TODO Refactor this so the this call is not made anytime this method is executed.
		Map<String, List<BetPredictionQualityInfluencerAggregate>> influencerResults = predictionQualityViewService.influencerPredictionsAggregated(bet, result.betOnThis());
		List<InfluencerStatisticalResultOutcome> influencerOutcomes = new ArrayList<>();
		for (InfluencerResult influencerResult : result.influencerDetailedResult()) {
			final List<BetPredictionQualityInfluencerAggregate> aggregates = influencerResults.get(influencerResult.influencerName());
			Optional<BetPredictionQualityInfluencerAggregate> measured = aggregates.stream().filter(a -> a.predictionPercent().equals(influencerResult.influencerPredictionValue())).findFirst();
			if(measured.isPresent()){
				BetPredictionQualityInfluencerAggregate influencerAggregate = measured.get();
				var succeeded = influencerAggregate.betSucceeded().doubleValue();
				var failed = influencerAggregate.betFailed().doubleValue();
				influencerOutcomes.add(new InfluencerStatisticalResultOutcome(influencerResult.influencerName(), succeeded / (succeeded+failed)));
			}
		}

		return influencerOutcomes;
	}

	private Double calcMatchPredictionStatisticalOutcome(PredictionResult result, Bet bet) {
		BetPredictionQuality aggregate = betPredictionAggregateRepository.findByBetAndPredictionPercent(bet, result.betSuccessInPercent());
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
}
