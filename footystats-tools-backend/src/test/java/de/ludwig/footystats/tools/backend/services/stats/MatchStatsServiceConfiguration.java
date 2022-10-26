package de.ludwig.footystats.tools.backend.services.stats;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ludwig.footystats.tools.backend.FootystatsProperties;
import de.ludwig.footystats.tools.backend.jackson.JackonsConfiguration;
import de.ludwig.footystats.tools.backend.services.match.MatchRepository;
import de.ludwig.footystats.tools.backend.services.match.MatchService;
import de.ludwig.footystats.tools.backend.services.prediction.PredictionService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

/**
 * Test configuration for JsonTests. Actually empty but required to avoid using the SpringBoot Config.
 */
@Import({JackonsConfiguration.class, FootystatsProperties.class})
@TestConfiguration
public class MatchStatsServiceConfiguration {
    @Bean
    public PredictionService predictionService() {
        return new PredictionService();
    }

    @Bean
    public MatchService matchService(MongoTemplate mongoTemplate, MatchRepository matchRepository, ObjectMapper objectMapper, PredictionService predictionService, MappingMongoConverter mappingMongoConverter) {
        return new MatchService(mongoTemplate, mappingMongoConverter, matchRepository, objectMapper, predictionService);
    }

    @Bean
    public MatchStatsService matchStatsService(MongoTemplate template, MappingMongoConverter mappingMongoConverter, MatchService matchService) {
        return new MatchStatsService(template, mappingMongoConverter, matchService);
    }
}
