package de.footystats.tools.controller.quality;

import de.footystats.tools.controller.jobs.JobInformation;
import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.quality.PredictionQualityService;
import de.footystats.tools.services.prediction.quality.batch.IBetPredictionQualityJobService;
import de.footystats.tools.services.prediction.quality.view.PredictionQualityViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/predictionquality")
public class PredictionQualityController {

	private final PredictionQualityService predictionQualityService;

	private final PredictionQualityViewService predictionQualityViewService;

	private final IBetPredictionQualityJobService betPredictionQualityJobService;

	public PredictionQualityController(PredictionQualityService predictionQualityService, PredictionQualityViewService predictionQualityViewService,
		IBetPredictionQualityJobService betPredictionQualityJobService) {
		this.predictionQualityService = predictionQualityService;
		this.predictionQualityViewService = predictionQualityViewService;
		this.betPredictionQualityJobService = betPredictionQualityJobService;
	}

	@GetMapping("/compute")
	public JobInformation asyncComputeQuality() {
		JobExecution jobExecution = betPredictionQualityJobService.startComputeJob();
		return JobInformation.convert(jobExecution);
	}

	@GetMapping("/latest/report/{moreQualityDetailsForThisBetType}")
	public Report latestReport(@PathVariable Bet moreQualityDetailsForThisBetType) {
		final var latestRevision = predictionQualityService.latestRevision();
		final var measuredPredictionCntAggregates = predictionQualityViewService.matchPredictionQualityMeasurementCounts(latestRevision);

		// Create the bet prediction percent with count succeeded / failed for a specific percent value.
		final var betPercentDistributionResult = predictionQualityViewService.betPredictionQuality(moreQualityDetailsForThisBetType, latestRevision);
		final var dontBetPercentDistributionResult = predictionQualityViewService.dontBetPredictionQuality(moreQualityDetailsForThisBetType,
			latestRevision);

		// create prediction results for all bets
		final var influencerPredictionsAggregated = predictionQualityViewService.influencerPredictionsAggregated(moreQualityDetailsForThisBetType,
			true, latestRevision);
		final var dontBetInfluencerPredictionsAggregated = predictionQualityViewService.influencerPredictionsAggregated(
			moreQualityDetailsForThisBetType, false, latestRevision);
		return new Report(measuredPredictionCntAggregates, betPercentDistributionResult, dontBetPercentDistributionResult,
			influencerPredictionsAggregated, dontBetInfluencerPredictionsAggregated);
	}

	@GetMapping(name = "/recompute", path = {"/recompute"}, produces = {"application/json"})
	public JobInformation asyncRecomputeQuality() {
		JobExecution jobExecution = betPredictionQualityJobService.startRecomputeJob();
		return JobInformation.convert(jobExecution);
	}
}
