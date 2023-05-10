package de.ludwig.footystats.tools.backend.services.prediction.quality.batch;

import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQuality;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityRevision;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ComputeBetPredictionQualityWriter implements ItemWriter<Collection<BetPredictionQuality>> {
	private final PredictionQualityService predictionQualityService;

	public ComputeBetPredictionQualityWriter(PredictionQualityService predictionQualityService) {
		this.predictionQualityService = predictionQualityService;
	}

	@Override
	public void write(Chunk<? extends Collection<BetPredictionQuality>> chunk) throws Exception {
		PredictionQualityRevision latestRevision = predictionQualityService.latestRevision();
		for (Collection<BetPredictionQuality> item : chunk.getItems()) {
			predictionQualityService.merge(item, latestRevision);
		}
	}
}
