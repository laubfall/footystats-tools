package de.footystats.tools.services.stats.batch;

import org.springframework.batch.core.JobExecution;

/**
 * Implementations know how to start a spring batch job that triggers the recalculation of predictions for all matches.
 */
public interface IMatchStatsJobService {

	/**
	 * Start the job.
	 *
	 * @return Current job execution context.
	 */
	JobExecution startReimportMatchStatsJob();
}
