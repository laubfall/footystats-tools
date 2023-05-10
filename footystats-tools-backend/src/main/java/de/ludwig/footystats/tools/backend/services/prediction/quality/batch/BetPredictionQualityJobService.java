package de.ludwig.footystats.tools.backend.services.prediction.quality.batch;

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
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Supplier;

@Slf4j
@Service
public class BetPredictionQualityJobService implements IBetPredictionQualityJobService {

	public static final String MIGRATED_TO_NEW_BET_PREDICTION_QUALITY_JOB = "migratedToNewBetPredictionQualityJob";
	private final JobLauncher jobLauncher;

	private final JobExplorer jobExplorer;

	private final Job migratedToNewBetPredictionQualityJob;

	private final Job computeBetPredictionQualityJob;

	public BetPredictionQualityJobService(JobLauncher jobLauncher, JobExplorer jobExplorer, Job migratedToNewBetPredictionQualityJob, Job computeBetPredictionQualityJob) {
		this.jobLauncher = jobLauncher;
		this.jobExplorer = jobExplorer;
		this.migratedToNewBetPredictionQualityJob = migratedToNewBetPredictionQualityJob;
		this.computeBetPredictionQualityJob = computeBetPredictionQualityJob;
	}

	@Override
	public JobExecution startRecomputeJob(){
		return executeJob(migratedToNewBetPredictionQualityJob, this::recomputeJobExecution);
	}

	@Override
	public JobExecution recomputeJobExecution(){
		Set<JobExecution> runningJobExecutions = jobExplorer.findRunningJobExecutions(MIGRATED_TO_NEW_BET_PREDICTION_QUALITY_JOB);
		if(runningJobExecutions.isEmpty()){
			return null;
		}

		return runningJobExecutions.stream().findFirst().get();
	}

	@Override
	public JobExecution startComputeJob() {
		return executeJob(computeBetPredictionQualityJob, this::computeJobExecution);
	}

	@Override
	public JobExecution computeJobExecution() {
		return null;
	}

	private JobExecution executeJob(Job jobToExecute, Supplier<JobExecution> currentExecution){
		var jobExecution = currentExecution.get();
		if(jobExecution != null){
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
}
