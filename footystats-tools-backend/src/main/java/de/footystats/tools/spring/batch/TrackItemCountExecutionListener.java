package de.footystats.tools.spring.batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;

/**
 * This class is used to track the number of items to process in a batch job.
 * <p>
 * Typically, this is inherited by a class that implements {@link JobExecutionListener}. Keep in mind that the {@link #beforeJob(JobExecution)} method
 * has to be called by the inheriting class.
 */
public abstract class TrackItemCountExecutionListener implements JobExecutionListener, StepExecutionListener {

	private static final String ECTX_COUNT_KEY = "countOfItemsToProcess";

	public static Long countOfItemsToProcess(JobExecution jobExecution) {
		if (jobExecution == null) {
			return 0L;
		}

		// Iterate over all step executions and sum up the count of items to process.
		var count = 0L;
		for (var stepExecution : jobExecution.getStepExecutions()) {
			final ExecutionContext executionContext = stepExecution.getExecutionContext();
			var rawValue = executionContext.get(ECTX_COUNT_KEY);
			if (rawValue == null) {
				continue;
			}
			count += executionContext.getLong(ECTX_COUNT_KEY);
		}
		return count;
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		stepExecution.getExecutionContext().put(ECTX_COUNT_KEY, count());
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		//final Long count = count();
		//jobExecution.getExecutionContext().put(ECTX_COUNT_KEY, count);
	}

	public abstract Long count();
}
