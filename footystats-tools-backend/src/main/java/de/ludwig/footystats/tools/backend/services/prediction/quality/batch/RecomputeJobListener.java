package de.ludwig.footystats.tools.backend.services.prediction.quality.batch;

import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQualityRepository;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityRevision;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityService;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class RecomputeJobListener implements JobExecutionListener {

	private final PredictionQualityService predictionQualityService;

	private final BetPredictionQualityRepository betPredictionQualityRepository;

	public RecomputeJobListener(PredictionQualityService predictionQualityService, BetPredictionQualityRepository betPredictionQualityRepository) {
		this.predictionQualityService = predictionQualityService;
		this.betPredictionQualityRepository = betPredictionQualityRepository;
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		// Cleanup old recomputations that were not lifted to a new revision.
		betPredictionQualityRepository.deleteByRevisionEquals(PredictionQualityRevision.IN_RECOMPUTATION);
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		predictionQualityService.revisionForRecompute();
	}
}
