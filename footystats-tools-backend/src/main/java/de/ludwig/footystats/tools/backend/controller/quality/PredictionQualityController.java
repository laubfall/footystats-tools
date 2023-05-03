package de.ludwig.footystats.tools.backend.controller.quality;

import de.ludwig.footystats.tools.backend.controller.jobs.JobInformation;
import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQualityJobService;
import de.ludwig.footystats.tools.backend.services.prediction.quality.Precast;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityRevision;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityService;
import de.ludwig.footystats.tools.backend.services.prediction.quality.view.BetPredictionQualityAllBetsAggregate;
import de.ludwig.footystats.tools.backend.services.prediction.quality.view.BetPredictionQualityBetAggregate;
import de.ludwig.footystats.tools.backend.services.prediction.quality.view.BetPredictionQualityInfluencerAggregate;
import de.ludwig.footystats.tools.backend.services.prediction.quality.view.PredictionQualityViewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/predictionquality")
public class PredictionQualityController {

	private final PredictionQualityService predictionQualityService;

	private final PredictionQualityViewService predictionQualityViewService;

	private final BetPredictionQualityJobService betPredictionQualityJobService;

	public PredictionQualityController(PredictionQualityService predictionQualityService, PredictionQualityViewService predictionQualityViewService, BetPredictionQualityJobService betPredictionQualityJobService) {
		this.predictionQualityService = predictionQualityService;
		this.predictionQualityViewService = predictionQualityViewService;
		this.betPredictionQualityJobService = betPredictionQualityJobService;
	}

	@Operation(summary = "foo", description = "bar")
	@GetMapping("/compute")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void computeQuality() {
		predictionQualityService.computeQuality();
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

	@PostMapping(name = "/recompute", path = {"/recompute"}, produces = {"application/json"})
	public JobInformation recomputeQuality() {
		JobExecution jobExecution = betPredictionQualityJobService.startJob();
		if(jobExecution == null){
			return null;
		}

		var ji = new JobInformation();
		ji.setJobId(jobExecution.getJobInstance().getInstanceId());
		return ji;
	}

	@PostMapping(name = "/precast", consumes = {"application/json"}, produces = {"application/json"}, path = {"/precast"})
	public Precast precast(@RequestBody PredictionQualityRevision revision) {
		return predictionQualityService.precast(revision);
	}
}
