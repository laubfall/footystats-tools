package de.ludwig.footystats.tools.backend.services.prediction.quality.batch;

import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQuality;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityRevision;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.util.Collection;

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
