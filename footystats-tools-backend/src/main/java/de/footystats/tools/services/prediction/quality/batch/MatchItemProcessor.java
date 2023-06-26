package de.footystats.tools.services.prediction.quality.batch;

import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.prediction.quality.BetPredictionQuality;
import de.footystats.tools.services.prediction.quality.PredictionQualityRevision;
import de.footystats.tools.services.prediction.quality.PredictionQualityService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * This does the prediction quality measurement for one match.
 */
@Component
public class MatchItemProcessor implements ItemProcessor<Match, Collection<BetPredictionQuality>> {

	private final PredictionQualityService predictionQualityService;

	public MatchItemProcessor(PredictionQualityService predictionQualityService) {
		this.predictionQualityService = predictionQualityService;
	}

	@Override
	public Collection<BetPredictionQuality> process(Match match) throws Exception {
		return predictionQualityService.measure(match, PredictionQualityRevision.IN_RECOMPUTATION);
	}
}
