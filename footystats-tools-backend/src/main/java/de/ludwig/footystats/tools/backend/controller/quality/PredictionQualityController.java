package de.ludwig.footystats.tools.backend.controller.quality;

import de.ludwig.footystats.tools.backend.services.prediction.Bet;
import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQualityRepository;
import de.ludwig.footystats.tools.backend.services.prediction.quality.Precast;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityRevision;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityService;
import de.ludwig.footystats.tools.backend.services.prediction.quality.view.BetPredictionQualityAllBetsAggregate;
import de.ludwig.footystats.tools.backend.services.prediction.quality.view.BetPredictionQualityBetAggregate;
import de.ludwig.footystats.tools.backend.services.prediction.quality.view.BetPredictionQualityInfluencerAggregate;
import de.ludwig.footystats.tools.backend.services.prediction.quality.view.PredictionQualityViewService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/predictionquality")
public class PredictionQualityController {

	private PredictionQualityService predictionQualityService;

	private PredictionQualityViewService predictionQualityViewService;

	private BetPredictionQualityRepository betPredictionQualityRepository;

	public PredictionQualityController(PredictionQualityService predictionQualityService, PredictionQualityViewService predictionQualityViewService, BetPredictionQualityRepository betPredictionQualityRepository) {
		this.predictionQualityService = predictionQualityService;
		this.predictionQualityViewService = predictionQualityViewService;
		this.betPredictionQualityRepository = betPredictionQualityRepository;
	}

	@GetMapping("/compute")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void computeQuality() {
		predictionQualityService.computeQuality();
	}

	@GetMapping("/latest/report/{moreQualityDetailsForThisBetType}")
	public Report latestReport(@PathVariable Bet moreQualityDetailsForThisBetType) {
		final List<BetPredictionQualityAllBetsAggregate> measuredPredictionCntAggregates = predictionQualityViewService.matchPredictionQualityMeasurementCounts();

		// Create the bet prediction percent with count succeeded / failed for a specific percent value.
		final List<BetPredictionQualityBetAggregate> betPercentDistributionResult = betPredictionQualityRepository.findAllByBetAndRevision(moreQualityDetailsForThisBetType, PredictionQualityRevision.NO_REVISION, BetPredictionQualityBetAggregate.class);

		// create prediction results for all bets
		final Map<String, List<BetPredictionQualityInfluencerAggregate>> influencerPredictionsAggregated = predictionQualityViewService.influencerPredictionsAggregated(moreQualityDetailsForThisBetType);
		return new Report(measuredPredictionCntAggregates, betPercentDistributionResult, influencerPredictionsAggregated);
	}

	@PostMapping(name = "/recompute", consumes = {"application/json"}, path = {"/recompute"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void recomputeQuality() {
		predictionQualityService.recomputeQuality();
	}

	@PostMapping(name = "/precast", consumes = {"application/json"}, produces = {"application/json"}, path = {"/precast"})
	public Precast precast(@RequestBody PredictionQualityRevision revision) {
		return predictionQualityService.precast(revision);
	}
}