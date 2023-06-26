package de.footystats.tools.services.prediction.quality.batch;

import de.footystats.tools.services.prediction.quality.BetPredictionQuality;
import de.footystats.tools.services.prediction.quality.PredictionQualityRevision;
import de.footystats.tools.services.prediction.quality.PredictionQualityService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class BetPredictionQualityWriter implements ItemWriter<Collection<BetPredictionQuality>> {
	private final PredictionQualityService predictionQualityService;

	public BetPredictionQualityWriter(PredictionQualityService predictionQualityService) {
		this.predictionQualityService = predictionQualityService;
	}

	@Override
	public void write(Chunk<? extends Collection<BetPredictionQuality>> chunk) throws Exception {
		for (Collection<BetPredictionQuality> item : chunk.getItems()) {
			predictionQualityService.merge(item, PredictionQualityRevision.IN_RECOMPUTATION);
		}
	}
}
