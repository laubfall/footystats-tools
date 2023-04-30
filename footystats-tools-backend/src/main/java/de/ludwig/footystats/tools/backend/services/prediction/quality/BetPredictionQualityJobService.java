package de.ludwig.footystats.tools.backend.services.prediction.quality;

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

@Slf4j
@Service
public class BetPredictionQualityJobService {

	private JobLauncher jobLauncher;

	private JobExplorer jobExplorer;

	private Job migratedToNewBetPredictionQualityJob;

	public BetPredictionQualityJobService(JobLauncher jobLauncher, JobExplorer jobExplorer, Job migratedToNewBetPredictionQualityJob) {
		this.jobLauncher = jobLauncher;
		this.jobExplorer = jobExplorer;
		this.migratedToNewBetPredictionQualityJob = migratedToNewBetPredictionQualityJob;
	}

	public JobExecution startJob(){
		var jobExecution = running();
		if(jobExecution != null){
			log.info("BetPredictionQuality migrating job is already running.");
			return jobExecution;
		}
		try {
			return jobLauncher.run(migratedToNewBetPredictionQualityJob, new JobParameters());
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

	public JobExecution running(){
		Set<JobExecution> runningJobExecutions = jobExplorer.findRunningJobExecutions("migratedToNewBetPredictionQualityJob");
		if(runningJobExecutions.isEmpty()){
			return null;
		}

		return runningJobExecutions.stream().findFirst().get();
	}
}
