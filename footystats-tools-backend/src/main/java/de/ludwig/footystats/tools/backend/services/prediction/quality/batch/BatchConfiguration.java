package de.ludwig.footystats.tools.backend.services.prediction.quality.batch;

import de.ludwig.footystats.tools.backend.services.match.Match;
import de.ludwig.footystats.tools.backend.services.match.MatchRepository;
import de.ludwig.footystats.tools.backend.services.prediction.quality.BetPredictionQuality;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityService;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.BatchConfigurationException;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
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
public class BatchConfiguration extends DefaultBatchConfiguration{
	@Bean
	public RepositoryItemReader<Match> matchItemReader(MatchRepository repository) {
		return new RepositoryItemReaderBuilder<Match>().repository(repository)
			.name("matchItemReader")
			.methodName("findMatchesByState")
			.arguments(MatchStatus.complete)
			.pageSize(100)
			.sorts(Map.of("dateUnix", Sort.Direction.DESC))
			.build();
	}

	@Bean
	public MatchItemProcessor processor(PredictionQualityService predictionQualityService) {
		return new MatchItemProcessor(predictionQualityService);
	}

	@Bean
	public BetPredictionQualityWriter writer(PredictionQualityService predictionQualityService) {
		return new BetPredictionQualityWriter(predictionQualityService);
	}

	@Bean(name="migrateToNewBetPredictionQualityStep")
	public Step step(PlatformTransactionManager transactionManager, JobRepository jobRepository, RepositoryItemReader<Match> itemReader, MatchItemProcessor itemProcessor, BetPredictionQualityWriter itemWriter) {
		return new SimpleStepBuilder<Match, Collection<BetPredictionQuality>>(new StepBuilder("measurePredictionQuality", jobRepository)
			.chunk(5, transactionManager))
			.reader(itemReader)
			.writer(itemWriter)
			.transactionManager(transactionManager)
			.processor(itemProcessor).build();
	}

	@Bean("migrateToNewBetPredictionQualityJob")
	public Job migrationJob(JobRepository jobRepository, Step migrateToNewBetPredictionQualityStep){
		return new JobBuilder("migrationJob", jobRepository).start(migrateToNewBetPredictionQualityStep).build();
	}

	@Override
	protected TaskExecutor getTaskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}
}
