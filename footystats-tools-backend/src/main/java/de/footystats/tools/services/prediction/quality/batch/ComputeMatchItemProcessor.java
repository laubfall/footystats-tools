package de.footystats.tools.services.prediction.quality.batch;

import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.prediction.quality.BetPredictionQuality;
import de.footystats.tools.services.prediction.quality.PredictionQualityRevision;
import de.footystats.tools.services.prediction.quality.PredictionQualityService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ComputeMatchItemProcessor implements ItemProcessor<Match, Collection<BetPredictionQuality>> {

	private final PredictionQualityService predictionQualityService;

	public ComputeMatchItemProcessor(PredictionQualityService predictionQualityService) {
		this.predictionQualityService = predictionQualityService;
	}

	@Override
	public Collection<BetPredictionQuality> process(Match match) throws Exception {
		final PredictionQualityRevision latestRevision = predictionQualityService.latestRevision();
		return predictionQualityService.measure(match, latestRevision);
	}
}
