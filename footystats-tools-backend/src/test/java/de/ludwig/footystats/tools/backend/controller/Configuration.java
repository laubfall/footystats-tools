package de.ludwig.footystats.tools.backend.controller;

import de.ludwig.footystats.tools.backend.mongo.converter.ConverterRegistry;
import de.ludwig.footystats.tools.backend.mongo.converter.MappingMongoConverterConfiguration;
import de.ludwig.footystats.tools.backend.services.EncryptionService;
import de.ludwig.footystats.tools.backend.services.footy.CsvFileDownloadService;
import de.ludwig.footystats.tools.backend.services.footy.dls.DownloadConfigService;
import de.ludwig.footystats.tools.backend.services.prediction.PredictionServiceConfiguration;
import de.ludwig.footystats.tools.backend.services.prediction.outcome.StatisticalResultOutcomeService;
import de.ludwig.footystats.tools.backend.services.prediction.quality.batch.IBetPredictionQualityJobService;
import de.ludwig.footystats.tools.backend.services.prediction.quality.view.PredictionQualityViewService;
import de.ludwig.footystats.tools.backend.services.stats.LeagueStatsServiceConfiguration;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatsServiceConfiguration;
import de.ludwig.footystats.tools.backend.services.stats.TeamStatsServiceConfiguration;
import org.springframework.batch.core.JobExecution;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import({PredictionServiceConfiguration.class, de.ludwig.footystats.tools.backend.services.csv.Configuration.class,
	FootyStatsCsvUploadController.class, MatchStatsServiceConfiguration.class, LeagueStatsServiceConfiguration.class,
	TeamStatsServiceConfiguration.class, MappingMongoConverterConfiguration.class, ConverterRegistry.class,
	EncryptionService.class, CsvFileDownloadService.class, DownloadConfigService.class,
	StatisticalResultOutcomeService.class, PredictionQualityViewService.class})
@TestConfiguration
public class Configuration {

	 @Bean
	public IBetPredictionQualityJobService betPredictionQualityJobService(){
		 return new IBetPredictionQualityJobService() {
			 @Override
			 public JobExecution startRecomputeJob() {
				 return null;
			 }

			 @Override
			 public JobExecution recomputeJobExecution() {
				 return null;
			 }

			 @Override
			 public JobExecution startComputeJob() {
				 return null;
			 }

			 @Override
			 public JobExecution computeJobExecution() {
				 return null;
			 }
		 };
	 }
}
