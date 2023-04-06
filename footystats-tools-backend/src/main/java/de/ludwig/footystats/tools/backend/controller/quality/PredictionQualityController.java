package de.ludwig.footystats.tools.backend.controller.quality;

import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import de.ludwig.footystats.tools.backend.services.prediction.quality.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/predictionquality")
public class PredictionQualityController {

	private PredictionQualityService predictionQualityService;

	private BetPredictionQualityRepository betPredictionQualityRepository;

	public PredictionQualityController(PredictionQualityService predictionQualityService, BetPredictionQualityRepository betPredictionQualityRepository) {
		this.predictionQualityService = predictionQualityService;
		this.betPredictionQualityRepository = betPredictionQualityRepository;
	}

	@GetMapping("/compute")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void computeQuality() {
		predictionQualityService.computeQuality();
	}

	@GetMapping("/latest/report")
	public Report latestReport(@PathVariable Bet moreQualityDetailsForThisBetType) {
		// Create the bet prediction percent with count succeeded / failed for a specific percent value.
		final List<BetPredictionQualityBetAggregate> betPercentDistributionResult = betPredictionQualityRepository.findAllByBetAndRevision(moreQualityDetailsForThisBetType, PredictionQualityService.INITIAL_REVISION, BetPredictionQualityBetAggregate.class);

		final Map<String, List<BetPredictionQualityInfluencerAggregate>> influencerPredictionsAggregated = predictionQualityService.influencerPredictionsAggregated(moreQualityDetailsForThisBetType);

		// create prediction results for all bets
		final List<BetPredictionQualityAllBetsAggregate> measuredPredictionCntAggregates = predictionQualityService.matchPredictionQualityMeasurementCounts();
		return new Report(measuredPredictionCntAggregates, betPercentDistributionResult,  influencerPredictionsAggregated);
	}


	@PostMapping(name = "/recompute", consumes = {"application/json"}, path = {"/recompute"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void recomputeQuality(@RequestBody PredictionQualityRevision revision) {
		predictionQualityService.recomputeQuality(revision);
	}
}
