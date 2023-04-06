package de.ludwig.footystats.tools.backend.services.prediction.outcome;

import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import de.ludwig.footystats.tools.backend.services.prediction.PredictionResult;
import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQuality;
import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQualityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class StatisticalResultOutcomeService {

	private BetPredictionQualityRepository betPredictionAggregateRepository;

	public StatisticalResultOutcomeService(BetPredictionQualityRepository betPredictionAggregateRepository) {
		this.betPredictionAggregateRepository = betPredictionAggregateRepository;
	}

	public StatisticalResultOutcome compute(final PredictionResult result, Bet bet) {

		BetPredictionQuality aggregate = betPredictionAggregateRepository.findByBetAndPredictionPercent(bet, result.betSuccessInPercent());
		if (aggregate == null) {
			// may happen if the predicted result does not exist cause there was no completed match with the same prediction value before.
			return null;
		}

		double matchStatisticalOutcome;
		if (result.betSuccessInPercent() >= 50) {
			matchStatisticalOutcome = calcStatisticalMatchOutcome(aggregate.getBetSucceeded(), aggregate.getBetFailed());
		} else {
			matchStatisticalOutcome = calcStatisticalMatchOutcome(aggregate.getBetFailed(), aggregate.getBetSucceeded());
		}

		return new StatisticalResultOutcome(bet, matchStatisticalOutcome, new ArrayList<>(0));
	}

	private double calcStatisticalMatchOutcome(Long success, Long failed) {
		return (double) success / (double) (success + failed);
	}
}
