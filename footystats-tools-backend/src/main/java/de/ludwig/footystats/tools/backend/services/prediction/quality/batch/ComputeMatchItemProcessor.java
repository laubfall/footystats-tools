package de.ludwig.footystats.tools.backend.services.prediction.quality.batch;

import de.ludwig.footystats.tools.backend.services.match.Match;
import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQuality;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityRevision;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityService;
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
