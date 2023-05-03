package de.ludwig.footystats.tools.backend.services.prediction.quality.batch;

import de.ludwig.footystats.tools.backend.services.match.Match;
import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQuality;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityRevision;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityService;
import org.springframework.batch.item.ItemProcessor;

import java.util.Collection;

public class MatchItemProcessor implements ItemProcessor<Match, Collection<BetPredictionQuality>> {

	private final PredictionQualityService predictionQualityService;


	public MatchItemProcessor(PredictionQualityService predictionQualityService) {
		this.predictionQualityService = predictionQualityService;
	}

	@Override
	public Collection<BetPredictionQuality> process(Match match) throws Exception {
		predictionQualityService.markWithRecomputationRevision(match);
		return predictionQualityService.measure(match, PredictionQualityRevision.IN_RECOMPUTATION);
	}
}