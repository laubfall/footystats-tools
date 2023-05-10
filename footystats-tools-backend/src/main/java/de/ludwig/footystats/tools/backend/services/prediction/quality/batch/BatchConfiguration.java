package de.ludwig.footystats.tools.backend.services.prediction.quality.batch;

import de.ludwig.footystats.tools.backend.services.match.Match;
import de.ludwig.footystats.tools.backend.services.match.MatchRepository;
import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQuality;
import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQualityRepository;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityService;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collection;
import java.util.Map;

@Configuration
public class BatchConfiguration extends DefaultBatchConfiguration {
	@Bean(name = "recomputeItemReader")
	public RepositoryItemReader<Match> matchItemReader(MatchRepository repository) {
		return new RepositoryItemReaderBuilder<Match>().repository(repository)
			.name("matchItemReader")
			.methodName("findMatchesByState")
			.arguments(MatchStatus.complete)
			.pageSize(100)
			.sorts(Map.of("dateUnix", Sort.Direction.DESC))
			.build();
	}

	@Bean(name = "computeItemReader")
	public RepositoryItemReader<Match> computeMatchItemReader(MatchRepository repository){
		return new RepositoryItemReaderBuilder<Match>().repository(repository)
			.name("computeMatchItemReader")
			.methodName("findMatchesByStateAndRevision_RevisionIsNull")
			.arguments(MatchStatus.complete)
			.pageSize(100)
			.sorts(Map.of("dateUnix", Sort.Direction.DESC))
			.build();
	}

	@Bean(name = "migrateToNewBetPredictionQualityStep")
	public Step step(PlatformTransactionManager transactionManager, JobRepository jobRepository, RepositoryItemReader<Match> recomputeItemReader, MatchItemProcessor itemProcessor, BetPredictionQualityWriter itemWriter) {
		return new SimpleStepBuilder<Match, Collection<BetPredictionQuality>>(new StepBuilder("measurePredictionQuality", jobRepository)
			.chunk(5, transactionManager)) // first parameter is the count of elements processed in one transaction.
			.reader(recomputeItemReader)
			.processor(itemProcessor)
			.writer(itemWriter)
			.allowStartIfComplete(true)
			.transactionManager(transactionManager)
			.build();
	}

	@Bean(name = "computeBetPredictionQualityStep")
	public Step computeStep(PlatformTransactionManager transactionManager, JobRepository jobRepository, RepositoryItemReader<Match> computeItemReader, ComputeMatchItemProcessor itemProcessor, ComputeBetPredictionQualityWriter itemWriter) {
		return new SimpleStepBuilder<Match, Collection<BetPredictionQuality>>(new StepBuilder("computeMeasurePredictionQuality", jobRepository)
			.chunk(5, transactionManager)) // first parameter is the count of elements processed in one transaction.
			.reader(computeItemReader)
			.processor(itemProcessor)
			.writer(itemWriter)
			.allowStartIfComplete(true)
			.transactionManager(transactionManager)
			.build();
	}

	@Bean(name = "migratedToNewBetPredictionQualityJob")
	public Job migrationJob(JobRepository jobRepository, Step migrateToNewBetPredictionQualityStep, RecomputeJobListener jobListener) {
		return new JobBuilder(BetPredictionQualityJobService.MIGRATED_TO_NEW_BET_PREDICTION_QUALITY_JOB, jobRepository).listener(jobListener).start(migrateToNewBetPredictionQualityStep).build();
	}

	@Bean(name = "computeBetPredictionQualityJob")
	public Job computeJob(JobRepository jobRepository, Step computeBetPredictionQualityStep, ComputeJobListener jobListener) {
		return new JobBuilder("computeBetPredictionQualityJob", jobRepository).listener(jobListener).start(computeBetPredictionQualityStep).build();
	}

	@Override
	protected TaskExecutor getTaskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}
}
