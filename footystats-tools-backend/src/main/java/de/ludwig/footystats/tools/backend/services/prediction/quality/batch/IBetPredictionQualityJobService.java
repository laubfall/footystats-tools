package de.ludwig.footystats.tools.backend.services.prediction.quality.batch;

import org.springframework.batch.core.JobExecution;

public interface IBetPredictionQualityJobService {
	JobExecution startRecomputeJob();

	JobExecution recomputeJobExecution();

	JobExecution startComputeJob();

	JobExecution computeJobExecution();
}
