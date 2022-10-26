package de.ludwig.footystats.tools.backend.services.prediction;

import de.ludwig.footystats.tools.backend.FootystatsProperties;
import de.ludwig.footystats.tools.backend.services.match.MatchRepository;
import de.ludwig.footystats.tools.backend.services.match.MatchService;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityReportRepository;
import de.ludwig.footystats.tools.backend.services.prediction.quality.PredictionQualityService;
import de.ludwig.footystats.tools.backend.services.stats.MatchStatsServiceConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import({FootystatsProperties.class, MatchStatsServiceConfiguration.class})
@TestConfiguration
public class PredictionServiceConfiguration {
	@Bean
	public PredictionQualityService predictionQualityService(MatchRepository matchRepository, MatchService matchService, PredictionQualityReportRepository predictionQualityReportRepository){
		return new PredictionQualityService(matchRepository, matchService, predictionQualityReportRepository);
	}
}