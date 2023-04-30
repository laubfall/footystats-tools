package de.ludwig.footystats.tools.backend.services.prediction.quality.batch;

import de.ludwig.footystats.tools.backend.services.match.Match;
import de.ludwig.footystats.tools.backend.services.match.MatchRepository;
import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQuality;
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
		// TODO set revision otherwise new matches would not get a revision number and
		// counted twice when we actualize the prediction quality report.
		return predictionQualityService.measure(match);
	}
}
