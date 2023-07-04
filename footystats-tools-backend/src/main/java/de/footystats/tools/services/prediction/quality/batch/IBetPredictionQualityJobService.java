package de.footystats.tools.services.prediction.quality.batch;

import org.springframework.batch.core.JobExecution;

/**
 * Implementations know how to start an async job to recompute bet prediction qualities or
 * compute bet prediction qualities for matches bet prediction qualities were not computed before.
 */
public interface IBetPredictionQualityJobService {

	/**
	 * Recompute bet prediction qualities for all predicted matches. Creates a new prediction quality report.
	 * @return Information about the running job.
	 */
	JobExecution startRecomputeJob();

	/**
	 * Compute bet prediction qualities for matches bet prediction qualities were not computed before.
	 * @return Information about the running job.
	 */
	JobExecution startComputeJob();
}
