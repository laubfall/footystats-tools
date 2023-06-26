package de.footystats.tools.controller.quality;

import de.footystats.tools.controller.jobs.JobInformation;
import de.footystats.tools.services.prediction.Bet;
import de.footystats.tools.services.prediction.quality.PredictionQualityRevision;
import de.footystats.tools.services.prediction.quality.PredictionQualityService;
import de.footystats.tools.services.prediction.quality.batch.IBetPredictionQualityJobService;
import de.footystats.tools.services.prediction.quality.view.BetPredictionQualityAllBetsAggregate;
import de.footystats.tools.services.prediction.quality.view.BetPredictionQualityBetAggregate;
import de.footystats.tools.services.prediction.quality.view.BetPredictionQualityInfluencerAggregate;
import de.footystats.tools.services.prediction.quality.view.PredictionQualityViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/predictionquality")
public class PredictionQualityController {

	private final PredictionQualityService predictionQualityService;

	private final PredictionQualityViewService predictionQualityViewService;

	private final IBetPredictionQualityJobService betPredictionQualityJobService;

	public PredictionQualityController(PredictionQualityService predictionQualityService, PredictionQualityViewService predictionQualityViewService, IBetPredictionQualityJobService betPredictionQualityJobService) {
		this.predictionQualityService = predictionQualityService;
		this.predictionQualityViewService = predictionQualityViewService;
		this.betPredictionQualityJobService = betPredictionQualityJobService;
	}

	@GetMapping("/compute")
	public JobInformation asyncComputeQuality() {
		JobExecution jobExecution = betPredictionQualityJobService.startComputeJob();
		return convert(jobExecution);
	}

	@GetMapping("/latest/report/{moreQualityDetailsForThisBetType}")
	public Report latestReport(@PathVariable Bet moreQualityDetailsForThisBetType) {
		final PredictionQualityRevision latestRevision = predictionQualityService.latestRevision();
		final List<BetPredictionQualityAllBetsAggregate> measuredPredictionCntAggregates = predictionQualityViewService.matchPredictionQualityMeasurementCounts(latestRevision);

		// Create the bet prediction percent with count succeeded / failed for a specific percent value.
		final List<BetPredictionQualityBetAggregate> betPercentDistributionResult = predictionQualityViewService.betPredictionQuality(moreQualityDetailsForThisBetType, latestRevision);
		final List<BetPredictionQualityBetAggregate> dontBetPercentDistributionResult = predictionQualityViewService.dontBetPredictionQuality(moreQualityDetailsForThisBetType, latestRevision);

		// create prediction results for all bets
		final Map<String, List<BetPredictionQualityInfluencerAggregate>> influencerPredictionsAggregated = predictionQualityViewService.influencerPredictionsAggregated(moreQualityDetailsForThisBetType, true, latestRevision);
		final Map<String, List<BetPredictionQualityInfluencerAggregate>> dontBetInfluencerPredictionsAggregated = predictionQualityViewService.influencerPredictionsAggregated(moreQualityDetailsForThisBetType, false, latestRevision);
		return new Report(measuredPredictionCntAggregates, betPercentDistributionResult, dontBetPercentDistributionResult, influencerPredictionsAggregated, dontBetInfluencerPredictionsAggregated);
	}

	@GetMapping(name = "/recompute", path = {"/recompute"}, produces = {"application/json"})
	public JobInformation asyncRecomputeQuality() {
		JobExecution jobExecution = betPredictionQualityJobService.startRecomputeJob();
		return convert(jobExecution);
	}

	private static JobInformation convert(JobExecution jobExecution) {
		if (jobExecution == null) {
			return null;
		}

		var ji = new JobInformation();
		ji.setJobId(jobExecution.getJobInstance().getInstanceId());
		return ji;
	}
}
