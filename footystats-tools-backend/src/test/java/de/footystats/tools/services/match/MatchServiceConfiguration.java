package de.footystats.tools.services.match;

import de.footystats.tools.FootystatsProperties;
import de.footystats.tools.jackson.JackonsConfiguration;
import de.footystats.tools.services.prediction.PredictionService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@Import({JackonsConfiguration.class, FootystatsProperties.class})
@TestConfiguration
public class MatchServiceConfiguration {

	@Bean
	public PredictionService predictionService() {
		return new PredictionService();
	}

	@Bean
	public MatchService matchService(MongoTemplate mongoTemplate, PredictionService predictionService, MappingMongoConverter mappingMongoConverter) {
		return new MatchService(mongoTemplate, mappingMongoConverter, predictionService);
	}
}
