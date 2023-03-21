package de.ludwig.footystats.tools.backend.services.prediction.outcome;

import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import de.ludwig.footystats.tools.backend.services.prediction.BetPredictionDistribution;
import de.ludwig.footystats.tools.backend.services.prediction.BetPredictionQuality;
import de.ludwig.footystats.tools.backend.services.prediction.PredictionResult;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityReport;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
@Service
@Scope("prototype")
public class StatisticalResultOutcomeService {
	private PredictionQualityReport report;

	private PredictionQualityReportRepository qualityReportRepository;

	public StatisticalResultOutcomeService(PredictionQualityReportRepository qualityReportRepository) {
		this.qualityReportRepository = qualityReportRepository;
	}

	public StatisticalResultOutcome compute(final PredictionResult result, Bet bet) {
		var currentReport = report();
		if (currentReport == null) {
			return null;
		}

		Optional<BetPredictionQuality> predictionQuality = currentReport.getMeasurements().stream().filter(bpq -> bpq.getBet().equals(bet)).findFirst();
		if (predictionQuality.isEmpty()) {
			return null;
		}

		double matchStatisticalOutcome;
		final Predicate<BetPredictionDistribution> predicateBetPredictionQuality = (betPredictionDistribution) -> betPredictionDistribution.getPredictionPercent().equals(result.betSuccessInPercent());
		if (result.betSuccessInPercent() >= 50) {
			var betOnThis = predictionQuality.get().getDistributionBetOnThis();
			var betOnThisFailed = predictionQuality.get().getDistributionBetOnThisFailed();
			var success = orDefault(betOnThis, predicateBetPredictionQuality, bet);
			var failed = orDefault(betOnThisFailed, predicateBetPredictionQuality, bet);
			matchStatisticalOutcome = calcStatisticalMatchOutcome(success.getCount(), failed.getCount());
		} else {
			var dontBetOnThis = predictionQuality.get().getDistributionDontBetOnThis();
			var dontBetOnThisFailed = predictionQuality.get().getDistributionDontBetOnThisFailed();
			var failed = orDefault(dontBetOnThis, predicateBetPredictionQuality, bet);
			var success = orDefault(dontBetOnThisFailed, predicateBetPredictionQuality, bet);
			matchStatisticalOutcome = calcStatisticalMatchOutcome(failed.getCount(), success.getCount());
		}

		return new StatisticalResultOutcome(bet, matchStatisticalOutcome, new ArrayList<>(0));
	}



	private BetPredictionDistribution orDefault(List<BetPredictionDistribution> all, Predicate<BetPredictionDistribution> filter, Bet bet) {
		Optional<BetPredictionDistribution> result = all.stream().filter(filter).findFirst();
		if (result.isEmpty()) {
			return new BetPredictionDistribution(0, 0l, new ArrayList<>(0));
		}

		return result.get();
	}

	private double calcStatisticalMatchOutcome(Long success, Long failed) {
		return (double) success / (double) (success + failed);
	}

	private PredictionQualityReport report() {
		if (report == null) {
			report = qualityReportRepository.findTopByOrderByRevisionDesc();
		}

		if (report == null) {
			log.info("Computing of statistical outcome per match not possible cause of missing quality report.");
		}

		return report;
	}
}
