package de.footystats.tools.services.prediction.quality.batch;

import static de.footystats.tools.services.prediction.quality.batch.BatchConfiguration.COMPUTE_BET_PREDICTION_QUALITY_JOB;
import static de.footystats.tools.services.prediction.quality.batch.BatchConfiguration.MIGRATED_TO_NEW_BET_PREDICTION_QUALITY_JOB;

import de.footystats.tools.spring.batch.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BetPredictionQualityJobService extends JobService implements IBetPredictionQualityJobService {

	private final Job migratedToNewBetPredictionQualityJob;

	private final Job computeBetPredictionQualityJob;

	public BetPredictionQualityJobService(JobLauncher jobLauncher, JobExplorer jobExplorer, Job migratedToNewBetPredictionQualityJob,
		Job computeBetPredictionQualityJob) {
		super(jobLauncher, jobExplorer);
		this.migratedToNewBetPredictionQualityJob = migratedToNewBetPredictionQualityJob;
		this.computeBetPredictionQualityJob = computeBetPredictionQualityJob;
	}

	@Override
	public JobExecution startRecomputeJob() {
		return executeJob(migratedToNewBetPredictionQualityJob, () -> byJobName(MIGRATED_TO_NEW_BET_PREDICTION_QUALITY_JOB));
	}

	@Override
	public JobExecution startComputeJob() {
		return executeJob(computeBetPredictionQualityJob, () -> byJobName(COMPUTE_BET_PREDICTION_QUALITY_JOB));
	}
}
