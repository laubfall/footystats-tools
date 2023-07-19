package de.footystats.tools.services.prediction.quality.batch;

import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.prediction.quality.BetPredictionQuality;
import de.footystats.tools.services.prediction.quality.PredictionQualityRevision;
import de.footystats.tools.services.prediction.quality.PredictionQualityService;
import java.util.Collection;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * This class computes the quality of done predictions for a match. This mainly means it checks if a prediction was right or wrong and write the
 * result to the mongo repo as documents per prediction in percent. Every document get a revision number, you can think of that as a bracket around
 * all documents. Computing quality of new matches does not change the revision number, but recomputing does.
 */
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
