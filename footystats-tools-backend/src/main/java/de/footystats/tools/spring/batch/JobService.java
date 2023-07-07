package de.footystats.tools.spring.batch;

import java.util.Set;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

@Slf4j
public abstract class JobService {

	private final JobLauncher jobLauncher;

	private final JobExplorer jobExplorer;

	protected JobService(JobLauncher jobLauncher, JobExplorer jobExplorer) {
		this.jobLauncher = jobLauncher;
		this.jobExplorer = jobExplorer;
	}

	protected JobExecution executeJob(Job jobToExecute, Supplier<JobExecution> currentExecution) {
		var jobExecution = currentExecution.get();
		if (jobExecution != null) {
			log.info("BetPredictionQuality migrating job is already running.");
			return jobExecution;
		}
		try {
			return jobLauncher.run(jobToExecute, new JobParameters());
		} catch (JobExecutionAlreadyRunningException e) {
			log.error("Job is already running.", e);
		} catch (JobRestartException e) {
			log.error("Failed to restart job.", e);
		} catch (JobInstanceAlreadyCompleteException e) {
			log.error("Job instance already completed.", e);
		} catch (JobParametersInvalidException e) {
			log.error("Invalid job parameters.", e);
		}

		return null;
	}

	protected JobExecution byJobName(String jobName) {
		final Set<JobExecution> runningJobExecutions = jobExplorer.findRunningJobExecutions(jobName);
		if (runningJobExecutions.isEmpty()) {
			return null;
		}

		return runningJobExecutions.stream().findFirst().get();
	}
}
