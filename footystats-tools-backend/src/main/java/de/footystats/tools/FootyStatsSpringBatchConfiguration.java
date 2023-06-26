package de.footystats.tools;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

/**
 * The spring batch processing configuration meant for use with the web application.
 */
@Configuration
@EnableBatchProcessing
public class FootyStatsSpringBatchConfiguration extends DefaultBatchConfiguration {
	@Override
	protected TaskExecutor getTaskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}
}
