package de.footystats.tools.services.stats.batch;

import de.footystats.tools.services.match.Match;
import de.footystats.tools.services.prediction.quality.batch.ComputeJobListener;
import de.footystats.tools.services.stats.MatchStats;
import de.footystats.tools.services.stats.MatchStatsRepository;
import java.util.Map;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration("matchStatsBatchConfiguration")
public class BatchConfiguration {

	public static final String REIMPORT_MATCH_STATS_JOB = "reimportMatchStatsJob";

	@Bean(name = "reimportMatchStatsItemReader")
	public RepositoryItemReader<MatchStats> matchStatsItemReader(MatchStatsRepository repository) {
		return new RepositoryItemReaderBuilder<MatchStats>().repository(repository)
			.name("matchItemReader")
			.methodName("findAll")
			.pageSize(100)
			.sorts(Map.of("dateUnix", Sort.Direction.DESC))
			.build();
	}

	@Bean(name = "reimportMatchStatsStep")
	public Step step(PlatformTransactionManager transactionManager, JobRepository jobRepository,
		RepositoryItemReader<MatchStats> reimportMatchStatsItemReader, MatchStatsItemProcessor itemProcessor, MatchWriter matchWriter) {
		return new SimpleStepBuilder<MatchStats, Match>(new StepBuilder("measurePredictionQuality", jobRepository)
			.chunk(100, transactionManager)) // first parameter is the count of elements processed in one transaction.
			.reader(reimportMatchStatsItemReader)
			.processor(itemProcessor)
			.writer(matchWriter)
			.allowStartIfComplete(true)
			.transactionManager(transactionManager)
			.build();
	}

	@Bean(name = REIMPORT_MATCH_STATS_JOB)
	public Job reimportMatchStatsJob(JobRepository jobRepository, Step reimportMatchStatsStep, ComputeJobListener jobListener) {
		return new JobBuilder(REIMPORT_MATCH_STATS_JOB, jobRepository).listener(jobListener).start(reimportMatchStatsStep).build();
	}
}
