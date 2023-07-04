package de.footystats.tools.services.stats.batch;

import de.footystats.tools.spring.batch.JobService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

@Service
public class MatchStatsJobService extends JobService implements IMatchStatsJobService {

	private final Job reimportMatchStatsJob;

	protected MatchStatsJobService(JobLauncher jobLauncher,
		JobExplorer jobExplorer, Job reimportMatchStatsJob) {
		super(jobLauncher, jobExplorer);
		this.reimportMatchStatsJob = reimportMatchStatsJob;
	}

	@Override
	public JobExecution startReimportMatchStatsJob(){
		return executeJob(reimportMatchStatsJob, ()->byJobName(BatchConfiguration.REIMPORT_MATCH_STATS_JOB));
	}
}
